package com.ecommerce.ecommerce.catalogo.skus.services.validadores;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.utils.validators.ValidadorExtensionArchivo;

/**
 * Implementación de archivos EXCEL de {@link ValidadorExtensionArchivo}.
 */
@Service
@Qualifier("validadorExtensionesExcel")
public class ValidadorExtensionExcel implements ValidadorExtensionArchivo {

    @Override
    public boolean validar(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        List<String> extensionesValidas = Arrays.asList("xlsx", "xls");

        return extensionesValidas.contains(fileExtension);
    }
}
