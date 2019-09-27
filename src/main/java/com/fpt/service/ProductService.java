package com.fpt.service;

import com.fpt.entity.Product;
import com.fpt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product create(Product product) {
        product.setCreateAt(Calendar.getInstance().getTimeInMillis());
        product.setUpdateAt(Calendar.getInstance().getTimeInMillis());
        product.setStatus(1);
        return productRepository.save(product);
    }

    public Page<Product> getList(int page, int limit) {
        return productRepository.findAll(PageRequest.of(page-1, limit));
    }

    public Product getDetail(long id) {
        return  productRepository.findById(id).orElse(null);
    }

    public List<Product> search(String name) {
        return productRepository.findFirstByName(name);
    }

//    public Product update(long id, Product updateProduct) {
//        Product existProduct = productRepository.findById(id).orElse(null);
//        if (existProduct == null) {
//            return null;
//        }
//
//        existProduct.setName(updateProduct.getName());
//        existProduct.setPrice(updateProduct.getPrice());
//        existProduct.setThumbnail(updateProduct.getThumbnail());
//        existProduct.setUpdateAt(updateProduct.getUpdateAt());
//        return productRepository.save(updateProduct);
//    }

    public boolean delete(long id) {
        Product existProduct = productRepository.findById(id).orElse(null);

        if (existProduct == null) {
            return false;
        }

        productRepository.delete(existProduct);
        return true;
    }
}
