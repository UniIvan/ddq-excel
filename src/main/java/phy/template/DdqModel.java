package phy.template;

import lombok.Data;

@Data
public class DdqModel {
    //名称
    private String name;
    //行
    private Integer rowIndex;
    //列
    private Integer cellIndex;
    //数据类型
    private DdqDataTypeEnum dataType;
}
