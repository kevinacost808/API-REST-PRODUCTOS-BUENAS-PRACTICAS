package com.kevin.apirestproductos.services;

import com.kevin.apirestproductos.model.dto.ProductoDto;
import com.kevin.apirestproductos.model.entity.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> getAllProducts();
    Producto saveProduct(ProductoDto productoDto);
    Producto findById(Integer id);
    void deleteProduct(Producto producto);
    boolean existId(Integer id);

}
