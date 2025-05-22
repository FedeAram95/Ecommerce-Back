package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.productos.propiedades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.entities.PropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.services.CatalogoAdminService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class CrearPropiedadProductoController {

    private final CatalogoAdminService catalogoAdminService;

    /**
     * API para crear una nueva propiedad de producto (sin asignarse a ningun prod/subc)
     * URL: ~/api/catalogo/productos/propiedades
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param propiedadProducto {@link PropiedadProducto} nueva a crear.
     * @return ResponseEntity con los datos de la propiedad creada.
     */
    
    @PostMapping("/productos/propiedades")
    public ResponseEntity<?> crearPropiedadProducto(@RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProducto(propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * API para crear una nueva propiedad y asignarla automáticamente a un
     * {@link com.ecommerce.ecommerce.catalogo.productos.entities.Producto}, lo que, a su vez,
     * asigna automáticamente la propiedad a la {@link com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria}
     * del producto requerido.
     * URL: ~/api/catalogo/productos/1/propiedades
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param productoId Long id del producto.
     * @param propiedadProducto {@link PropiedadProducto} a crear.
     * @param result binding result con errores de request, si los tiene.
     * @return ResponseEntity con la propiedad creada.
     */
    
    @PostMapping("/productos/{productoId}/propiedades")
    public ResponseEntity<?> crearPropiedadProducto(@PathVariable Long productoId,
                                                    @Valid @RequestBody PropiedadProducto propiedadProducto,
                                                    BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", "Bad Request");
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProducto(productoId, propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Crea una nueva propiedad para una subcategoria requerida.
     * URL: ~/api/catalogo/subcategorias/1/propiedades
     * HttpMethod: POST
     * HttpStatus: CREATED
     * @param subcategoriaId Long id de la subcategoria a agregar la propiedad nueva.
     * @param propiedadProducto Propiedad nueva.
     * @return ResponseEntity con la subcategoria actualizada.
     */
    
    @PostMapping("/subcategorias/{subcategoriaId}/propiedades")
    public ResponseEntity<?> crearPropiedadProductoSubcategoria(@PathVariable Long subcategoriaId,
                                                                @RequestBody PropiedadProducto propiedadProducto) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = this.catalogoAdminService.crearPropiedadProductoSubcategoria(subcategoriaId, propiedadProducto);
        } catch (ProductoException e) {
            response.put("mensaje", "Error al crear nueva propiedad de producto para la subcategoria");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedadProducto", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
