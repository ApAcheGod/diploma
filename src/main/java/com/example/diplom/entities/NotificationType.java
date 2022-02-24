package com.example.diplom.entities;

public enum NotificationType {

    NEW_TASK("Новое задание"),
    NEW_EXAMINATION("Новое задание для проверки"),
    MARK("Оценка");

    private final String title;

    NotificationType(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
