package phy.template;

import lombok.Data;
import phy.template.filter.DdqTemplateRoute;

import java.util.List;

@Data
public class DdqConfig {
    //模板名称
    private String name;
    //判断上传文件是否是此模板
    private DdqTemplateRoute route;
    //模板元素
    private List<DdqModel> models;
}
