package com.ecommerce.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.ecommerce.dto.BannerDto;

/**
 * Servicio que se encarga de convertir {@link BannerDto} en Json.
 */
public interface JsonBannerConverter {

    /**
     * Convierte {@link BannerDto} en objeto Json.
     * @param bannerDto String del Banner dto a convertir.
     * @return BannerDto convertido en Json.
     */
    BannerDto getJsonFromDto(String bannerDto);
}
