/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iss_project5;

/**
 *
 * @author Bcc
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.Arrays;

public class SymmetricWithMac {
    public static final SecureRandom secureRandom = new SecureRandom();
    public static final int RANDOM_BYTES_LENGTH = 16;
    public static final String MAC_ALGORITHM = "HMACSHA256";
    public static final String STRING_ENCODING = "ISO_8859_1";
    public static final String HEX_MAC_KEY = "AEB908AA1CEDFFDEA1F255640A05EEF6";

    static public String encrypt(String plainText, String hexAesKey, String hexMacKey, String macAlgorithm)
            throws Exception {
        if(plainText== null)
        {
            return "";
        }
        // compute the cipher
        byte[] getBytesFromString = hexAesKey.getBytes();
        BigInteger bigInteger = new BigInteger(1, getBytesFromString);
        String convertedResult = String.format("%x", bigInteger);
        byte[] decodedHexAesKey = DatatypeConverter.parseHexBinary(convertedResult);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedHexAesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] paddingBytes = new byte[RANDOM_BYTES_LENGTH];
        secureRandom.nextBytes(paddingBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(paddingBytes));
        byte[] encryptedText = cipher.doFinal(plainText.getBytes(STRING_ENCODING));

        // Prepend random to the encryptedText
        byte[] paddedCipher = concatByteArrays(paddingBytes, encryptedText);

        // Append message digest
        byte[] digest = computeDigest(paddedCipher, hexMacKey, macAlgorithm);
        byte[] completeText = concatByteArrays(paddedCipher, digest);

        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(completeText);
    }
    static public String decodeString(String text,String sessionKey) throws Exception {
        return SymmetricWithMac.decrypt(text,sessionKey,SymmetricWithMac.HEX_MAC_KEY,SymmetricWithMac.MAC_ALGORITHM);
    }

    static public String decrypt(String encryptedText, String hexAesKey, String hexMacKey,
                          String macAlgorithm) throws Exception {
        System.out.println("encryptedText: "+encryptedText);
        if(encryptedText.equals(""))
        {
            return "";
        }
        BASE64Decoder base64decoder = new BASE64Decoder();
        int macLength = Mac.getInstance(macAlgorithm).getMacLength();
        byte[] completeBytes = base64decoder.decodeBuffer(encryptedText);
        int macStartIndex = completeBytes.length - macLength;
        byte[] padding = Arrays.copyOfRange(completeBytes, 0, RANDOM_BYTES_LENGTH);
        byte[] paddedCipher = Arrays.copyOfRange(completeBytes, 0, macStartIndex);
        byte[] encryptedBytes = Arrays.copyOfRange(completeBytes, RANDOM_BYTES_LENGTH, macStartIndex);
        byte[] digestBytes = Arrays.copyOfRange(completeBytes, macStartIndex, completeBytes.length);


        byte[] computedDigest = computeDigest(paddedCipher, hexMacKey, macAlgorithm);
        if (!MessageDigest.isEqual(digestBytes, computedDigest)) {
            throw new RuntimeException("Message corrupted");
        }

        byte[] getBytesFromString = hexAesKey.getBytes();
        BigInteger bigInteger = new BigInteger(1, getBytesFromString);
        String convertedResult = String.format("%x", bigInteger);
        byte[] decodedHexAesKey = DatatypeConverter.parseHexBinary(convertedResult);
        SecretKeySpec keySpec = new SecretKeySpec(decodedHexAesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(padding));

        String plaintext = new String(cipher.doFinal(encryptedBytes), STRING_ENCODING);
        return plaintext;
    }

    static private byte[] concatByteArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    static private byte[] computeDigest(byte[] message, String hexMacKey, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] decodedHexMacKey = DatatypeConverter.parseHexBinary(hexMacKey);
        SecretKeySpec secretKeySpc = new SecretKeySpec(decodedHexMacKey, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpc);
        byte[] digest = mac.doFinal(message);
        return digest;
    }
}
