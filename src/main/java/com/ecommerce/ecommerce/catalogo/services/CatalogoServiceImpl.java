package com.ecommerce.ecommerce.catalogo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.SubcategoriaRepository;
import com.ecommerce.ecommerce.catalogo.productos.entities.Marca;
import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.entities.PropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.productos.repositories.MarcaRepository;
import com.ecommerce.ecommerce.catalogo.productos.repositories.ProductoRepository;
import com.ecommerce.ecommerce.catalogo.productos.services.BuscadorProductosService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final ProductoRepository productoRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final MarcaRepository marcaRepository;
    private final BuscadorProductosService buscadorProductosService;


    @Override
    public Map<String, Object> buscarProductos(String termino) {
        Map<String, Object> resultadoBusqueda = new HashMap<>();
        List<Producto> productos = this.buscadorProductosService.buscarProductos(termino);
        List<Marca> marcas = this.buscadorProductosService.marcasDeProductosEncontrados(termino);
        List<Subcategoria> subcategorias = this.buscadorProductosService.subcategoriasDeProductosEncontrados(termino);
        List<PropiedadProducto> propiedades = this.buscadorProductosService.propiedadesDeProductosEncontrados(termino);

        resultadoBusqueda.put("totalProductos", productos.size());
        resultadoBusqueda.put("productos", productos);
        resultadoBusqueda.put("marcas", marcas);
        resultadoBusqueda.put("subcategorias", subcategorias);
        resultadoBusqueda.put("propiedades", propiedades);
        return resultadoBusqueda;
    }


    @Override
    public List<Marca> listarMarcas() {
        return this.marcaRepository.findAll();
    }


    @Override
    public List<Producto> obtenerProductosDestacados() {
        return this.productoRepository
                .findAllByDestacadoIsTrueAndActivoIsTrue();
    }


    @Override
    public Producto obtenerProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new ProductoException("No existe el producto con id: " + id));
    }


    @Override
    public List<Producto> productosPrecioMenorMayor() {
        return this.productoRepository.findAllByOrderByPrecioAsc();
    }


    @Override
    public List<Producto> productosPrecioMayorMenor() {
        return this.productoRepository.findAllByOrderByPrecioDesc();
    }


    @Override
    public List<Producto> productosPorSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new ProductoException("No se encontró la categoria con id: " + subcategoriaId));

        return this.productoRepository.findAllBySubcategoriaAndActivoIsTrue(subcategoria);
    }


    @Override
    public List<Producto> productosPorMarca(Long marcaId) {
        Marca marca = this.marcaRepository.findById(marcaId)
                .orElseThrow(() -> new ProductoException("No se encontró la marca con id: " + marcaId));

        return this.productoRepository.findAllByMarcaAndActivoIsTrue(marca);
    }

    @Override
    public List<Producto> productosPorPrecio(Double precioMax) {
        return this.productoRepository.findAllByPrecioBetween(0.00, precioMax);
    }
}
