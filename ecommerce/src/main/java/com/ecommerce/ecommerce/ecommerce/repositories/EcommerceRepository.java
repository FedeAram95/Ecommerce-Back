package com.ecommerce.ecommerce.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerce.ecommerce.entities.EcommerceConfig;

@Repository
public interface EcommerceRepository extends JpaRepository<EcommerceConfig, Long> {

}
