package com.kevin.apirestproductos.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductoDto implements Serializable {
    private Integer id;
    private String marca;
    private String nombre;
    private String descripcion;
    private Double precioCompra;
    private Double precioVenta;
    private Date fechaIngreso;
}
