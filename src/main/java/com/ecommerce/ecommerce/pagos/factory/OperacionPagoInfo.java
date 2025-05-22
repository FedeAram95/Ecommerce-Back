package com.ecommerce.ecommerce.pagos.factory;

import java.util.Date;
import java.util.Map;

import com.ecommerce.ecommerce.pagos.dto.AmountPayload;
import com.ecommerce.ecommerce.pagos.dto.PayerPayload;

public abstract class OperacionPagoInfo {

    protected Map<String, Object> atributos;

    public OperacionPagoInfo(Map<String, Object> atributos) {
        this.atributos = atributos;
    }

    public Map<String, Object> getAtributos() {
        return this.atributos;
    }

    public abstract String getId();

    public Map<String, Object> getInfo() {
        return null;
    }

    public abstract Long getNroOperacion();

    public abstract String getStatus();

    public abstract Date getExpiraEn();

    public abstract String getApproveUrl();

    public abstract AmountPayload getAmount();

    public abstract PayerPayload getPayer();
}
