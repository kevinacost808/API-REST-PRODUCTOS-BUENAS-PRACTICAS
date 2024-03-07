package com.kevin.apirestproductos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String marca;
    private String nombre;
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    private Date fechaIngreso;
}
