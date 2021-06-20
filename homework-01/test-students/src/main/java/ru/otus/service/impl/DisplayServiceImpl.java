package ru.otus.service.impl;

import ru.otus.service.DisplayService;

public class DisplayServiceImpl implements DisplayService {

    @Override
    public void showText(String textToShow) {
        System.out.println(textToShow);
    }
}
