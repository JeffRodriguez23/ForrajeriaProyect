package com.proyect.forrajeria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name="producto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre")
    private String productName;
    @Column(name="precio")
    private BigDecimal productPrice;
    @Column(name="proteina")
    private String productProtein;
    @Column(name="peso")
    private String productWeight;
    @Column(name = "sabor")
    private String productFlavor;
    @Column(name = "tamanio")
    private String productSize;

    @ManyToOne
    @JoinColumn(name = "id_maker")
    @JsonIgnoreProperties("productList") // Evita referencias circulares
    private Maker maker;



}
