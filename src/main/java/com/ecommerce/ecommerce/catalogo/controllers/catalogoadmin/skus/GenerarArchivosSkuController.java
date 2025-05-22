package com.ecommerce.ecommerce.catalogo.controllers.catalogoadmin.skus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;
import com.ecommerce.ecommerce.catalogo.skus.services.SkuExportadorService;
import com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler.SkuExcelGenerador;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/catalogo")
@AllArgsConstructor
public class GenerarArchivosSkuController {

    private final SkuExportadorService skuExportadorService;

    /**
     * API para generar y exportar archivo plantilla de todos los skus registrados en el sistema,
     * que servirá para la actualización masiva de precios/disponibilidad de los mismos.
     * URL: ~/api/catalogo/productos/skus/exportar/excel
     * HttpMethod: GET
     * HttpStatus: OK
     * @return ResponseEntity con el archivo generado, listando los skus.
     */
    
    @GetMapping("/productos/skus/exportar/excel")
    public ResponseEntity<?> exportarArchivoSkusExcel() {
        Map<String, Object> response = new HashMap<>();
        ByteArrayInputStream stream;

        try {
            stream = this.skuExportadorService.exportar(new SkuExcelGenerador());
        } catch (IOException | SkuException e) {
            response.put("mensaje", "Error al generar archivo con skus");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8")
                .header("Content-Disposition", "attachment;filename=Actualizacion-Skus.xlsx")
                .body(new InputStreamResource(stream));
    }

    /**
     * API para generar y exportar archivo plantilla con los skus pertenecientes a un producto
     * requerido, a través de su id, para actualizar de forma masiva el precio/disponibilidad
     * de los skus que el producto posee.
     * URL: ~/api/catalogo/productos/1/skus/exportar/excel
     * HttpMethod: GET
     * HttpStatus: OK
     * @param productoId Long id del producto a generar archivo con sus skus.
     * @return ResponseEntity con el archivo generado, listando los skus.
     */
    
    @GetMapping("/productos/{productoId}/skus/exportar/excel")
    public ResponseEntity<?> exportarArchivosSkusExcel(@PathVariable Long productoId) {
        Map<String, Object> response = new HashMap<>();
        ByteArrayInputStream stream;

        try {
            stream = this.skuExportadorService.exportar(new SkuExcelGenerador(), productoId);
        } catch (IOException | SkuException | ProductoException e) {
            response.put("mensaje", "Error al generar archivo con skus");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8")
                .header("Content-Disposition", "attachment;filename=Actualizacion-Skus.xlsx")
                .body(new InputStreamResource(stream));
    }

    /**
     * API que se utiliza para generar archivo plantilla en excel para la actualización de precios/disponibilidad de skus,
     * que son pertenecientes a productos que tienen cierta marca requerida, a través del id de la marca.
     * URL: ~/api/catalogo/productos/marcas/1/skus/exportar/excel
     * HttpMethod: GET
     * HttpStatus: OK
     * @param marcaId Long id de la marca.
     * @return ResponseEntity con archivo plantilla de skus.
     */
    
    @GetMapping("/productos/marcas/{marcaId}/skus/exportar/excel")
    public ResponseEntity<?> exportarArchivosSkusExcelPorMarca(@PathVariable Long marcaId) {
        Map<String, Object> response = new HashMap<>();
        ByteArrayInputStream stream;

        try {
            stream = this.skuExportadorService.exportarPorMarca(new SkuExcelGenerador(), marcaId);
        } catch (IOException | SkuException | ProductoException e) {
            response.put("mensaje", "Error al generar archivo con skus");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8")
                .header("Content-Disposition", "attachment;filename=Actualizacion-Skus.xlsx")
                .body(new InputStreamResource(stream));
    }

    /**
     * API que se utiliza para generar archivo plantilla en excel para la actualización de precios/disponibilidad de skus,
     * que son pertenecientes a productos que tienen cierta subcategoría requerida, a través del id de la subcategoría.
     * URL: ~/api/catalogo/productos/subcategorias/1/skus/exportar/excel
     * HttpMethod: GET
     * HttpStatus: OK
     * @param subcategoriaId Long id de la subcategoría.
     * @return ResponseEntity con archivo plantilla de skus.
     */
    
    @GetMapping("/productos/subcategorias/{subcategoriaId}/skus/exportar/excel")
    public ResponseEntity<?> exportarArchivosSkusExcelPorSubcategoria(@PathVariable Long subcategoriaId) {
        Map<String, Object> response = new HashMap<>();
        ByteArrayInputStream stream;

        try {
            stream = this.skuExportadorService.exportarPorSubcategoria(new SkuExcelGenerador(), subcategoriaId);
        } catch (IOException | SkuException | ProductoException e) {
            response.put("mensaje", "Error al generar archivo con skus");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8")
                .header("Content-Disposition", "attachment;filename=Actualizacion-Skus.xlsx")
                .body(new InputStreamResource(stream));
    }
}
