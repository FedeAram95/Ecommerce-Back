package com.ecommerce.ecommerce.operaciones.services.anulador;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.ecommerce.operaciones.entities.EstadoOperacion;
import com.ecommerce.ecommerce.operaciones.entities.Operacion;
import com.ecommerce.ecommerce.operaciones.repositories.OperacionRepository;
import com.ecommerce.ecommerce.operaciones.services.OperacionService;
import com.ecommerce.ecommerce.pagos.entities.OperacionPago;
import com.ecommerce.ecommerce.utils.scheduled.Programable;

/**
 * Esta implementación ejecuta el proceso de validación de operaciones en estado EN_ENTREGA.
 * <br>
 * Implementa {@link Programable}, utilizando una función
 * cron para establecer, diariamente, la validación de expiración del pago en efectivo de operaciones.
 * Se ejecuta por defecto: todos los dias a las 05:00 AM hora del servidor.
 */
@Service
public class OperacionAnuladorAutomaticoImpl implements OperacionAnuladorAutomatico {

    private final Logger log = LoggerFactory.getLogger(OperacionAnuladorAutomaticoImpl.class);

    private final OperacionRepository operacionRepository;
    private final OperacionService operacionService;

    @Autowired
    public OperacionAnuladorAutomaticoImpl(OperacionRepository operacionRepository,
                                           OperacionService operacionService) {
        this.operacionRepository = operacionRepository;
        this.operacionService = operacionService;
    }

    @Override
    public boolean expired(Operacion operacion) {
        OperacionPago pago = operacion.getPago();
        Date actualDate = new Date();
        return actualDate.after(pago.getExpiraEn());
    }

    @Override
    @Transactional
    public void anularOperacion(Long nroOperacion) {
        log.info("Anulando operación: " + nroOperacion);
        this.operacionService.anular(nroOperacion);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 5 1/1 * ?")
    public void execute() {
        List<Operacion> enReserva = this.operacionRepository.findAllByEstado(EstadoOperacion.EN_RESERVA);

        int totalAnulados = 0;
        for (Operacion operacion: enReserva) {
            if (expired(operacion)) {
                totalAnulados++;
                anularOperacion(operacion.getNroOperacion());
            }
        }

        log.info("Total anulados: " + totalAnulados);
    }
}
