package com.ecommerce.ecommerce.imagenes.services.validadores;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.utils.validators.ValidadorExtensionArchivo;

/**
 * Implementación para archivos de imágenes de {@link ValidadorExtensionArchivo}.
 */
@Service
@Qualifier("validadorExtensionImages")
public class ValidadorExtensionImages implements ValidadorExtensionArchivo {

    @Override
    public boolean validar(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        List<String> extensionesValidas = Arrays.asList("jpeg", "jpg", "png", "mp4", "pdf", "webp");

        return extensionesValidas.contains(fileExtension);
    }
}
