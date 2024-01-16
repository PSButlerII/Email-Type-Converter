package com.recondeveloper.emailanalysis;

import com.recondeveloper.emailanalysis.converter.EmailConversionService;
import com.recondeveloper.emailanalysis.parser.EmailParser;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String EMAIL_FILE_PATH = "";
    private String EMAIL_SAVE_PATH = "C:\\EmailConvertionTestFolder\"C:\\Users\\Student\\Desktop\\A US Marine Veterans Story - January, 2024.eml";
    private static String EMAIL_FORMAT = "json";

    private final EmailParser emailParser;
    private static Scanner userInput = new Scanner(System.in);

    private static EmailConversionService emailConversionService = new EmailConversionService();
    public App(EmailParser emailParser, Scanner userInput, EmailConversionService emailConversionService) {
        this.emailParser = emailParser;
        App.userInput = userInput;
        App.emailConversionService = emailConversionService;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println(" Enter the email file location");
        EMAIL_FILE_PATH = userInput.nextLine();
        System.out.println(" Enter type to convert to");
        EMAIL_FORMAT = userInput.nextLine();
        emailConversionService.processEmail(EMAIL_FILE_PATH, EMAIL_FORMAT);

    }
}
