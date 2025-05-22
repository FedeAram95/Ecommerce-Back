package com.ecommerce.ecommerce.catalogo.skus.services.actualizador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.skus.dto.SkuDto;
import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;
import com.ecommerce.ecommerce.catalogo.skus.services.SkuService;
import com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler.SkuArchivosHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ActualizadorMasivoServiceImpl implements ActualizadorMasivoService {

    private final SkuService skuService;

    @Override
    public List<Sku> actualizarPreciosDisponibilidadSkus(SkuArchivosHandler skuArchivosHandler, MultipartFile file) {
        List<SkuDto> skuDtos = skuArchivosHandler.importar(file);
        log.info(skuDtos.toString());
        List<Sku> skusActualizados = new ArrayList<>();

        for (SkuDto skuDto: skuDtos) {
            Sku skuBD = this.skuService.obtenerSku(skuDto.getId());

            this.skuService.actualizarPrecio(skuBD.getId(), skuDto.getPrecio());
            this.skuService.actualizarDisponibilidad(skuBD.getId(), skuDto.getDisponibilidad());

            skusActualizados.add(skuBD);
        }

        return skusActualizados;
    }
}
