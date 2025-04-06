package com.in_all_media.product_service.controller;

import com.in_all_media.product_service.model.Product;
import com.in_all_media.product_service.service.IProductService;
import com.in_all_media.product_service.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // Endpoint **Price Filter**
    @GetMapping("/filter/price/{initial_range}/{final_range}")
    public ResponseEntity<?> filterByPrice(@PathVariable(required = false) Integer initial_range,
                                           @PathVariable(required = false) Integer final_range) {
        logger.info("Received request for filtering products in price range: {} to {}", initial_range, final_range);
        try {
            // Validate if parameters are missing
            if (initial_range == null || final_range == null) {
                ErrorResponse errorResponse = new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Parameter is missing.",
                        "/filter/price/" + (initial_range != null ? initial_range : "missing")
                                + "/" + (final_range != null ? final_range : "missing") // Show 'missing' for undefined parameters
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Validate if parameters are invalid (negative or inconsistent range)
            if (initial_range < 0 || final_range < 0 || initial_range > final_range) {
                ErrorResponse errorResponse = new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid range parameters.",
                        "/filter/price/" + initial_range + "/" + final_range
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Filter products within the price range
            List<Product> filteredProducts = productService.getProductsByPriceRange(initial_range, final_range);

            return ResponseEntity.ok(filteredProducts);
        } catch (Exception e) {
            logger.error("ERROR: An unexpected error occurred while filtering products: {}", e.getMessage(), e);
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred.",
                    "/filter/price/" + initial_range + "/" + final_range
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Endpoint **Price Sorting**
    @GetMapping("/sort/price")
    public ResponseEntity<?> sortByPrice() {
        logger.info("Received request to sort products by price");
        try {
            // Sort products by price in ascending order
            List<String> sortedProductsNames = productService.getProductsNamesSortedByPrice();

            return ResponseEntity.ok(sortedProductsNames);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while sorting products: {}", e.getMessage(), e);
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred.",
                    "/sort/price"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}