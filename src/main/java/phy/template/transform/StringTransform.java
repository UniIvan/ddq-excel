package phy.template.transform;

public class StringTransform implements AbstractTransform<String> {

    public static final String EMPTY_STR = "";

    @Override
    public String transform(String sourse) {
        if (null == sourse) {
            return EMPTY_STR;
        }
        return sourse;
    }
}
