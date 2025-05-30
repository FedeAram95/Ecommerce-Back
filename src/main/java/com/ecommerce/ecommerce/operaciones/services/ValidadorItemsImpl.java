package com.ecommerce.ecommerce.operaciones.services;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;

@Service
public class ValidadorItemsImpl implements ValidadorItems {

    @Override
    public boolean esItemNoVendible(Sku sku) {
        // Regla de negocio: el producto posee skus adicionales ? el sku ES un default sku (default prod != null
        // ) ? --> ITEM NO VENDIBLE.

        // sku tiene default producto? (es default sku)
        if (sku.getDefaultProducto() != null) {
            // sku.defaultProducto NO tiene skus adicionales (es vendible sin propiedades) ? true : false
            return !sku.getDefaultProducto().isVendibleSinPropiedades();
        }

        return false;
    }
}
