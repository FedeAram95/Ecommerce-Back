package com.ecommerce.ecommerce.imagenes.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.imagenes.entities.Imagen;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public Imagen subirImagen(MultipartFile archivo) {
        try {
            String filename = archivo.getOriginalFilename();
            Path filepath = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(filepath.getParent());
            Files.write(filepath, archivo.getBytes());

            return Imagen.builder()
                    .path(filename)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen", e);
        }
    }

    @Override
    public byte[] descargarImagen(Imagen imagen) {
        try {
            Path filepath = Paths.get(UPLOAD_DIR + imagen.getPath());
            return Files.readAllBytes(filepath);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer la imagen", e);
        }
    }

    @Override
    public Imagen findByPath(String imagePath) {
        Path filepath = Paths.get(UPLOAD_DIR + imagePath);
        if (Files.exists(filepath)) {
            return Imagen.builder().path(imagePath).build();
        } else {
            throw new RuntimeException("Imagen no encontrada: " + imagePath);
        }
    }

    @Override
    public boolean eliminarImagen(Imagen imagen) {
        try {
            Path filepath = Paths.get(UPLOAD_DIR + imagen.getPath());
            return Files.deleteIfExists(filepath);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo eliminar la imagen", e);
        }
    }
}
