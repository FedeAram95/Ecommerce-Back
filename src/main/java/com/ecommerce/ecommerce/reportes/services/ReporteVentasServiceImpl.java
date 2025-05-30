package com.ecommerce.ecommerce.reportes.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.services.SkuService;
import com.ecommerce.ecommerce.operaciones.entities.DetalleOperacion;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.repositories.OperacionRepository;
import com.ecommerce.ecommerce.ventas.dto.VentaPayload;
import com.ecommerce.ecommerce.ventas.services.VentaService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class ReporteVentasServiceImpl implements ReporteVentasService {

    private final OperacionRepository operacionRepository;
    private final SkuService skuService;

    private final VentaService ventaService;

    @Override
    public List<VentaPayload> generarReporteVentas(String estado, Date fechaDesde, Date fechaHasta) {
        List<VentaPayload> ventas = new ArrayList<>();

        if (estado != null) {
            ventas = this.ventaService.ventasEstado(estado);
        }

        if (fechaDesde != null) {
            ventas = this.ventaService.ventasFecha(fechaDesde, fechaHasta);
        }

        if (estado == null && fechaDesde == null) {
            ventas = this.ventaService.listarVentas();
        }

        if (estado != null && fechaDesde != null) {
            ventas = this.ventaService.ventasFechaYEstado(estado, fechaDesde, fechaHasta);
        }

        return ventas;
    }


    @Override
    public Map<String, Object> generarReporteVentasSku(Long skuId) {
        Map<String, Object> reportData = new HashMap<>();

        Sku sku = this.skuService.obtenerSku(skuId);
        Integer vendidosTotal = 0;
        Double montoTotal = 0.00;
        List<Operacion> ventas = this.operacionRepository.findAll();

        for (Operacion venta: ventas) {
            List<DetalleOperacion> items = venta.getItems();

            for (DetalleOperacion item: items) {
                if (item.getSku().equals(sku)) {
                    vendidosTotal += item.getCantidad();
                    montoTotal += item.getSubtotal();
                }
            }
        }

        reportData.put("sku", sku);
        reportData.put("vendidosTotal", vendidosTotal);
        reportData.put("montoTotal", montoTotal);

        return reportData;
    }


    @Override
    public Map<String, Object> generarReporteVentasSku(Long skuId, Date fechaDesde, Date fechaHasta) {
        Map<String, Object> reportData = new HashMap<>();

        if (fechaHasta.before(fechaDesde))
            throw new RuntimeException("La fecha hasta debe ser superior a la fecha desde");

        Sku sku = this.skuService.obtenerSku(skuId);
        Integer vendidosTotal = 0;
        Double montoTotal = 0.00;

        List<Operacion> ventasFecha = this.operacionRepository.findAllByFechaOperacionBetween(fechaDesde, fechaHasta);

        log.info("ventas fecha --> " + ventasFecha);
        log.info("total -->" + ventasFecha.size());

        for (Operacion venta: ventasFecha) {
            List<DetalleOperacion> items = venta.getItems();

            for (DetalleOperacion item: items) {
                if (item.getSku().equals(sku)) {
                    vendidosTotal += item.getCantidad();
                    montoTotal += item.getSubtotal();
                }
            }
        }

        reportData.put("sku", sku);
        reportData.put("fechaDesde", fechaDesde);
        reportData.put("fechaHasta", fechaHasta);
        reportData.put("vendidosTotal", vendidosTotal);
        reportData.put("montoTotal", montoTotal);
        return reportData;
    }


    @Override
    public Map<String, Object> generarReporteVentasItemsTotales(Date fechaDesde, Date fechaHasta) {
        Map<String, Object> reportData = new HashMap<>();
        List<Operacion> ventasFecha = this.operacionRepository.findAllByFechaOperacionBetween(fechaDesde,
                fechaHasta);

        List<DetalleOperacion> items = new ArrayList<>();

        for (Operacion venta: ventasFecha) {
            items.addAll(venta.getItems());
        }

        reportData.put("items", items);
        return reportData;
    }
}
