package phy.boot;

import phy.util.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

//        File file = new File("H:\\工作\\Data Provider DDQ - NEW DRAFT.xlsx");
        File file = new File("H:\\工作\\test.xlsx");

        List<Map<String, String>> mapList = null;
        try {
            mapList = ExcelUtil.readExcel(new FileInputStream(file), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(mapList);

    }
}
