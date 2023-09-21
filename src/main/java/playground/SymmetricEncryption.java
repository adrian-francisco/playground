package playground;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SymmetricEncryption {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKey key = getSecretKey();
        byte[] iv = getIV();

        System.out.println("Secret Key: " + DatatypeConverter.printHexBinary(key.getEncoded()));
        System.out.println("IV: " + DatatypeConverter.printHexBinary(iv));

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec params = new GCMParameterSpec(128, iv);

        String message = "secret message";
        System.out.println("Original Message: " + message);

        // Encrypt
        cipher.init(Cipher.ENCRYPT_MODE, key, params);
        byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encrypted Message: " + DatatypeConverter.printHexBinary(encrypted));

        // Decrypt
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        String unencrypted = new String(cipher.doFinal(encrypted));
        System.out.println("Unencrypted Message: " + unencrypted);
    }

    private static SecretKey getSecretKey() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256, random);
        SecretKey key = generator.generateKey();
        return key;
    }
    
    private static byte[] getIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }
}
