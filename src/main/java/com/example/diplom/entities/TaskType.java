package com.example.diplom.entities;

public enum TaskType {
    CREDIT("Зачёт"),
    EXAM("Экзамен"),
    CHECK_POINT("Контрольная точка"),
    HOME_WORK("Домашняя работа"),
    INDIVIDUAL_WORK("Самостоятельная работа");

    private final String title;

    TaskType(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

}
