package ru.nvy;

import ru.nvy.service.GeneralService;

public class Main {

    private static final String PATH1 = "C:\\Users\\NVY\\Standard\\Documents\\GitHub\\University\\TAYK\\Lab3\\src\\main\\java\\ru\\nvy\\testInput\\test1.txt";
    private static final String PATH2 = "C:\\Users\\NVY\\Standard\\Documents\\GitHub\\University\\TAYK\\Lab3\\src\\main\\java\\ru\\nvy\\testInput\\test2.txt";
    private static final String PATH3 = "C:\\Users\\NVY\\Standard\\Documents\\GitHub\\University\\TAYK\\Lab3\\src\\main\\java\\ru\\nvy\\testInput\\test3.txt";

    public static void main(String[] args) {
        GeneralService.runApp(PATH2);
    }
}