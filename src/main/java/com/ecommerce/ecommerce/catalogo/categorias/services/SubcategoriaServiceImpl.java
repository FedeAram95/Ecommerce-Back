package com.ecommerce.ecommerce.catalogo.categorias.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.categorias.exceptions.CategoriaException;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.SubcategoriaRepository;
import com.ecommerce.ecommerce.catalogo.productos.entities.PropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;
import com.ecommerce.ecommerce.imagenes.services.ImageService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubcategoriaServiceImpl implements SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;
    private final ImageService imageService;

    @Transactional
    @Override
    public Subcategoria crear(Subcategoria subcategoria) {
        Subcategoria nuevaSubcategoria = Subcategoria.builder()
                .codigo(subcategoria.getCodigo())
                .nombre(subcategoria.getNombre())
                .propiedades(new ArrayList<>()).build();
        return this.subcategoriaRepository.save(nuevaSubcategoria);
    }


    @Override
    public List<Subcategoria> obtenerSubcategorias() {
        return this.subcategoriaRepository.findAll();
    }

    @Override
    public Subcategoria obtenerSubcategoria(Long subcategoriaId) {
        return this.subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new ProductoException("No existe subcategoria con id: " + subcategoriaId));
    }


    @Override
    public PropiedadProducto obtenerPropiedad(Long subcategoriaId, Long propiedadId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        PropiedadProducto propiedadProducto = null;

        boolean existePropiedad = false;
        for (PropiedadProducto propiedad: subcategoria.getPropiedades()) {
            if (propiedad.getId().equals(propiedadId)) {
                propiedadProducto = propiedad;
                existePropiedad = true;
                break;
            }
        }

        if (!existePropiedad) {
            throw new ProductoException("La propiedad requerida no pertenece a la subcategoria: " +
                    subcategoria.getNombre());
        }

        return propiedadProducto;
    }


    @Override
    public List<PropiedadProducto> obtenerPropiedadesSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        return subcategoria.getPropiedades();
    }

    @Transactional
    @Override
    public Imagen subirFotoSubcategoria(Long subcategoriaId, MultipartFile foto) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        if (subcategoria.getFoto() != null)
            this.eliminarFotoSubcategoria(subcategoria.getId());

        Imagen fotoSubcategoria = this.imageService.subirImagen(foto);
        subcategoria.setFoto(fotoSubcategoria);
        this.subcategoriaRepository.save(subcategoria);
        return fotoSubcategoria;
    }


    @Override
    public byte[] obtenerFotoSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        Imagen fotoSubcategoria = subcategoria.getFoto();

        if (fotoSubcategoria == null) throw new CategoriaException("La subcategoría: " + subcategoria.getNombre()
                + " no tiene foto");

        return this.imageService.descargarImagen(fotoSubcategoria);
    }


    @Override
    public String obtenerPathFotoSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);
        return subcategoria.getFoto().getPath();
    }

    @Transactional
    @Override
    public void eliminarFotoSubcategoria(Long subcategoriaId) {
        Subcategoria subcategoria = this.obtenerSubcategoria(subcategoriaId);

        if (subcategoria.getFoto() == null) throw new CategoriaException("La subcategoría: " + subcategoria.getNombre()
                + " no tiene foto");

        Imagen fotoSubcategoria = subcategoria.getFoto();
        subcategoria.setFoto(null);
        this.subcategoriaRepository.save(subcategoria);
        this.imageService.eliminarImagen(fotoSubcategoria);
    }
}
