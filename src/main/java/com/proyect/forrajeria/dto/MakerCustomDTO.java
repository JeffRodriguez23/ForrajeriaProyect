package com.proyect.forrajeria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakerCustomDTO {

    private String makerName;
    private List<ProductDTO> productDTOList;
}
