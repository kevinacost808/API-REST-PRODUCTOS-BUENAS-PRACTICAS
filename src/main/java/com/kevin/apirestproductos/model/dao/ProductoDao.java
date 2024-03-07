package com.kevin.apirestproductos.model.dao;

import com.kevin.apirestproductos.model.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto, Integer> {
}
