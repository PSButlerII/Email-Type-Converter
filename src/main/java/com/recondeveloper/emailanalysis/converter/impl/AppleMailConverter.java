package com.recondeveloper.emailanalysis.converter.impl;

import com.recondeveloper.emailanalysis.converter.EmailConverter;

import javax.mail.internet.MimeMessage;

public class AppleMailConverter implements EmailConverter {
    @Override
    public MimeMessage readEmail(String filePath) {
        return null;
    }

    @Override
    public MimeMessage convertEmail(MimeMessage email, String targetFormat) {
        return null;
    }

    @Override
    public void saveEmail(MimeMessage email, String savePath) {

    }
}
