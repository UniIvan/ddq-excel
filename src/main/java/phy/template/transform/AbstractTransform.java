package phy.template.transform;

public interface AbstractTransform<V> {
    //转换为实体类
    V transform(String sourse);
}
