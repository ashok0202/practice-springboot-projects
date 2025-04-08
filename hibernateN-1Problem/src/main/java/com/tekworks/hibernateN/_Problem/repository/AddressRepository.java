package com.tekworks.hibernateN._Problem.repository;

import com.tekworks.hibernateN._Problem.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}
