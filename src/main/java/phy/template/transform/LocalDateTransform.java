package phy.template.transform;

import org.apache.commons.lang3.StringUtils;
import phy.util.DateTimeUtils;

import java.time.LocalDate;

public class LocalDateTransform implements AbstractTransform<LocalDate>{
    @Override
    public LocalDate transform(String sourse) {
        if(StringUtils.isBlank(sourse)){
            return null;
        }
        //todo 需要做适配 先暂时
        return DateTimeUtils.string2LocalDate(sourse);
    }
}
