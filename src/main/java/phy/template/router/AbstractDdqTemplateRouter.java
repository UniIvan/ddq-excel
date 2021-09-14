package phy.template.router;


import org.apache.poi.xssf.usermodel.XSSFSheet;

public  interface AbstractDdqTemplateRouter {
     boolean isTarget(XSSFSheet sheet);
}
