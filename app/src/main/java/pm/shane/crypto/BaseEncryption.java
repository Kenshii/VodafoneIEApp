package pm.shane.crypto;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import pm.shane.mobileapp.MainApp;
import pm.shane.mobileapp.R;

public class BaseEncryption {

    private static final Random random = new SecureRandom();
    private static AESEncrypted lastAesEncrypted;

    private static byte[] randomBytes(int length) { // Must be alphanumeric.
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString().getBytes(Charset.forName("UTF-8"));
    }

    public static byte[] getRandomKey() {
        return randomBytes(32);
    }

    public static byte[] getRandomIV() {
        return randomBytes(16);
    }

    private static String bytesToBase64String(byte[] key) {
        return new String(Base64.encode(key, 2));
    }

    public static String rsaEncryptBytes(byte[] inArray) {
        try {
            PublicKey key = CertificateFactory.getInstance("X.509")
                .generateCertificate(MainApp.getContext().getResources().openRawResource(R.raw.rsa_cert))
                .getPublicKey();
            Cipher pkCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            pkCipher.init(1, key);
            return bytesToBase64String(pkCipher.doFinal(inArray));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AESEncrypted aesEncryptString(String originalInput) {
        try {
            byte[] inputArray = originalInput.getBytes(Charset.forName("UTF-8"));
            byte[] keyArray = getRandomKey();
            byte[] ivArray = getRandomIV();
            SecretKeySpec keySpec = new SecretKeySpec(keyArray, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivArray);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(1, keySpec, ivSpec);
            String aesInputString = bytesToBase64String(cipher.doFinal(inputArray));
            String aesKey = rsaEncryptBytes(keyArray);
            String aesIv = rsaEncryptBytes(ivArray);
            setLastAesEncrypted(new AESEncrypted(inputArray, keyArray, ivArray, aesInputString, aesKey, aesIv));
            return getLastAesEncrypted();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static String aesDecryptString(String input, byte[] keyArray, byte[] ivArray) {
        try {
            byte[] inputArray = Base64.decode(input, 0);
            Key secretKeySpec = new SecretKeySpec(keyArray, "AES");
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(ivArray);
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance.init(2, secretKeySpec, ivParameterSpec);
            return new String(instance.doFinal(inputArray));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setLastAesEncrypted(AESEncrypted lastAesEncrypted) {
        BaseEncryption.lastAesEncrypted = lastAesEncrypted;
    }

    public static AESEncrypted getLastAesEncrypted() {
        return lastAesEncrypted;
    }

}
