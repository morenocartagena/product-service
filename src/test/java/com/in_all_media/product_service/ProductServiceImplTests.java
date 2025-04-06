package com.in_all_media.product_service;

import com.in_all_media.product_service.model.Product;
import com.in_all_media.product_service.repository.IProductRepository;
import com.in_all_media.product_service.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceImplTests {

    private IProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        // Mocking the repository
        productRepository = Mockito.mock(IProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    private List<Product> getMockProducts() {
        // Mock data for tests
        return List.of(
                new Product("123456789", "Laptop", "Electronics", 1500, 10, 1),
                new Product("987654321", "Phone", "Electronics", 800, 15, 1),
                new Product("456789123", "Headphones", "Accessories", 200, 5, 1),
                new Product("789123456", "Monitor", "Electronics", 1200, 8, 1)
        );
    }

    @Test
    public void testGetProductsByPriceRange_ValidRange_ShouldReturnFilteredProducts() {
        // Arrange: Mock the repository's behavior
        when(productRepository.findAllProducts()).thenReturn(getMockProducts());

        // Act: Call the service method
        List<Product> filteredProducts = productService.getProductsByPriceRange(500, 1500);

        // Assert: Verify the results
        assertEquals(3, filteredProducts.size());
        assertEquals("Laptop", filteredProducts.get(0).getItem());
        assertEquals("Phone", filteredProducts.get(1).getItem());
        assertEquals("Monitor", filteredProducts.get(2).getItem());
    }

    @Test
    public void testGetProductsByPriceRange_InvalidRange_ShouldReturnEmptyList() {
        // Arrange
        when(productRepository.findAllProducts()).thenReturn(getMockProducts());

        // Act
        List<Product> filteredProducts = productService.getProductsByPriceRange(2000, 3000);

        // Assert
        assertEquals(0, filteredProducts.size());
    }

    @Test
    public void testGetProductsNamesSortedByPrice_ShouldReturnSortedNames() {
        // Arrange
        when(productRepository.findAllProducts()).thenReturn(getMockProducts());

        // Act
        List<String> sortedProductNames = productService.getProductsNamesSortedByPrice();

        // Assert
        assertEquals(4, sortedProductNames.size());
        assertEquals("Headphones", sortedProductNames.get(0));
        assertEquals("Phone", sortedProductNames.get(1));
        assertEquals("Monitor", sortedProductNames.get(2));
        assertEquals("Laptop", sortedProductNames.get(3));
    }
}