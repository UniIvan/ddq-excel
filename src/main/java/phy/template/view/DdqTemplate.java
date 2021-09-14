package phy.template.view;

import lombok.Data;
import phy.template.router.AbstractDdqTemplateRouter;
import java.util.List;

@Data
public class DdqTemplate {
    //模板名称
    private String name;
    //判断上传文件是否是此模板
    private AbstractDdqTemplateRouter router;
    //模板元素
    private List<DdqModel> models;

}
