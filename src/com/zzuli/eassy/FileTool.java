package com.zzuli.eassy;

import com.google.common.primitives.Bytes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Author: Administrator
 * Date: 2018-1-17 19:04
 * Created with IDEA
 */
public class FileTool {

    private static UserDefinedFileAttributeView userDefinedFileAttributeView;

    private static final String key = "verify";
    public static byte[] readImg(String fileName){
        try{
            File file = new File(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage img = ImageIO.read(file);
            ImageIO.write(img,"png",baos);
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
            File out = new File("C:\\Users\\Administrator\\Desktop\\test.png");
            ImageIO.write(bi1,"png",out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //verify:""
    public boolean setEncryptionAttr(String code, String filename){
        try {
            Path path = Paths.get(filename);
            userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
            userDefinedFileAttributeView.write(key, Charset.defaultCharset().encode(code));
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public String getEncryptionAttr(String filename){
        try {
            Path path = Paths.get(filename);
            userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
            if(!userDefinedFileAttributeView.list().contains(key)) return null;
            ByteBuffer bb = ByteBuffer.allocate(userDefinedFileAttributeView.size(key)); // 准备一块儿内存块读取
            userDefinedFileAttributeView.read(key, bb);
            bb.flip();
            String value = Charset.defaultCharset().decode(bb).toString();
            return value;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        FileTool ft = new FileTool();
        ft.setEncryptionAttr("shdjkahdjashdjkashdjhaskdhasdkjasdhk","C:\\Users\\Administrator\\Desktop\\1.png");
        System.out.println(ft.getEncryptionAttr("C:\\Users\\Administrator\\Desktop\\1.png"));



//        SHAImplement sha = new SHAImplement();
        RSAImplement rsa = new RSAImplement();
        byte[] bs = FileTool.readImg("C:\\Users\\Administrator\\Desktop\\1.png");
        byte[] s = FileTool.readImg("C:\\Users\\Administrator\\Desktop\\2.png");
        System.out.println(bs.length +  "              "+ s.length);
//        System.out.println(bs.length);
//        List<Byte> list = new ArrayList<Byte>();
//        for(int i = 0; i < bs.length; i++){
//            list.add(bs[i]);
//        }
//        String digest = sha.process(bs);
//        byte[] bDigest = ByteHexUtil.Hex2Byte(digest);
//        for(int i = 0; i < bDigest.length; i++){
//            list.add(bDigest[i]);
//        }
//        System.out.println(list.size());
//        bs = Bytes.toArray(list);
//        FileTool.generateImg(bs);
//        bs = FileTool.readImg("C:\\Users\\Administrator\\Desktop\\test.png");
//        System.out.println(bs.length);
    }
}
