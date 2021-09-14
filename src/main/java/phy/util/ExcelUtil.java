package phy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import phy.exception.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {

    private ExcelUtil(){

    }

    public static XSSFSheet readExcelToXSSFSheet(InputStream inputStream, String sheetName, String path) {
        //定义工作簿
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            log.error("Excel data file cannot be found! path:{}", path);
            throw new ApiException("Excel data file cannot be found!");
        }
        //定义工作表
        XSSFSheet xssfSheet;
        if (StringUtils.isBlank(sheetName)) {
            // 默认取第一个子表
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }
        return xssfSheet;
    }

    public static String readDataByXSSFSheet(XSSFSheet xssfSheet, int rowIndex, int cellIndex) {
        XSSFRow xssfRow = xssfSheet.getRow(rowIndex-1);
        XSSFCell xssfCell = xssfRow.getCell(cellIndex);
        return getString(xssfCell);
    }

    /**
     * 把单元格的内容转为字符串
     *
     * @param xssfCell 单元格
     * @return 字符串
     */
    public static String getString(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        if (xssfCell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else if (xssfCell.getCellTypeEnum() == CellType.BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else {
            return xssfCell.getStringCellValue();
        }
    }
}
