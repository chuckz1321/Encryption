package com.zzuli.eassy;

import org.bouncycastle.crypto.digests.SHA1Digest;

public class SHAImplement {

    private byte[] fillByte(){
        return null;
    }
    public static void main(String[] args) {
        String str = "1";
        byte[] strByte = str.getBytes();
        String hex = ByteHexUtil.Byte2Hex(strByte);
        strByte = ByteHexUtil.Hex2Byte(hex);
    }
}
