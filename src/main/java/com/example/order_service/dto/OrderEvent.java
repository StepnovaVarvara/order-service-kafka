package com.example.order_service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderEvent {
    private String product;
    private Integer quantity;
}
