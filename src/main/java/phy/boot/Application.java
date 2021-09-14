package phy.boot;

import phy.service.TemplateService;
import phy.util.FileUtil;

import java.io.File;
import java.util.List;


public class Application {
    public static void main(String[] args) {
        //todo 测试
        String path ="H:\\工作";
        File file = new File(path);
        List<File> files=FileUtil.findFilePathsBySuffix(file,".xlsx");
        TemplateService templateService =new TemplateService();
        for(File f:files){
            try{
                String json=templateService.getDdqJson(f);
                System.out.println(json);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
