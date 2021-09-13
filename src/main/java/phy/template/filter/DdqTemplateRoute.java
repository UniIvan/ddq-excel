package phy.template.filter;

import org.apache.poi.sl.usermodel.Sheet;

public interface DdqTemplateRoute {
    boolean isTarget(Sheet sheet);
}
