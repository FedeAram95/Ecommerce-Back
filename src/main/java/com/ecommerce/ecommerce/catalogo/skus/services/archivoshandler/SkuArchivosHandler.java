package com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.skus.dto.SkuDto;

/**
 * Clase que se encarga de importar un archivo y convertirlo según sea necesario para
 * la administración de productos(actualización precio/disponibilidad).
 * <br>
 * Sus implementaciones dependen del tipo de archivo, por ej.: Excel o CSV
 */
public interface SkuArchivosHandler {

    /**
     * Método responsable de importar un archivo y convertirlo en un List de
     * {@link SkuDto}.
     * <br>
     * Dichos objetos pueden ser manipulados para la actualización masiva de
     * precio/disponibilidad de skus.
     * @param file {@link MultipartFile} archivo con los datos de los productos a importar.
     * @return {@link List<SkuDto>} con los objetos importados y mapeados.
     */
    List<SkuDto> importar(MultipartFile file);
}
