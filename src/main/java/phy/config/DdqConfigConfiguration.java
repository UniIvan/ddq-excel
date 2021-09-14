package phy.config;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import phy.annotation.Router;
import phy.exception.ApiException;
import phy.template.view.DdqModel;
import phy.template.router.AbstractDdqTemplateRouter;
import phy.template.view.DdqTemplate;
import phy.util.JsonUtil;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class DdqConfigConfiguration {

    private List<DdqTemplate> ddqConfigs;
    private Map<String, AbstractDdqTemplateRouter> abstractDdqTemplateRouters;

    private DdqConfigConfiguration() {
        this.abstractDdqTemplateRouters = initRouter();
        this.ddqConfigs = initDdqConfig();
    }

    private static DdqConfigConfiguration instance = new DdqConfigConfiguration();

    public static DdqConfigConfiguration getInstance() {
        return instance;
    }

    private List<DdqTemplate> initDdqConfig() {
        URL url = DdqConfigConfiguration.class.getClassLoader().getResource("transform");
        File file = new File(url.getPath());
        File[] files = file.listFiles();
        if (null == files || files.length == 0) {
            throw new ApiException("transform文件目录读取失败");
        }
        List<DdqTemplate> configs = Lists.newArrayList();
        for (File current : files) {
            String jsonConfig = JsonUtil.fromJsonFile(current.getPath());
            try {
                DdqTemplate ddqConfig = resolve(jsonConfig);
                configs.add(ddqConfig);
            } catch (Exception e) {
                log.error("解析配置文件失败:%s", current.getPath());
                throw e;
            }
        }
        return configs;
    }

    private DdqTemplate resolve(String jsonConfig) {
        DdqTemplate config = new DdqTemplate();
        Map<String, Object> map = JsonUtil.toMap(jsonConfig);
        config.setName(map.get("template").toString());
        AbstractDdqTemplateRouter router = abstractDdqTemplateRouters.get(map.get("router"));
        config.setRouter(router);
        String modelsJson = JsonUtil.toJson(map.get("models"));
        List<DdqModel> ddqModels = JsonUtil.toList(modelsJson, DdqModel.class);
        config.setModels(ddqModels);
        return config;
    }

    private Map<String, AbstractDdqTemplateRouter> initRouter() {
        //入参 要扫描的包名
        Reflections f = new Reflections("phy.template.router");
        //入参 目标注解类
        Set<Class<? extends AbstractDdqTemplateRouter>> set = f.getSubTypesOf(AbstractDdqTemplateRouter.class);
        Map<String, AbstractDdqTemplateRouter> map = Maps.newHashMap();
        for (Class<? extends AbstractDdqTemplateRouter> clazz : set) {
            String name = clazz.getSimpleName().toLowerCase();
            Router router = clazz.getAnnotation(Router.class);
            if (null != router && StringUtils.isNotBlank(router.name())) {
                name = router.name();
            }
            AbstractDdqTemplateRouter abstractDdqTemplateRouter = null;
            try {
                abstractDdqTemplateRouter = clazz.getConstructor().newInstance();
            } catch (Exception e) {
                log.error("%s没有无参构造器", name);
                throw new ApiException(name + "没有无参构造器", e);
            }
            map.put(name, abstractDdqTemplateRouter);
        }
        return map;
    }

    public List<DdqTemplate> getDdqConfig() {
        return ddqConfigs;
    }

}
