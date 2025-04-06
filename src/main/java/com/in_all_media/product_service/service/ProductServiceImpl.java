package com.in_all_media.product_service.service;

import com.in_all_media.product_service.model.Product;
import com.in_all_media.product_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByPriceRange(int initialRange, int finalRange) {
        // Use Stream API to filter products within the specified price range
        return productRepository.findAllProducts().stream()
                .filter(product -> product.getPrice() >= initialRange && product.getPrice() <= finalRange)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getProductsNamesSortedByPrice() {
        // Use Stream API to sort the list of products by price in ascending order
        return productRepository.findAllProducts().stream()
                .sorted((firstProduct, secondProduct) -> Integer.compare(firstProduct.getPrice(), secondProduct.getPrice()))
                .map(Product::getItem) // Extract product names (item)
                .collect(Collectors.toList());
    }
}