package com.limaz.tddstore.core;

import java.util.UUID;

public class Customer
    {
        public UUID id;
        public String firstName;
        public String lastName;
        public Address shippingAddress;

        public Customer(UUID id, String firstName, String lastName)
        {
        	this.id = id;
        	this.firstName = firstName;
        	this.lastName = lastName;
            shippingAddress = new Address();
        }
    }

    