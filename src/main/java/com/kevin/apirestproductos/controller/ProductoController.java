package com.kevin.apirestproductos.controller;

import com.kevin.apirestproductos.model.dto.ProductoDto;
import com.kevin.apirestproductos.model.entity.Producto;
import com.kevin.apirestproductos.model.payload.MensajeResponse;
import com.kevin.apirestproductos.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {
    @Autowired
    private IProductoService productoService;

    @GetMapping("/productos")
    public ResponseEntity<?> getAllProduct(){
        List<Producto> listProduct = productoService.getAllProducts();
        if(listProduct==null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("La lista es nula")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND
            );
        }
        if(listProduct.isEmpty()){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro de productos es 0")
                            .object(null)
                            .build()
                    , HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Lista de productos")
                        .object(listProduct)
                        .build()
                , HttpStatus.OK
        );
    }
    @GetMapping(value = "/producto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showById(@PathVariable Integer id){
        try{
            Producto producto = productoService.findById(id);

            if(producto==null){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Producto no encontrado")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND
                );
            }

            ProductoDto productoDto = ProductoDto.builder()
                    .id(producto.getId())
                    .nombre(producto.getNombre())
                    .marca(producto.getMarca())
                    .precioVenta(producto.getPrecioVenta())
                    .fechaIngreso(producto.getFechaIngreso())
                    .precioCompra(producto.getPrecioCompra())
                    .descripcion(producto.getDescripcion())
                    .build();
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("")
                            .object(productoDto)
                            .build()
                    , HttpStatus.OK
            );
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(dataAccessException.getMessage())
                            .object(null)
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }

    }
    @PostMapping("/producto")
    public ResponseEntity<?> saveProduct(@RequestBody ProductoDto productoDto){
        Producto productoSave = null;
        try{
            productoSave = productoService.saveProduct(productoDto);
            productoDto = ProductoDto.builder()
                    .id(productoSave.getId())
                    .nombre(productoSave.getNombre())
                    .marca(productoSave.getMarca())
                    .descripcion(productoSave.getDescripcion())
                    .precioCompra(productoSave.getPrecioCompra())
                    .precioVenta(productoSave.getPrecioVenta())
                    .fechaIngreso(productoSave.getFechaIngreso())
                    .build();
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Producto guardado con exito")
                            .object(productoDto)
                            .build()
                    , HttpStatus.CREATED
            );
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(dataAccessException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        try {
            Producto productoDelete = productoService.findById(id);
            productoService.deleteProduct(productoDelete);
            return new ResponseEntity<>("Producto Eliminado correctamente", HttpStatus.OK);
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(dataAccessException.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    @PutMapping(value = "/producto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody ProductoDto productoDto, @PathVariable Integer id){
        try{
            if(productoService.existId(id)){
                if(!productoDto.getId().equals(id)){
                    return new ResponseEntity<>(
                            MensajeResponse.builder()
                                    .mensaje("El atributo no coinciden")
                                    .object(null)
                                    .build()
                            ,HttpStatus.CONFLICT
                    );
                }

                Producto productoActualizar = productoService.saveProduct(productoDto);

                productoDto = ProductoDto.builder()
                        .id(productoActualizar.getId())
                        .marca(productoActualizar.getMarca())
                        .nombre(productoActualizar.getNombre())
                        .precioVenta(productoActualizar.getPrecioVenta())
                        .precioCompra(productoDto.getPrecioCompra())
                        .fechaIngreso(productoActualizar.getFechaIngreso())
                        .descripcion(productoDto.getDescripcion())
                        .build();
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Actualizado correctamente")
                                .object(productoDto)
                                .build()
                        , HttpStatus.CREATED
                );
            }else{
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El producto a actualizar no coincide")
                                .object(productoDto)
                                .build()
                        , HttpStatus.INTERNAL_SERVER_ERROR
                );
            }
        }catch (DataAccessException dataAccessException){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(dataAccessException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }
}
