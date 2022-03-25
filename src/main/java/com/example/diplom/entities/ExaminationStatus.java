package com.example.diplom.entities;

public enum ExaminationStatus {

    ACCEPTED("Зачтено"),
    NOT_ACCEPTED("Не зачтено"),
    REWORK("Доработка");

    private final String title;

    ExaminationStatus(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public static ExaminationStatus getNameByTitle(String title){
        for (ExaminationStatus examinationStatus: ExaminationStatus.values()){
            if (title.equals(examinationStatus.getTitle())){
                return examinationStatus;
            }
        }
        return null;
    }
}
