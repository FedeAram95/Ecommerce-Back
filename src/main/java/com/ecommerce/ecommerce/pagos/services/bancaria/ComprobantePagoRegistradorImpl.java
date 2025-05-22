package com.ecommerce.ecommerce.pagos.services.bancaria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommerce.imagenes.entities.Imagen;
import com.ecommerce.ecommerce.imagenes.services.ImageService;
import com.ecommerce.ecommerce.pagos.entities.OperacionPago;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComprobantePagoRegistradorImpl implements ComprobantePagoRegistrador {

    private final ImageService imageService;

    @Autowired
    public ComprobantePagoRegistradorImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public OperacionPago registrarComprobantePago(MultipartFile comprobante, OperacionPago pago) {
        // Si el pago ya tiene comprobante, eliminamos el que ya existía.
        if (pago.getComprobante() != null) {
            Imagen comprobanteEliminar = pago.getComprobante();
            pago.setComprobante(null);
            this.imageService.eliminarImagen(comprobanteEliminar);
        }
        // Subimos la img a asociar al pago de la compra
        Imagen imagenComprobante = this.imageService.subirImagen(comprobante);
        log.info("Comprobante: " + imagenComprobante);

        pago.setComprobante(imagenComprobante);

        return pago;
    }
}
