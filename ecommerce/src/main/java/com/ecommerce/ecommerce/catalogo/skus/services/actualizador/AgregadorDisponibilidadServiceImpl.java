package com.ecommerce.ecommerce.catalogo.skus.services.actualizador;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.exceptions.SkuException;
import com.ecommerce.ecommerce.catalogo.skus.services.SkuService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AgregadorDisponibilidadServiceImpl implements AgregadorDisponibilidadService {

    private final SkuService skuService;

    @Override
    public Sku agregarDisponibilidad(Long skuId, Integer disponibilidadAgregada) {
        if (disponibilidadAgregada <= 0)
            throw new SkuException("La disponibilidad a agregar no puede ser menor o igual a 0");

        Sku skuActual = this.skuService.findById(skuId);
        Integer dispActual = skuActual.getDisponibilidad();

        skuActual.setDisponibilidad(dispActual + disponibilidadAgregada);
        return this.skuService.save(skuActual);
    }
}
