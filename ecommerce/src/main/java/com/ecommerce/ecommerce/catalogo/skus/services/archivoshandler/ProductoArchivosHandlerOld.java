package com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.productos.dto.ProductoDTOOld;

/**
 * Esta clase se encarga de recibir y manejar los distintos archivos que se pueden
 * recibir para el manejo de productos (alta o actualizacion de productos).
 */
@Deprecated
public interface ProductoArchivosHandlerOld {

    /**
     * Recibe un archivo en formato .csv y convierte los datos en un listado
     * de Productos.
     * @param archivo Multipart file .csv con productos.
     * @return List listado de productos.
     */
    List<ProductoDTOOld> recibirCsv(MultipartFile archivo);

    /**
     * Recibe un archivo en formato .xlxs y convierte los datos en un listado
     * de Productos.
     * @param archivo Multipart file .xlxs con productos.
     * @return List listado de productos.
     */
    List<ProductoDTOOld> recibirExcel(MultipartFile archivo);

    /**
     * Recibe un archivo en formato .xlxs con productos YA EXISTENTES, con solo
     * datos que se utilizarán para la actualización masiva de disponibilidad o
     * nombres (futuramente tambien precio).
     * @param archivo Multipart file .xlxs con productos.
     * @return List listado de productos.
     */
    List<ProductoDTOOld> recibirExcelActualizarStock(MultipartFile archivo);
}
