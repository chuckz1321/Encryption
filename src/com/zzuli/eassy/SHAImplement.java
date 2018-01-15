package com.zzuli.eassy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SHAImplement {
    public static void main(String[] args) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        try {
            // 被加密的字节数组
            byte[] data = "hgfhfgh".getBytes();
            // 获取Bouncy Castle提供者
//          Provider provider = Security.getProvider("BC");
            // 获取MD4算法的消息摘要对象
//          MessageDigest md = MessageDigest.getInstance("MD4", provider);
            MessageDigest md = MessageDigest.getInstance("SHA1");
            // 使用指定的数据更新消息摘要数据
            md.update(data);
            // 获取消息摘要数据
            byte[] result = md.digest();
            // 使用十六进制输出加密结果
            StringBuffer buffer = new StringBuffer();
            for (int i = 0, len = result.length; i < len; i++ ) {
                byte b = result[i];
                buffer.append( chars[b >>> 4 & 0xF] );
                buffer.append( chars[b & 0xF] );
            }
            System.out.println( buffer.toString() );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
