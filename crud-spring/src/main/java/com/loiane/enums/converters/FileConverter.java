package com.loiane.enums.converters;

import java.util.Base64;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.web.multipart.MultipartFile;

@Converter(autoApply = true)
public class FileConverter implements AttributeConverter<MultipartFile, String> {

    @Override
    public String convertToDatabaseColumn(MultipartFile file) {
        if (file == null) {
            return null;
        }
        try {
            // Converte o arquivo para Base64 e retorna como uma string
            byte[] fileContent = file.getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public MultipartFile convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        try {
            // Converte a string Base64 para um array de bytes e cria um MultipartFile
            byte[] fileContent = Base64.getDecoder().decode(value);
            String fileName = "arquivo";
            return new org.springframework.mock.web.MockMultipartFile(fileName, fileName, null, fileContent);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}