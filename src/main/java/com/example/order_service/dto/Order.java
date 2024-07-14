package com.example.order_service.dto;

import lombok.Data;

@Data
public class Order {
    private String product;
    private Integer quantity;
}
