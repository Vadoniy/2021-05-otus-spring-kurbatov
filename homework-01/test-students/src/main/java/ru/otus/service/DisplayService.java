package ru.otus.service;

import java.io.OutputStream;

public interface DisplayService {

    void showText(String textToShow, OutputStream outputStream);

    String getInputString();
}
