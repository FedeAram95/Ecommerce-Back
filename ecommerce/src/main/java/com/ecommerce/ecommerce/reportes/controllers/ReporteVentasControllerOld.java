package com.ecommerce.ecommerce.reportes.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;
import com.ecommerce.ecommerce.operaciones.exceptions.OperacionException;
import com.ecommerce.ecommerce.reportes.services.ReporteVentasService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Deprecated
public class ReporteVentasControllerOld {

    private final ReporteVentasService reporteVentasService;

    @GetMapping("/reportes/ventas/skus/{skuId}")
    public ResponseEntity<?> generarReporteVentasSku(@PathVariable Long skuId) {
        return new ResponseEntity<>("En proceso de implementación...", HttpStatus.OK);
    }

    @GetMapping("/reportes/ventas/skus/{skuId}/entre")
    public ResponseEntity<?> generarReporteVentasSku(@PathVariable Long skuId,
                                                     @RequestParam Date fechaDesde,
                                                     @RequestParam Date fechaHasta) {
        return new ResponseEntity<>("En proceso de implementación...", HttpStatus.OK);
    }

    @GetMapping("/reportes/ventas/skus")
    public ResponseEntity<?> generarReporteVentasSkusTotales(@RequestParam Date fechaDesde,
                                                             @RequestParam Date fechaHasta) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> reporte;

        try {
            reporte = this.reporteVentasService.generarReporteVentasItemsTotales(fechaDesde, fechaHasta);
        } catch (OperacionException | SkuException e) {
            response.put("mensaje", "Error al generar el reporte");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("reporte", reporte);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
