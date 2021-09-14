package phy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import phy.config.DdqConfigConfiguration;
import phy.exception.ApiException;
import phy.template.view.DdqModel;
import phy.template.view.DdqTemplate;
import phy.util.ExcelUtil;
import phy.util.JsonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TemplateService {

    private DdqConfigConfiguration ddqConfigConfiguration = DdqConfigConfiguration.getInstance();

    public Optional<DdqTemplate> getDdqTemplate(XSSFSheet sheet) {
        List<DdqTemplate> ddqConfig = ddqConfigConfiguration.getDdqConfig();
        for (DdqTemplate template : ddqConfig) {
            boolean result = template.getRouter().isTarget(sheet);
            if (result) {
                return Optional.of(template);
            }
        }
        return Optional.empty();
    }

    public String getDdqJson(File file) {
        //我们直接将打开的流对象放到try的圆括号中，这样当流使用完毕时，会自动将流进行关闭
        try {
            FileInputStream inputStream = new FileInputStream(file);
            XSSFSheet xssfSheet = ExcelUtil.readExcelToXSSFSheet(inputStream, null, file.getPath());

            Optional<DdqTemplate> ddqTemplateOptional = getDdqTemplate(xssfSheet);
            if (!ddqTemplateOptional.isPresent()) {
                throw new ApiException(String.format("文件:%s,未匹配到模板", file.getPath()));
            }
            DdqTemplate ddqTemplate = ddqTemplateOptional.get();
            List<DdqModel> ddqModels = ddqTemplate.getModels();
            fillDdqModels(xssfSheet, ddqModels, file);
            return JsonUtil.toJson(ddqModels);
        } catch (FileNotFoundException e) {
            throw new ApiException(String.format("file cannot be found! path: %s", file.getPath()));
        }
    }

    private void fillDdqModels(XSSFSheet xssfSheet, List<DdqModel> ddqModels, File file) {
        for (DdqModel model : ddqModels) {
            try {
                String value = ExcelUtil.readDataByXSSFSheet(xssfSheet, model.getRowIndex(), model.getCellIndex());
                model.setValue(value);
            } catch (Exception e) {
                log.error(String.format("%s : %s 数据解析错误，文件path: %s ,错误原因:%s",
                        model.getRowIndex(), model.getCellIndex(), file.getPath(), e.getMessage()));
            }
        }
    }

}
