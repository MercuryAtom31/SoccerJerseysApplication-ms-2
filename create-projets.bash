#!/usr/bin/env bash
 
spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=customers-service \
--package-name=com.soccerjerseysapplication.customers \
--groupId=com.soccerjerseysapplication.customers \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
customers-service
 
spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=jerseys-service \
--package-name=com.soccerjerseysapplication.jerseys \
--groupId=com.soccerjerseysapplication.jerseys \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
jerseys-service
 
spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=orders-service \
--package-name=com.soccerjerseysapplication.orders \
--groupId=com.soccerjerseysapplication.orders \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
orders-service
 
spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=playersteams-service \
--package-name=com.soccerjerseysapplication.playersteams \
--groupId=com.soccerjerseysapplication.playersteams \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
playersteams-service
 
spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.soccerjerseysapplication.apigateway \
--groupId=com.soccerjerseysapplication.apigateway \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
api-gateway