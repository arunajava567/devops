package com.cg.product.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.product.model.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
