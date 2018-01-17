package com.zzuli.eassy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * Author: Administrator
 * Date: 2018-1-10 10:25
 * Created with IDEA
 */
public class RSAImplement {
    //coming from Euler Theorem
    //static variable
    private final static BigInteger p = new BigInteger("10866289542619597012329317938911255252787972295435799906578527505833930501906010171265281409293819089999220398009901661332021540312243871136136371637956901");

    private final static BigInteger q = new BigInteger("8741509263779571070796761735336456686611686514894798380826660057179943659283242561858002920946511703382883975213490200252468931321778695056512619262951737");

    private final static BigInteger e = new BigInteger("65537");

    private static BigInteger d;

    private static BigInteger n;

    //essential variable (e,d,n)
    //public (n,e)
    //private (n,d)
    public void generateVariable(){
        n = p.multiply(q);
        BigInteger rulerVar = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        //e*x + rulerVar*y = 1
        d = ex_gcd(e,rulerVar).get(1);
//        while (d.compareTo(BigInteger.ZERO) <= 0){
//            d = d.add(rulerVar);
//        }
    }
    private List<BigInteger> ex_gcd(BigInteger a, BigInteger b){
        List<BigInteger> backList = new ArrayList<>();
        if(b.intValue() == 0 ){
            backList.add(a);
            backList.add(new BigInteger("1"));
            backList.add(new BigInteger("0"));
            return backList;
        }
        List<BigInteger> tempList = ex_gcd(b,a.mod(b));
        backList.add(tempList.get(0));
        backList.add(tempList.get(2));
        backList.add(tempList.get(1).subtract(a.divide(b).multiply(tempList.get(2))));
        return backList;
    }

    private boolean judgeCoprime(int a, int b){
        if(a < 0 || b < 0) return false;
        if(a < b){
            int temp = a;
            a = b;
            b = temp;
        }
        int temp = 0;
        while(true){
            temp = a % b;
            if(temp == 0){
                break;
            }else{
                a = b;
                b = temp;
            }
        }
        if(b == 1) return true;
        return false;
    }


    public BigInteger encrypt(BigInteger info){
        return info.modPow(e,n);
    }

    public BigInteger encode(BigInteger code){
        return code.modPow(d,n);
    }

    public static void main(String[] args) {
        try{
            SHAImplement sha = new SHAImplement();
            RSAImplement rsaImplement  = new RSAImplement();
            String data = "透你个吗的";
            System.out.println("原始数据:"+ data);
            String digest =sha.process(data);
            System.out.println("摘要："+ digest);
            //生成私钥密钥
            rsaImplement.generateVariable();
            byte[] infoBytes = digest.getBytes("UTF-8");
            BigInteger infoBigInteger = new BigInteger(infoBytes);
            BigInteger code = rsaImplement.encrypt(infoBigInteger);
            System.out.println("密文："+code);
            BigInteger decodeBigInteger = rsaImplement.encode(code);
            byte[] encodeArray = decodeBigInteger.toByteArray();
            System.out.println("解析："+ new String(encodeArray,"UTF-8"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
