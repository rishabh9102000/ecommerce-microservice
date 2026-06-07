package com.example.order.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    @NotBlank
    private  String productId;
    @NotBlank
    private String userId;
    @Min(1)
    private int quantity;
}
