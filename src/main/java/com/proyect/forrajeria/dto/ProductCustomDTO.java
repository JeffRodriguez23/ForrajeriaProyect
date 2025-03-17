package com.proyect.forrajeria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCustomDTO {

    private String productName;
    private BigDecimal productPrice;
    private String productProtein;
    private String productWeight;
    private String productFlavor;
    private String productSize;
    private String makerName;

}
