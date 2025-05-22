package com.ecommerce.ecommerce.catalogo.skus.services.archivoshandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.catalogo.skus.entities.Sku;

@Service
public class SkuExcelGenerador implements SkuArchivosGenerador {

    @Override
    public ByteArrayInputStream generarArchivo(List<Sku> skus) throws IOException {
        String[] columnas = {"ID", "Nombre", "Valores", "Precio", "Disponibilidad"};

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Actualización-Skus");
        Row row = sheet.createRow(0);

        for (int i=0; i < columnas.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        int initRow = 1;

        for (Sku sku: skus) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(sku.getId());
            row.createCell(1).setCellValue(sku.getNombre());
            row.createCell(2).setCellValue(""+sku.getValoresData());
            row.createCell(3).setCellValue(sku.getPrecio());
            row.createCell(4).setCellValue(sku.getDisponibilidad());

            initRow++;
        }

        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
