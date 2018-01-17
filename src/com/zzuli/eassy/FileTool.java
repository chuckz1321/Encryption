package com.zzuli.eassy;

import java.io.*;

/**
 * <p>
 * Author: Administrator
 * Date: 2018-1-17 19:04
 * Created with IDEA
 */
public class FileTool {
    public static String readToString(String fileName) {
        String encoding = "GB2312";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
