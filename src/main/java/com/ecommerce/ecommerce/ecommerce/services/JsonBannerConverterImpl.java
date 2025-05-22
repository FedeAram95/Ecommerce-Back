package com.ecommerce.ecommerce.ecommerce.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.ecommerce.dto.BannerDto;
import com.ecommerce.ecommerce.ecommerce.exceptions.BannerException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonBannerConverterImpl implements JsonBannerConverter {

    @Override
    public BannerDto getJsonFromDto(String bannerDto) {
        BannerDto bannerJson;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            bannerJson = objectMapper.readValue(bannerDto, BannerDto.class);
        } catch (IOException e) {
            throw new BannerException("Error al convertir banner dto en json: ".concat(e.getMessage()));
        }

        return bannerJson;
    }
}
