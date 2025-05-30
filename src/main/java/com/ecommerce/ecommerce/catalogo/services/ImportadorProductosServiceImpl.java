package com.ecommerce.ecommerce.catalogo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.SubcategoriaRepository;
import com.ecommerce.ecommerce.catalogo.productos.dto.ProductoDTOOld;
import com.ecommerce.ecommerce.catalogo.productos.entities.Marca;
import com.ecommerce.ecommerce.catalogo.productos.entities.Producto;
import com.ecommerce.ecommerce.catalogo.productos.entities.UnidadMedida;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.productos.repositories.MarcaRepository;
import com.ecommerce.ecommerce.catalogo.productos.repositories.ProductoRepository;
import com.ecommerce.ecommerce.catalogo.productos.repositories.UnidadMedidaRepository;
import com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler.ProductoArchivosHandlerOld;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Deprecated
public class ImportadorProductosServiceImpl implements ImportadorProductosService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;
    private final ProductoArchivosHandlerOld productoArchivosHandlerOld;

    @Transactional
    @Override
    public List<Producto> importarDeCSV(MultipartFile archivo) {
        List<ProductoDTOOld> dtos = this.productoArchivosHandlerOld.recibirCsv(archivo);
        System.out.println(dtos);
        List<Producto> productos = new ArrayList<>();

        for (ProductoDTOOld productoDTOOld : dtos) {
            productos.add(this.mapProductoDto(productoDTOOld));
        }

        return this.productoRepository.saveAll(productos);
    }

    @Transactional
    @Override
    public List<Producto> importarDeExcel(MultipartFile archivo) {
        List<ProductoDTOOld> dtos = this.productoArchivosHandlerOld.recibirExcel(archivo);
        System.out.println(dtos);
        List<Producto> productos = new ArrayList<>();

        for (ProductoDTOOld productoDTOOld : dtos) {
            productos.add(this.mapProductoDto(productoDTOOld));
        }

        return this.productoRepository.saveAll(productos);
    }

    @Transactional
    @Override
    public List<Producto> actualizarStockDeExcel(MultipartFile archivo) {
        List<ProductoDTOOld> dtos = this.productoArchivosHandlerOld.recibirExcelActualizarStock(archivo);
        System.out.println(dtos);
        List<Producto> productosActualizados = new ArrayList<>();

        for (ProductoDTOOld productoDTOOld : dtos) {
            Producto productoBD = this.productoRepository.getOne(productoDTOOld.getId());

            productoBD.setDisponibilidadGeneral(productoDTOOld.getDisponibilidad());
            Producto productoActuzliado = this.productoRepository.save(productoBD);
            productosActualizados.add(productoActuzliado);
        }

        return productosActualizados;
    }

    private Producto mapProductoDto(ProductoDTOOld productoDTOOld) {
        Subcategoria subcategoria = this.subcategoriaRepository.findById(productoDTOOld.getSubcategoriaId())
                .orElseThrow(() -> new ProductoException("No se encontro la categoría con id: " +
                        productoDTOOld.getSubcategoriaId()));

        Marca marca = this.marcaRepository.findById(productoDTOOld.getMarcaId())
                .orElseThrow(() -> new ProductoException("No se encontro la marca con id: " +
                        productoDTOOld.getMarcaId()));

        UnidadMedida unidadMedida = this.unidadMedidaRepository.findById(productoDTOOld.getUnidadMedidaId())
                .orElseThrow(() -> new ProductoException("No se encontro la unidad de medida con id: " +
                        productoDTOOld.getUnidadMedidaId()));

        return Producto.builder()
                .nombre(productoDTOOld.getNombre())
                .descripcion(productoDTOOld.getDescripcion())
                .precio(productoDTOOld.getPrecio())
                .disponibilidadGeneral(productoDTOOld.getDisponibilidad())
                .subcategoria(subcategoria)
                .marca(marca)
                .unidadMedida(unidadMedida)
                .activo(true)
                .fechaCreacion(new Date())
                .destacado(true)
                .foto(null)
                .build();
    }
}
