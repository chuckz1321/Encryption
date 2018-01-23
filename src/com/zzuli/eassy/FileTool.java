package com.zzuli.eassy;

import com.google.common.primitives.Bytes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Author: Administrator
 * Date: 2018-1-17 19:04
 * Created with IDEA
 */
public class FileTool {
    public static byte[] readImg(String fileName){
        try{
            File file = new File(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage img = ImageIO.read(file);
            ImageIO.write(img,"jpg",baos);
            return baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void generateImg(byte[] bytes){
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bi1 = ImageIO.read(bais);
            File out = new File("C:\\Users\\Administrator\\Desktop\\test.jpg");
            ImageIO.write(bi1,"jpg",out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SHAImplement sha = new SHAImplement();
        RSAImplement rsa = new RSAImplement();
        byte[] bs = FileTool.readImg("C:\\Users\\Administrator\\Desktop\\me.jpg");
        System.out.println(bs.length);
        List<Byte> list = new ArrayList<Byte>();
        for(int i = 0; i < bs.length; i++){
            list.add(bs[i]);
        }
        String digest = sha.process(bs);
        byte[] bDigest = ByteHexUtil.Hex2Byte(digest);
        for(int i = 0; i < bDigest.length; i++){
            list.add(bDigest[i]);
        }
        System.out.println(list.size());
        bs = Bytes.toArray(list);
        FileTool.generateImg(bs);
        bs = FileTool.readImg("C:\\Users\\Administrator\\Desktop\\test.jpg");
        System.out.println(bs.length);
    }
}
