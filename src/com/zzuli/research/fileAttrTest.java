package com.zzuli.research;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Author: Administrator
 * Date: 2018-1-24 10:53
 * Created with IDEA
 */
public class fileAttrTest {
    //http://blog.csdn.net/lirx_tech/article/details/51428238
    public static void main(String[] args) {
        try {
            Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\无标题.png");
            UserDefinedFileAttributeView udfav = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
            udfav.write("pass", Charset.defaultCharset().encode("杨姐upup"));
            List<String> list = new ArrayList<>();
            list = udfav.list();
            for (String name: list) {
                ByteBuffer bb = ByteBuffer.allocate(udfav.size(name)); // 准备一块儿内存块读取
                udfav.read(name, bb);
                bb.flip();
                String value = Charset.defaultCharset().decode(bb).toString();
                System.out.println(name + " : " + value);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
