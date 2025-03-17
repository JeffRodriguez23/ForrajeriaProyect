package com.proyect.forrajeria.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name="distribuidor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String makerName;

    @OneToMany(mappedBy ="maker", orphanRemoval = true , fetch = FetchType.LAZY)
    @JsonIgnoreProperties("maker") // Evita la referencia infinita en la serialización
    private List<Product> productList;


}
