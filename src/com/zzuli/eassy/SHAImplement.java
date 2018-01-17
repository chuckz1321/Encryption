package com.zzuli.eassy;

import java.math.BigInteger;

public class SHAImplement {

    private static Integer originalLength;

    private final static int mLength = 128;

    private final static int K1 = 0x5A827999;// (0 <= t <= 19)
    private final static int K2 = 0x6ED9EBA1;// (20 <= t <= 39)
    private final static int K3 = 0x8F1BBCDC;// (40 <= t <= 59)
    private final static int K4 = 0xCA62C1D6;// (60 <= t <= 79)

    private int H0,H1,H2,H3,H4;

    private int[] W = new int[80];

    private int f(int s1, int s2, int s3){
        return s1 & s2 | ~s1 & s3;
    }

    private int h(int s1, int s2, int s3){
        return s1 ^ s2 ^ s3;
    }

    private int g(int s1, int s2, int s3){
        return s1 & s2 | s1 & s3 | s2 & s3;
    }


    private String fillByte(byte[] data){
        String hexData = ByteHexUtil.Byte2Hex(data);
        originalLength = hexData.length() * 4;
        //补足数据位
        if(hexData.length() * 4 % 512 != 448) hexData += '8';
        while(hexData.length() * 4 % 512 != 448){
            hexData += '0';
        }
        String oLHex = Integer.toHexString(originalLength);
        //补足长度位
        while(oLHex.length() * 4 != 64){
            oLHex = '0' + oLHex;
        }
        return hexData + oLHex;

    }

    private String processBlock(String data){
        if (data == null) return null;
        this.H0 = 1732584193;
        this.H1 = -271733879;
        this.H2 = -1732584194;
        this.H3 = 271733878;
        this.H4 = -1009589776;
        while(data.length() != 0){
            //获取前512位
            String block = data.substring(0,mLength);
            data = data.substring(mLength);
            this.processSubBlock(block);
        }
        return Integer.toHexString(this.H0) + Integer.toHexString(this.H1) +Integer.toHexString(this.H2) +
                Integer.toHexString(this.H3) +Integer.toHexString(this.H4);
    }

    private void processSubBlock(String block){
        int index = 0,tempI;
        //处理W的前16个
        while(block.length() != 0){
            String tempS = block.substring(0,8);
            block = block.substring(8);
            Long tempL = Long.parseLong(tempS,16);
            W[index++] = tempL.intValue();
        }
        for(int i = index; i < 80; i++){
            tempI = this.W[i - 3] ^ this.W[i - 8] ^ this.W[i - 14] ^ this.W[i - 16];
            this.W[i] = tempI << 1 | tempI >>> 31;
        }

        int var1 = this.H0, var2=this.H1, var3=this.H2, var4 = this.H3, var5 = this.H4, var6 = 0, var7;

        for(var7 = 0; var7 < 4; ++var7) {
            var5 += (var1 << 5 | var1 >>> 27) + this.f(var2, var3, var4) + this.W[var6++] + 1518500249;
            var2 = var2 << 30 | var2 >>> 2;
            var4 += (var5 << 5 | var5 >>> 27) + this.f(var1, var2, var3) + this.W[var6++] + 1518500249;
            var1 = var1 << 30 | var1 >>> 2;
            var3 += (var4 << 5 | var4 >>> 27) + this.f(var5, var1, var2) + this.W[var6++] + 1518500249;
            var5 = var5 << 30 | var5 >>> 2;
            var2 += (var3 << 5 | var3 >>> 27) + this.f(var4, var5, var1) + this.W[var6++] + 1518500249;
            var4 = var4 << 30 | var4 >>> 2;
            var1 += (var2 << 5 | var2 >>> 27) + this.f(var3, var4, var5) + this.W[var6++] + 1518500249;
            var3 = var3 << 30 | var3 >>> 2;
        }

        for(var7 = 0; var7 < 4; ++var7) {
            var5 += (var1 << 5 | var1 >>> 27) + this.h(var2, var3, var4) + this.W[var6++] + 1859775393;
            var2 = var2 << 30 | var2 >>> 2;
            var4 += (var5 << 5 | var5 >>> 27) + this.h(var1, var2, var3) + this.W[var6++] + 1859775393;
            var1 = var1 << 30 | var1 >>> 2;
            var3 += (var4 << 5 | var4 >>> 27) + this.h(var5, var1, var2) + this.W[var6++] + 1859775393;
            var5 = var5 << 30 | var5 >>> 2;
            var2 += (var3 << 5 | var3 >>> 27) + this.h(var4, var5, var1) + this.W[var6++] + 1859775393;
            var4 = var4 << 30 | var4 >>> 2;
            var1 += (var2 << 5 | var2 >>> 27) + this.h(var3, var4, var5) + this.W[var6++] + 1859775393;
            var3 = var3 << 30 | var3 >>> 2;
        }

        for(var7 = 0; var7 < 4; ++var7) {
            var5 += (var1 << 5 | var1 >>> 27) + this.g(var2, var3, var4) + this.W[var6++] + -1894007588;
            var2 = var2 << 30 | var2 >>> 2;
            var4 += (var5 << 5 | var5 >>> 27) + this.g(var1, var2, var3) + this.W[var6++] + -1894007588;
            var1 = var1 << 30 | var1 >>> 2;
            var3 += (var4 << 5 | var4 >>> 27) + this.g(var5, var1, var2) + this.W[var6++] + -1894007588;
            var5 = var5 << 30 | var5 >>> 2;
            var2 += (var3 << 5 | var3 >>> 27) + this.g(var4, var5, var1) + this.W[var6++] + -1894007588;
            var4 = var4 << 30 | var4 >>> 2;
            var1 += (var2 << 5 | var2 >>> 27) + this.g(var3, var4, var5) + this.W[var6++] + -1894007588;
            var3 = var3 << 30 | var3 >>> 2;
        }

        for(var7 = 0; var7 <= 3; ++var7) {
            var5 += (var1 << 5 | var1 >>> 27) + this.h(var2, var3, var4) + this.W[var6++] + -899497514;
            var2 = var2 << 30 | var2 >>> 2;
            var4 += (var5 << 5 | var5 >>> 27) + this.h(var1, var2, var3) + this.W[var6++] + -899497514;
            var1 = var1 << 30 | var1 >>> 2;
            var3 += (var4 << 5 | var4 >>> 27) + this.h(var5, var1, var2) + this.W[var6++] + -899497514;
            var5 = var5 << 30 | var5 >>> 2;
            var2 += (var3 << 5 | var3 >>> 27) + this.h(var4, var5, var1) + this.W[var6++] + -899497514;
            var4 = var4 << 30 | var4 >>> 2;
            var1 += (var2 << 5 | var2 >>> 27) + this.h(var3, var4, var5) + this.W[var6++] + -899497514;
            var3 = var3 << 30 | var3 >>> 2;
        }

        this.H0 += var1;
        this.H1 += var2;
        this.H2 += var3;
        this.H3 += var4;
        this.H4 += var5;
        for(var7 = 0; var7 < W.length; ++var7) {
            this.W[var7] = 0;
        }
    }
    public String process(String data){
        byte[] dataByte = data.getBytes();
        return this.processBlock(this.fillByte(dataByte));
    }

}
