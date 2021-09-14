package phy.template.router;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import phy.annotation.Router;
import phy.util.ExcelUtil;

@Router(name = "newDraftRouter")
public class NewDraftDDqTemplateRouter implements AbstractDdqTemplateRouter {
    //行
    private final static int ROW_INDEX = 5;
    //列
    private final static int CELL_INDEX = 1;
    //模板默认值
    private final static String CONTENT = "CONTACT DETAILS";

    @Override
    public boolean isTarget(XSSFSheet sheet) {
        String value = ExcelUtil.readDataByXSSFSheet(sheet, ROW_INDEX, CELL_INDEX);
        if (StringUtils.isNotBlank(value) && StringUtils.equals(value, CONTENT)) {
            return true;
        }
        return false;
    }
}
