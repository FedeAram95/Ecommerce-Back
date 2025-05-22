package com.ecommerce.ecommerce.reportes.services;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.jasperreports.engine.JRException;

@Deprecated
public interface ExportOperacionReporteService {

    byte[] generarReportPDF() throws FileNotFoundException, JRException;

    ByteArrayInputStream generarReportEXCEL() throws IOException, JRException;
}
