package com.ecommerce.ecommerce.reportes.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.sf.jasperreports.engine.JRException;

public interface ExportProductoReporteService {

    ByteArrayInputStream generarReporteExcel() throws IOException, JRException;
}
