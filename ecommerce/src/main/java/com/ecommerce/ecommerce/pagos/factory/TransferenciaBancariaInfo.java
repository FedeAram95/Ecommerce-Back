package com.ecommerce.ecommerce.pagos.factory;

import java.util.Date;
import java.util.Map;

import com.ecommerce.ecommerce.pagos.dto.AmountPayload;
import com.ecommerce.ecommerce.pagos.dto.PayerPayload;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"atributos"})
public class TransferenciaBancariaInfo extends OperacionPagoInfo {

    public TransferenciaBancariaInfo(Map<String, Object> atributos) {
        super(atributos);
    }

    @Override
    public String getId() {
        return (String) atributos.get("pagoId");
    }

    @Override
    public Map<String, Object> getInfo() {
        return (Map<String, Object>) atributos.get("info");
    }

    @Override
    public Long getNroOperacion() {
        return (Long) atributos.get("nroOperacion");
    }

    @Override
    public String getStatus() {
        return (String) atributos.get("estado");
    }

    @Override
    public Date getExpiraEn() {
        return (Date) atributos.get("expiraEn");
    }

    @Override
    public String getApproveUrl() {
        return null;
    }

    @Override
    public AmountPayload getAmount() {
        return (AmountPayload) atributos.get("monto");
    }

    @Override
    public PayerPayload getPayer() {
        return (PayerPayload) atributos.get("pagador");
    }
}
