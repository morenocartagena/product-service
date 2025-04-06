package com.in_all_media.product_service.repository;

import com.in_all_media.product_service.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// TODO: Replace this implementation with a custom repository as needed.
@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private final List<Product> providedProductCollection = new ArrayList<>();

    public ProductRepositoryImpl() {
        // Initialize the product list
        providedProductCollection.add(new Product("74001755", "Ball Gown", "Full Body Outfits", 3548, 7, 1));
        providedProductCollection.add(new Product("74002423", "Shawl", "Accessories", 758, 12, 1));
    }

    @Override
    public List<Product> findAllProducts() {
        return providedProductCollection;
    }
}