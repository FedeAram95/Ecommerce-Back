package com.ecommerce.ecommerce.pagos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.pagos.entities.MedioPago;
import com.ecommerce.ecommerce.pagos.exceptions.MedioPagoException;
import com.ecommerce.ecommerce.pagos.repositories.MedioPagoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedioPagoServiceImpl implements MedioPagoService {

    private final MedioPagoRepository medioPagoRepository;


    @Override
    public List<MedioPago> findAll() {
        return this.medioPagoRepository.findAll();
    }


    @Override
    public MedioPago findById(Long aLong) {
        return this.medioPagoRepository.findById(aLong)
                .orElseThrow(() -> new MedioPagoException("No existe medio de pago con id: " + aLong));
    }

    @Override
    public MedioPago save(MedioPago object) {
        return null;
    }

    @Override
    public void delete(MedioPago object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}
