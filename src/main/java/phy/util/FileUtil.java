package phy.util;

import org.apache.commons.compress.utils.Lists;

import java.io.File;
import java.util.List;

public class FileUtil {

    private FileUtil(){

    }

    public static List<File> findFilePathsBySuffix(File file, String suffix) {
        List<File> files = Lists.newArrayList();
        File[] listFiles = file.listFiles();
        for (File lf : listFiles) {
            if (lf.isFile()) {
                if (lf.getName().endsWith(suffix)) {
                    files.add(lf);
                }
            } else {
                if (lf.isDirectory()) {
                    findFilePathsBySuffix(lf, suffix, files);
                }
            }
        }
        return files;
    }

    private static void findFilePathsBySuffix(File file, String suffix, List<File> files) {
        File[] listFiles = file.listFiles();
        for (File lf : listFiles) {
            if (lf.isFile()) {
                if (lf.getName().endsWith(suffix)) {
                    files.add(lf);
                }
            } else {
                if (lf.isDirectory()) {
                    findFilePathsBySuffix(lf, suffix, files);
                }
            }
        }
    }
}
