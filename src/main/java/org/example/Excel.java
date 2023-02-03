package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Excel {
    private final CellStyle cellStyle1;
    private final CellStyle cellStyle2;
    private final CellStyle cellStyle3;
    private final CellStyle cellStyle4;
    private DataFormat format;
    private CellStyle dateStyle;
    private Workbook book= new HSSFWorkbook();
    private Sheet sheet;

    private List<String> saveNames = new ArrayList<>();

    public Excel()  {


        sheet = book.createSheet("ListPriceChange");
        dateStyle = book.createCellStyle();
        format = book.createDataFormat();

        cellStyle1 = sheet.getWorkbook().createCellStyle();
        cellStyle1.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("0.00"));
        cellStyle1.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle2 = sheet.getWorkbook().createCellStyle();
        cellStyle2.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("0.00"));
        cellStyle2.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle3 = sheet.getWorkbook().createCellStyle();
        cellStyle3.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("0.00"));
        cellStyle3.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle4 = sheet.getWorkbook().createCellStyle();
        cellStyle4.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("0.00"));
        cellStyle4.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        cellStyle4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    }

    public void readFile(String file) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        book = (Workbook) wb;
        sheet = wb.getSheetAt(0);
        save_cell0();
    }

    public void save_cell0(){
        saveNames = new ArrayList<>();
        for (int k =0; k<2000; k++){
            Row row_w_r = sheet.getRow(k);
            if (row_w_r==null) row_w_r = sheet.createRow(k);
            Cell cell_r = row_w_r.getCell(0);
            if (cell_r==null) cell_r = row_w_r.createCell(0);
            try {
                //System.out.println(cell_r.getStringCellValue() + "            " + name);
                saveNames.add(cell_r.getStringCellValue()+"");
            }catch (Exception e){e.printStackTrace();}
        }
    }
    public void setColorCell(int row_s, int cell_s, int index_s){
        Row row_w = sheet.getRow(row_s-1);
        if (row_w==null) row_w = sheet.createRow(row_s-1);
        Cell cell = row_w.getCell(cell_s-1);
        if (cell==null) cell = row_w.createCell(cell_s-1);

        switch (index_s){
            case 1:
                cell.setCellStyle(cellStyle1);
                break;
            case 2:
                cell.setCellStyle(cellStyle2);
                break;
            case 3:
                cell.setCellStyle(cellStyle3);
                break;
            case 4:
                cell.setCellStyle(cellStyle4);
                break;
        }

        sheet.autoSizeColumn(cell_s-1);

    }

    public boolean checkName(String name){
       // int first = sheet.getFirstRowNum();
       // int last = sheet.getLastRowNum();
        //System.out.println(first+":"+last);
        boolean bool_r = false;

        bool_r = saveNames.contains(name);
        //if (bool_r==false) System.out.println(name);

       /* for (int k =0; k<2000; k++){

            Row row_w_r = sheet.getRow(k);
            if (row_w_r==null) row_w_r = sheet.createRow(k);
            Cell cell_r = row_w_r.getCell(0);
            if (cell_r==null) cell_r = row_w_r.createCell(0);
            try {
                //System.out.println(cell_r.getStringCellValue() + "            " + name);
                if (cell_r.getStringCellValue().equals(name)) bool_r = true;
            }catch (Exception e){e.printStackTrace();}
        }*/
        return bool_r;
    }
    public void writeRowCell(int row, int cell, String text, ExcelFormat excelFormat, String format_r){
        Row row_w = sheet.getRow(row-1);
        if (row_w==null) row_w = sheet.createRow(row-1);
        Cell name = row_w.getCell(cell-1);
        if (name==null) name = row_w.createCell(cell-1);

        switch (excelFormat){
            case STRING:
                name.setCellValue(text);
                break;
            case DOUBLE:
                Double text_d = Double.parseDouble(text);
                name.setCellValue(text_d);
                if (format_r!=null) {
                    dateStyle.setDataFormat(format.getFormat(format_r));
                    name.setCellStyle(dateStyle);
                }
                break;
            case INT:
                Integer text_i = Integer.parseInt(text);
                name.setCellValue(text_i);
                if (format_r!=null) {
                    dateStyle.setDataFormat(format.getFormat(format_r));
                    name.setCellStyle(dateStyle);
                }
                break;
        }





        sheet.autoSizeColumn(cell);
    }


    public void writeIntoExcel(String file) throws FileNotFoundException, IOException {



        // Записываем всё в файл
        book.write(new FileOutputStream(file));

    }

    public void closeStreem() throws IOException {
        book.close();
    }

}


