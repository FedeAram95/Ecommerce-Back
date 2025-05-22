package com.ecommerce.ecommerce.catalogo.categorias.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.catalogo.categorias.entities.Categoria;
import com.ecommerce.ecommerce.catalogo.categorias.entities.Subcategoria;
import com.ecommerce.ecommerce.catalogo.categorias.exceptions.CategoriaException;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.CategoriaRepository;
import com.ecommerce.ecommerce.catalogo.categorias.repositories.SubcategoriaRepository;
import com.ecommerce.ecommerce.imagenes.entities.Imagen;
import com.ecommerce.ecommerce.imagenes.services.ImageService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public Categoria crear(Categoria categoria) {
        Categoria categoriaNueva = Categoria.builder()
                .nombre(categoria.getNombre())
                .subcategorias(new ArrayList<>())
                .build();

        return this.categoriaRepository.save(categoriaNueva);
    }

    @Override
    @Transactional
    public Categoria actualizar(Categoria categoria, Long id) {
        Categoria categoriaActual = this.obtenerCategoria(id);

        categoriaActual.setNombre(categoria.getNombre());

        return this.categoriaRepository.save(categoriaActual);
    }

    @Override

    public List<Categoria> obtenerCategorias() {
        return this.categoriaRepository.findAllByOrderByNombreAsc();
    }

    @Override

    public Categoria obtenerCategoria(Long id) {
        return this.categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoría no existente con id: " + id));
    }

    @Override

    public List<Subcategoria> obtenerSubcategorias(Long categoriaId) {
        Categoria categoria = this.categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaException("Categoria no existente con id: " + categoriaId));


        return categoria.getSubcategorias();
    }

    @Override

    public Subcategoria obtenerSubcategoriaDeCategoria(Long categoriaId, Long subcategoriaId) {
        Categoria categoria = this.obtenerCategoria(categoriaId);
        Subcategoria subcategoria = null;

        boolean existeSub = false;
        for (Subcategoria sub: categoria.getSubcategorias()) {
            if (sub.getId().equals(subcategoriaId)) {
                existeSub = true;
                subcategoria = sub;
                break;
            }
        }

        if (!existeSub) throw new CategoriaException("La subcategoría no pertenece a la categoría: ".concat(categoria.getNombre()));

        return subcategoria;
    }

    @Transactional
    @Override
    public Imagen subirFotoCategoria(Long categoriaId, MultipartFile foto) {
        Categoria categoria = this.obtenerCategoria(categoriaId);

        if (categoria.getFoto() != null)
            this.eliminarFotoCategoria(categoria.getId());

        Imagen fotoCategoria = this.imageService.subirImagen(foto);
        categoria.setFoto(fotoCategoria);
        this.categoriaRepository.save(categoria);
        return fotoCategoria;
    }


    @Override
    public byte[] obtenerFotoCategoria(Long categoriaId) {
        Categoria categoria = this.obtenerCategoria(categoriaId);
        Imagen fotoCategoria = categoria.getFoto();

        if (fotoCategoria == null) throw new CategoriaException("La categoría: " + categoria.getNombre()
                + " no tiene foto");

        return this.imageService.descargarImagen(fotoCategoria);
    }


    @Override
    public String obtenerPathFotoCategoria(Long categoriaId) {
        Categoria categoria = this.obtenerCategoria(categoriaId);
        return categoria.getFoto().getPath();
    }

    @Transactional
    @Override
    public void eliminarFotoCategoria(Long categoriaId) {
        Categoria categoria = this.obtenerCategoria(categoriaId);

        if (categoria.getFoto() == null) throw new CategoriaException("La categoría: " + categoria.getNombre()
                + " no tiene foto");

        Imagen fotoCategoria = categoria.getFoto();
        categoria.setFoto(null);
        this.categoriaRepository.save(categoria);
        this.imageService.eliminarImagen(fotoCategoria);
    }


    @Override
    public Categoria obtenerCategoriaPorSubcategoria(Subcategoria subcategoria) {
        return this.categoriaRepository.findBySubcategoriasContaining(subcategoria)
                .orElseThrow(() -> new CategoriaException("La subcategoría no pertenece a ninguna categoría"));
    }
}
