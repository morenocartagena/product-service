package com.in_all_media.product_service.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// TODO: Extend or replace this model as needed.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String barcode;
    private String item;
    private String category;
    private Integer price;
    private Integer discount;
    private Integer available;
}