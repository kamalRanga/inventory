package com.guru.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guru.login.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}