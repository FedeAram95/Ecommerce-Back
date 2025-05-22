package com.ecommerce.ecommerce.catalogo.productos.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalogo.productos.entities.Marca;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.productos.repositories.MarcaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    @Override
    public Marca crearMarca(Marca marca) {
        Marca marcaNueva = Marca.builder()
                .nombre(marca.getNombre())
                .build();

        return this.save(marcaNueva);
    }

    @Override
    public List<Marca> obtenerMarcas() {
        return this.findAll();
    }

    @Override
    public Marca obtenerMarca(Long id) {
        return this.findById(id);
    }

    @Override
    public Marca actualizar(Marca marca, Long id) {
        Marca marcaActual = this.obtenerMarca(id);

        marcaActual.setNombre(marca.getNombre());
        return this.save(marcaActual);
    }

    @Override
    public void eliminarMarca(Marca marca) {
        this.delete(marca);
    }

    @Override
    public void eliminarMarca(Long marcaId) {
        this.deleteById(marcaId);
    }


    @Override
    public List<Marca> findAll() {
        return this.marcaRepository.findAll();
    }


    @Override
    public Marca findById(Long aLong) {
        return this.marcaRepository.findById(aLong)
                .orElseThrow(() -> new ProductoException("Marca no existente con id: " + aLong));
    }

    @Transactional
    @Override
    public Marca save(Marca object) {
        return this.marcaRepository.save(object);
    }

    @Transactional
    @Override
    public void delete(Marca object) {
        this.deleteById(object.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        if (this.marcaRepository.findById(aLong).isEmpty())
            throw new ProductoException("No existe la marca con id: " + aLong);

        try {
            this.marcaRepository.deleteById(aLong);
        } catch (DataIntegrityViolationException e) {
            throw new ProductoException("No se pudo eliminar la marca con id ".concat(String.valueOf(aLong)) +
                    " ya que tiene referencias con otros objetos : " + e.getMessage());
        }

    }
}
