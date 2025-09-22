package dev.hwiveloper.orbitlink.common.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Converter
@Slf4j
public class PrivateInfoConverter implements AttributeConverter<String, String> {

    @Value("${application.database.column.encrypt.key}")
    private String ENC_KEY;

    @Value("${application.database.column.encrypt.iv}")
    private String IV;

    private SecretKeySpec getSecretKeySpec() {
        byte[] keyBytes = ENC_KEY.getBytes(StandardCharsets.UTF_8);
        if (!(keyBytes.length == 16 || keyBytes.length == 24 || keyBytes.length == 32)) {
            throw new IllegalArgumentException("Invalid AES key length: " + keyBytes.length);
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    private IvParameterSpec getIvSpec() {
        byte[] ivBytes = IV.getBytes(StandardCharsets.UTF_8);
        if (ivBytes.length != 16) {
            throw new IllegalArgumentException("Invalid IV length: " + ivBytes.length);
        }
        return new IvParameterSpec(ivBytes);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(), getIvSpec());

            byte[] encryptedBytes = encryptCipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Encryption failed. Returning raw value. attr={}", attribute, e);
            return attribute; // fallback
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(), getIvSpec());

            byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("Decryption failed. Returning raw value. dbData={}", dbData, e);
            return dbData; // fallback
        }
    }
}