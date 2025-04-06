package com.in_all_media.product_service.service;

import com.in_all_media.product_service.model.Product;
import java.util.List;

public interface IProductService {
    List<Product> getProductsByPriceRange(int initialRange, int finalRange);
    List<String> getProductsNamesSortedByPrice();
}