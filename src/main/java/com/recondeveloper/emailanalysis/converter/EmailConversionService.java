package com.recondeveloper.emailanalysis.converter;

import com.recondeveloper.emailanalysis.model.Email;

import javax.mail.internet.MimeMessage;

public class EmailConversionService {
    public void processEmail(String filePath, String targetFormat) {
        String fileType = extractFileType(filePath); // Implement this method to determine file type
        EmailConverter converter = EmailConverterFactory.getConverter(fileType);

        MimeMessage email = converter.readEmail(filePath);
        MimeMessage convertedEmail = converter.convertEmail(email, targetFormat);
        converter.saveEmail(convertedEmail, constructSavePath(filePath, targetFormat)); // Implement constructSavePath
    }

    private String constructSavePath(String filePath, String targetFormat) {
        // TODO: Implement save path construction logic
        // take the file path and replace the file extension with the target format
        int lastPeriod = filePath.lastIndexOf(".");
        if (lastPeriod == -1) {
            return null;
        }
        filePath = filePath.substring(0, lastPeriod + 1);
        filePath = filePath + targetFormat;

        return filePath;
    }

    private String extractFileType(String filePath) {
        // TODO: Implement file type extraction logic

        // look for the last period in the file path and return the substring after it.  If there is no period, return null.
        int lastPeriod = filePath.lastIndexOf(".");
        if (lastPeriod == -1) {
            return null;
        }
        filePath = filePath.substring(lastPeriod + 1);

        return filePath;
    }

    // Additional methods like extractFileType and constructSavePath...
}

