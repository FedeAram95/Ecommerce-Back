package com.ecommerce.ecommerce.catalogo.productos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.SubcategoriaRepository;
import com.ecommerce.ecommerce.catalogo.productos.entities.Marca;
import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.entities.PropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.repositories.MarcaRepository;
import com.ecommerce.ecommerce.catalogo.productos.repositories.ProductoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscadorProductosServiceImpl implements BuscadorProductosService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final SubcategoriaRepository subcategoriaRepository;


    @Override
    public List<Producto> buscarProductos(String termino) {
        List<Producto> productosTotal = new ArrayList<>();

        List<Producto> productosEncontradosPorNombre = this.encontrarProductosPorNombre(termino);
        List<Producto> productosEncontradosPorMarca = this.encontrarProductosPorMarca(termino);
        List<Producto> productosEncontradosPorSubcategoria = this.encontrarProductosPorSubcategoria(termino);

        if (productosEncontradosPorNombre.size() != 0) productosTotal.addAll(productosEncontradosPorNombre);
        if (productosEncontradosPorMarca.size() != 0) productosTotal.addAll(productosEncontradosPorMarca);
        if (productosEncontradosPorSubcategoria.size() != 0) productosTotal.addAll(productosEncontradosPorSubcategoria);



        return this.eliminarDuplicados(productosTotal);
    }


    @Override
    public List<Marca> marcasDeProductosEncontrados(String termino) {
        List<Producto> productos = this.buscarProductos(termino);
        List<Marca> marcasDeProductos = new ArrayList<>();

        for (Producto producto: productos) {
            marcasDeProductos.add(producto.getMarca());
        }

        return this.eliminarDuplicados(marcasDeProductos);
    }


    @Override
    public List<Subcategoria> subcategoriasDeProductosEncontrados(String termino) {
        List<Producto> productos = this.buscarProductos(termino);
        List<Subcategoria> subcategoriasDeProductos = new ArrayList<>();

        for (Producto producto: productos) {
            subcategoriasDeProductos.add(producto.getSubcategoria());
        }

        return this.eliminarDuplicados(subcategoriasDeProductos);
    }


    @Override
    public List<PropiedadProducto> propiedadesDeProductosEncontrados(String termino) {
        List<Producto> productos = this.buscarProductos(termino);
        List<PropiedadProducto> propiedadesDeProductos = new ArrayList<>();

        for (Producto producto: productos) {
            propiedadesDeProductos.addAll(producto.getPropiedades());
        }

        return this.eliminarDuplicados(propiedadesDeProductos);
    }

    private List<Producto> encontrarProductosPorNombre(String termino) {
        return this.productoRepository
                .findAllByNombreContainingIgnoringCaseAndActivoIsTrueOrderByNombreAsc(termino);
    }

    private List<Producto> encontrarProductosPorMarca(String termino) {
        List<Producto> productos = new ArrayList<>();
        List<Marca> marcas = this.marcaRepository.findAllByNombreContainingIgnoringCase(termino);
        /*
        this.marcaRepository.findByNombreContainingIgnoringCase(termino).ifPresent(marca -> productos
                .addAll(this.productoRepository.findAllByMarcaAndActivoIsTrue(marca)));
         */

        for (Marca marca: marcas) {
            productos.addAll(this.productoRepository.findAllByMarcaAndActivoIsTrue(marca));
        }

        return productos;
    }

    private List<Producto> encontrarProductosPorSubcategoria(String termino) {
        List<Producto> productos = new ArrayList<>();
        List<Subcategoria> subcategorias = this.subcategoriaRepository.findAllByNombreContainingIgnoringCase(termino);
        /*
        this.subcategoriaRepository.findByNombreContainingIgnoringCase(termino).ifPresent(subcategoria -> productos
                .addAll(this.productoRepository.findAllBySubcategoriaAndActivoIsTrue(subcategoria)));
         */

        for (Subcategoria subcategoria: subcategorias) {
            productos.addAll(this.productoRepository.findAllBySubcategoriaAndActivoIsTrue(subcategoria));
        }

        return productos;
    }

    private <T> List<T> eliminarDuplicados(List<T> lista) {
        ArrayList<T> nuevaLista = new ArrayList<>();

        for (T elemento: lista) {
            if (!nuevaLista.contains(elemento))
                nuevaLista.add(elemento);
        }

        return nuevaLista;
    }
}
