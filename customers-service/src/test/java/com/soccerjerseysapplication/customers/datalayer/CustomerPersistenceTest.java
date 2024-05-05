package com.soccerjerseysapplication.customers.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerPersistenceTest {
    @Autowired
    CustomerRepository customerRepository;
    Customer presaveCustomer;
    @BeforeEach
    public void setup(){
        customerRepository.deleteAll();
        presaveCustomer = new Customer("john", "johnes", "test@gmail.com", new Address("a","a", "a", "a","a"), List.of(new PhoneNumber(), new PhoneNumber(), new PhoneNumber()));
        customerRepository.save(presaveCustomer);
    }


    @Test
    public void findByCustomerIdentifier_CustomerId_ShouldSucceed(){
        //act
        Customer foundCustomer = customerRepository.findByCustomerIdentifier_CustomerId(presaveCustomer.getCustomerIdentifier().getCustomerId());
        //assert
        assertNotNull(foundCustomer);
    }

    @Test
    public void findCustomerCustomerIdentifier_CustomerId_ShouldFail(){
        //act
        Customer fail = customerRepository.findByCustomerIdentifier_CustomerId("test");
        //assert
        assertNull(fail);
    }

}