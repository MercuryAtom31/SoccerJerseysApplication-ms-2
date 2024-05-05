#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

#array to hold all our test data ids
allTestCustomerIds=()

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

##CREATE SOME CUSTOMER TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
#
body=\
'{
  "firstName":"Christine",
  "lastName":"Gerard",
  "emailAddress":"christine@gmail.com",
  "streetAddress": "99 Main Street",
  "city":"Montreal",
  "province": "Quebec",
  "country": "Canada",
  "postalCode": "H3A 1A1",
  "phoneNumbers": [
    {
      "type": "HOME",
      "number": "514-555-5555"
    },
    {
      "type": "WORK",
      "number": "514-555-5556"
    }
  ]
}'
    recreateCustomer 1 "$body"
#

} #end of setupTestdata


#USING CUSTOMER TEST DATA - EXECUTE POST REQUEST
function recreateCustomer() {
    local testId=$1
    local aggregate=$2

    #create the customer and record the generated customerId
    customerId=$(curl -X POST http://$HOST:$PORT/api/v1/customers -H "Content-Type:
    application/json" --data "$aggregate" | jq '.customerId')
    allTestCustomerIds[$testId]=$customerId
    echo "Added Customer with customerId: ${allTestCustomerIds[$testId]}"
}


#don't start testing until all the microservices are up and running
function waitForService() {
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
waitForService curl -X DELETE http://$HOST:$PORT/api/v1/customers/cc9c2c7f-afc9-46fb-8119-17158e54d02f

setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all customers works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers -s"
assertEqual 10 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted customer works
echo -e "\nTest 2: Verify that a normal get by id of earlier posted customer works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} '${body}' -s"
assertEqual ${allTestCustomerIds[1]} $(echo $RESPONSE | jq .customerId)
assertEqual "\"Christine\"" $(echo $RESPONSE | jq ".firstName")
#
#
## Verify that an update of an earlier posted customer works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted customer works"
body=\
'{
  "firstName":"Christine",
  "lastName":"Gerard",
  "emailAddress":"christine@gmail.com",
  "streetAddress": "99 Main Street",
  "city":"Montreal",
  "province": "Quebec",
  "country": "Canada",
  "postalCode": "H3A 1A1",
  "phoneNumbers": [
    {
      "type": "HOME",
      "number": "514-555-5555"
    },
    {
      "type": "WORK",
      "number": "514-555-5556"
    }
  ]
}'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted customer works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get customer request with a non existing customerId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d7 -s"
#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get customer request with an invalid customerId"
assertCurl 422 "curl http://$HOST:$PORT/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d -s"


if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi