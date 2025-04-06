package com.in_all_media.product_service.repository;

import com.in_all_media.product_service.model.Product;
import java.util.List;

public interface IProductRepository {
    List<Product> findAllProducts();
}