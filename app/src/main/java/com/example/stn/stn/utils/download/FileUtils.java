package com.example.stn.stn.utils.download;

import java.io.File;
import java.io.IOException;

/**
 * 文件管理类
 * Created by xulong
 */
public class FileUtils {

    public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message =
                        "File "
                                + directory
                                + " exists and is "
                                + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {

                if (!directory.isDirectory()) {
                    String message =
                            "Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }

    public static boolean mkdirs(File directory) {
        try {
            forceMkdir(directory);
            return true;
        } catch (IOException e){
        }
        return false;
    }

}
