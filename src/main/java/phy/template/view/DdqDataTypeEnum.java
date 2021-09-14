package phy.template.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import phy.template.transform.AbstractTransform;
import phy.template.transform.LocalDateTransform;
import phy.template.transform.StringTransform;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public enum DdqDataTypeEnum {
    STRING("string", StringTransform.class),
    DATE("localDate", LocalDateTransform.class);

    private final String type;
    private final Class<? extends AbstractTransform> clazz;
}
