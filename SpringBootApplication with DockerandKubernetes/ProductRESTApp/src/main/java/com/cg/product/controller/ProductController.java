package com.cg.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.product.model.Product;
import com.cg.product.repository.ProductRepository;

@RestController
@RequestMapping("/myapp")
@CrossOrigin(origins="*")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/product")
	public @ResponseBody List<Product> getAllProducts() {
		List<Product> productList =productRepository.findAll();
		return productList;
	}

	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable(value="id") String productId) {
		List<Product> productList =productRepository.findAll();
		List<Product> filteredProductList = productList.stream().filter((product)->product.getId()==Integer.parseInt(productId)).collect(Collectors.toList());
		if(filteredProductList == null || filteredProductList.size() == 0) {
			return null;
		}
		else {
			return filteredProductList.get(0);
		}
	}

	@PostMapping(value="/product", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Product createNewProduct(@RequestBody Product product) {
		productRepository.save(product);
		System.out.println("createNewProduct(): " + product);
		return product;
	}

	@PutMapping(value="/product/{id}", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Product updateProduct(@RequestBody Product newProduct, @PathVariable(value="id") String productId) {
		Product productToBeUpdated = null;
		List<Product> productList =productRepository.findAll();
		List<Product> filteredProductList = productList.stream().filter((product)->product.getId()==Integer.parseInt(productId)).collect(Collectors.toList());
		if(filteredProductList == null || filteredProductList.size() == 0) {
			return null;
		}
		else {
			productToBeUpdated = filteredProductList.get(0);
		}
		productToBeUpdated.setName(newProduct.getName());
		productToBeUpdated.setPrice(newProduct.getPrice());
		productToBeUpdated.setQuantity(newProduct.getQuantity());
		productRepository.save(productToBeUpdated);
		return productToBeUpdated;
	}

	@DeleteMapping(value="/product/{id}")
	public boolean deleteProduct(@PathVariable(value="id") String productId) {
		List<Product> productList =productRepository.findAll();
		List<Product> filteredProductList = productList.stream().filter((product)->product.getId()==Integer.parseInt(productId)).collect(Collectors.toList());
		if(filteredProductList == null || filteredProductList.size() == 0) {
			return false;
		}
		else {
			productList.remove(filteredProductList.get(0));
			productRepository.delete(filteredProductList.get(0));
			return true;
		}
	}

}
