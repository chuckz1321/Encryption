package com.zzuli.eassy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Author: Administrator
 * Date: 2018-1-10 10:25
 * Created with IDEA
 */
public class RSAImplement {
    //coming from Euler Theorem
    private final static BigInteger e = new BigInteger("65537");

    private static BigInteger d;

    private static BigInteger n;

    //essential variable (e,d,n)
    //public (n,e)
    //private (n,d)
    public void generateVariable(BigInteger a, BigInteger b, int length){
        if(length % 512 != 0) return ;
        if(a.gcd(b) == BigInteger.valueOf(1)) return ;
        n = a.multiply(b);
        byte[] byteN = n.toByteArray();
//        if(byteN.length != length) return false;
        BigInteger rulerVar = a.subtract(BigInteger.valueOf(1)).multiply(b.subtract(BigInteger.valueOf(1)));
//        e*x + rulerVar*y = 1
        List<BigInteger> varList = ex_gcd(e,rulerVar);
        d = varList.get(1);
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

    public static void main(String[] args) {
        RSAImplement rsaImplement  = new RSAImplement();
        rsaImplement.generateVariable(new BigInteger("619"),new BigInteger("631"),512);
    }
}
