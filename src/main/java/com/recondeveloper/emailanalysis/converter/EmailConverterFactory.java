package com.recondeveloper.emailanalysis.converter;

import com.recondeveloper.emailanalysis.converter.impl.AppleMailConverter;
import com.recondeveloper.emailanalysis.converter.impl.OutlookEmailConverter;

public class EmailConverterFactory {
    public static EmailConverter getConverter(String fileType) {
        switch (fileType.toLowerCase()) {
            case "msg":
            case "pst":
            case "eml":
                return new OutlookEmailConverter();
            case "emlx":
                return new AppleMailConverter();
            // Add more cases for other file types
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}

