package com.zzuli.research;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class algorithmTest {
    private static String str = "56f4ds564f5sdf4sd65f56sd4f56sd4f5sd4f6sd456fsddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd大叔大叔大叔大叔4f56sd4f65sd4f56sd4f************************************************************************************************************************";
    public static void main(String args[]){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();

            BigInteger e = rsaPublicKey.getPublicExponent();
            BigInteger n = rsaPublicKey.getModulus();
            BigInteger d = rsaPrivateKey.getPrivateExponent();

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(str.getBytes());
            byte[] result = signature.sign();
            System.out.println(new BigInteger(result));

            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = keyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(str.getBytes());
            boolean b = signature.verify(result);
            System.out.println(b);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
