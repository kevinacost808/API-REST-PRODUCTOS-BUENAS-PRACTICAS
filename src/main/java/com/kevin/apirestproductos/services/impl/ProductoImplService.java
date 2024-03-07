package com.kevin.apirestproductos.services.impl;

import com.kevin.apirestproductos.model.dao.ProductoDao;
import com.kevin.apirestproductos.model.dto.ProductoDto;
import com.kevin.apirestproductos.model.entity.Producto;
import com.kevin.apirestproductos.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoImplService implements IProductoService {
    @Autowired
    private ProductoDao productoDao;

    @Transactional(readOnly = true)
    @Override
    public List<Producto> getAllProducts() {
        return (List) productoDao.findAll();
    }

    @Transactional
    @Override
    public Producto saveProduct(ProductoDto productoDto) {
        Producto producto = Producto.builder()
                .id(productoDto.getId())
                .nombre(productoDto.getNombre())
                .marca(productoDto.getMarca())
                .descripcion(productoDto.getDescripcion())
                .precioCompra(productoDto.getPrecioCompra())
                .precioVenta(productoDto.getPrecioVenta())
                .fechaIngreso(productoDto.getFechaIngreso())
                .build();
        return productoDao.save(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findById(Integer id) {
        return productoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteProduct(Producto producto) {
        productoDao.delete(producto);
    }

    @Override
    public boolean existId(Integer id) {
        return productoDao.existsById(id);
    }
}
