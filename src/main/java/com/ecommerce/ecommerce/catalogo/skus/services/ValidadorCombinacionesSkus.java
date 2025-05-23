package com.ecommerce.ecommerce.catalogo.skus.services;

import java.util.List;

import com.ecommerce.ecommerce.catalogo.productos.entities.ValorPropiedadProducto;

/**
 * Esta clase se encarga de validar si una combinacion de valores es igual a otra, para la generación
 * de Skus.
 */
public interface ValidadorCombinacionesSkus {

    /**
     * Valida entre dos listas de valores, que pertenecen a dos skus distintos, si ya existe la misma
     * combinacion de dichos valores.
     * @param comb1 {@link List<ValorPropiedadProducto>} combinacion 1.
     * @param comb2 {@link List<ValorPropiedadProducto>} combinacion 2.
     * @return true: es la misma combinación 1 y 2; false: no lo es.
     */
    boolean esMismaCombinacion(List<ValorPropiedadProducto> comb1, List<ValorPropiedadProducto> comb2);
}
