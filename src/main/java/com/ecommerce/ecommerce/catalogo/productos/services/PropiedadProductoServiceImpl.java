package com.ecommerce.ecommerce.catalogo.productos.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.catalogo.productos.entities.PropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.entities.ValorPropiedadProducto;
import com.ecommerce.ecommerce.catalogo.productos.exceptions.ProductoException;
import com.ecommerce.ecommerce.catalogo.productos.repositories.PropiedadProductoRepository;
import com.ecommerce.ecommerce.catalogo.productos.repositories.ValorPropiedadProductoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PropiedadProductoServiceImpl implements PropiedadProductoService {

    private final PropiedadProductoRepository propiedadProductoRepository;
    private final ValorPropiedadProductoRepository valorPropiedadProductoRepository;

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedadProducto) {
        List<ValorPropiedadProducto> valores = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(propiedadProducto.getValores())) valores.addAll(propiedadProducto.getValores());

        return this.save(PropiedadProducto.builder()
                .nombre(propiedadProducto.getNombre())
                .variable(propiedadProducto.isVariable())
                .valores(valores).build());
    }


    @Override
    public List<PropiedadProducto> obtenerPropiedadesProducto() {
        return this.findAll();
    }


    @Override
    public PropiedadProducto obtenerPropiedadProducto(Long propiedadId) {
        return this.findById(propiedadId);
    }

    @Transactional
    @Override
    public PropiedadProducto actualizarPropiedadProducto(Long propiedadId, PropiedadProducto propiedadProducto) {
        PropiedadProducto propiedadActual = this.obtenerPropiedadProducto(propiedadId);
        List<ValorPropiedadProducto> valores = propiedadActual.getValores();

        // Verificar que no se agreguen/quitar valores.
        if (propiedadProducto.getValores().size() != valores.size())
            throw new ProductoException("No se pueden eliminar/quitar valores aquí. Usar endpoint" +
                    " correspondiente");

        propiedadActual.setNombre(propiedadProducto.getNombre());

        if (propiedadProducto.getValores() != null) {
            valores = propiedadProducto.getValores();
        }
        propiedadActual.setValores(valores);
        return this.save(propiedadActual);
    }

    @Transactional
    @Override
    public void eliminarPropiedadProducto(Long propiedadId) {
        this.deleteById(propiedadId);
    }

    @Transactional
    @Override
    public PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        ValorPropiedadProducto nuevoValor = ValorPropiedadProducto.builder()
                .valor(valor.getValor()).build();

        propiedad.getValores().add(nuevoValor);
        return this.save(propiedad);
    }


    @Override
    public List<ValorPropiedadProducto> obtenerValoresDePropiedad(Long propiedadId) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        return propiedad.getValores();
    }

    @Transactional
    @Override
    public PropiedadProducto actualizarValorPropiedad(Long propiedadId, Long valorId, ValorPropiedadProducto valor) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        boolean existeValor = false;
        for (ValorPropiedadProducto valorPropiedadProducto: propiedad.getValores()) {
            if (valorPropiedadProducto.getId().equals(valorId)) {
                valorPropiedadProducto.setValor(valor.getValor());
                existeValor = true;
                break;
            }
        }

        if (!existeValor) throw new ProductoException("No existe el valor: '".concat(valor.getValor())
                .concat("' para la propiedad: '".concat(propiedad.getNombre())));

        return this.save(propiedad);
    }

    @Transactional
    @Override
    public void eliminarValorPropiedad(Long propiedadId, Long valorId) {
        PropiedadProducto propiedad = this.obtenerPropiedadProducto(propiedadId);

        boolean existeValor = false;
        for (ValorPropiedadProducto valorPropiedadProducto: propiedad.getValores()) {
            if (valorPropiedadProducto.getId().equals(valorId)) {
                this.valorPropiedadProductoRepository.deleteById(valorId);
                existeValor = true;
                break;
            }
        }

        if (!existeValor) throw new ProductoException("No existe el valor con id: '"
                .concat(String.valueOf(valorId)).concat("' para la propiedad: '".concat(propiedad.getNombre())));
    }


    @Override
    public List<PropiedadProducto> findAll() {
        return this.propiedadProductoRepository.findAll();
    }


    @Override
    public PropiedadProducto findById(Long aLong) {
        return this.propiedadProductoRepository.findById(aLong)
                .orElseThrow(() -> new ProductoException("No existe la propiedad producto con id: " + aLong));
    }

    @Transactional
    @Override
    public PropiedadProducto save(PropiedadProducto object) {
        return this.propiedadProductoRepository.save(object);
    }

    @Transactional
    @Override
    public void delete(PropiedadProducto object) {
        this.deleteById(object.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        if (this.propiedadProductoRepository.findById(aLong).isEmpty())
            throw new ProductoException("No existe la propiedad producto con id: " + aLong);

        try {
            this.propiedadProductoRepository.deleteById(aLong);
        } catch (DataIntegrityViolationException e) {
            throw new ProductoException("No se pudo eliminar la propiedad con id ".concat(String.valueOf(aLong)) +
                    " ya que tiene referencias con otros objetos : " + e.getMessage());
        }
    }
}
