package com.ecommerce.ecommerce.pagos.mapper;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.pagos.entities.OperacionPago;
import com.ecommerce.ecommerce.pagos.factory.OperacionPagoInfo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperacionPagoMapperImpl implements OperacionPagoMapper {

    @Override
    public OperacionPago mapToOperacionPago(OperacionPagoInfo pagoInfo) {
        String totalBruto = "";
        String totalNeto = "";
        String fee = "";
        String payerId = "";
        String payerEmail = "";
        String payerFullName = "";

        if (pagoInfo.getAmount() != null) {
            totalBruto = pagoInfo.getAmount().getTotalBruto();
            totalNeto = pagoInfo.getAmount().getTotalNeto();
            fee = pagoInfo.getAmount().getFee();
        }

        if (pagoInfo.getPayer() != null) {
            payerId = pagoInfo.getPayer().getPayerId();
            payerEmail = pagoInfo.getPayer().getPayerEmail();
            payerFullName = pagoInfo.getPayer().getPayerFullName();
        }

        return OperacionPago.builder()
                .id(pagoInfo.getId())
                .nroOperacion(pagoInfo.getNroOperacion())
                .status(pagoInfo.getStatus())
                .expiraEn(pagoInfo.getExpiraEn())
                .approveUrl(pagoInfo.getApproveUrl())
                .totalBruto(totalBruto)
                .totalNeto(totalNeto)
                .fee(fee)
                .payerId(payerId)
                .payerEmail(payerEmail)
                .payerFullName(payerFullName).build();
    }
}
