package com.ecommerce.ecommerce.catalogo.skus.services.actualizador;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler.SkuArchivosHandler;

/**
 * Servicio que se encarga de la actualización masiva de precios y disponibilidad, tanto para productos,
 * como para sus skus.
 * <br>
 * Sus implementaciones son responsables de actualizaciones masivas a través de archivos (excel, csv, etc...).
 */
public interface ActualizadorMasivoService {

    /**
     * Toma un archivo con los datos de los precios/disponibilidad de skus actualizados, y ejecuta la
     * actualización para todos ellos, guardandolos en la base de datos ya actualizados.
     * <br>
     * Requiere de un implementador para importar el archivo (excel/csv/etc).
     * @param skuArchivosHandler {@link SkuArchivosHandler} que realizará la importación.
     * @param file {@link MultipartFile} con el archivo a importar.
     * @return Lista de skus actualizados.
     */
    List<Sku> actualizarPreciosDisponibilidadSkus(SkuArchivosHandler skuArchivosHandler, MultipartFile file);
}
