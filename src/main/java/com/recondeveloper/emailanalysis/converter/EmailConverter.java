package com.recondeveloper.emailanalysis.converter;

import javax.mail.internet.MimeMessage;

public interface EmailConverter {
    MimeMessage readEmail(String filePath);
    MimeMessage convertEmail(MimeMessage email, String targetFormat);
    void saveEmail(MimeMessage email, String savePath);
}

