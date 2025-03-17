package com.proyect.forrajeria.dto;

import com.proyect.forrajeria.entity.Maker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productName;
    private BigDecimal productPrice;
    private String productProtein;
    private String productWeight;
    private String productFlavor;
    private String productSize;
    private Long makerId;
}
