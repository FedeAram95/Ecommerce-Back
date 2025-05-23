package com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;

/**
 * Clase que se encarga de generar archivos plantilla para la actualización masiva de
 * precio y disponibilidad de skus.
 * <br>
 * Interfaz que será realizada por aquellas clases que tengan su propia implementación para
 * la generación de archivos de actualización masiva de skus.
 */
public interface SkuArchivosGenerador {

    /**
     * Método que genera el archivo a partir de un listado de {@link Sku}s.
     * @param skus Listado con los skus que estarán en el archivo.
     * @return archivo.
     */
    ByteArrayInputStream generarArchivo(List<Sku> skus) throws IOException;
}
