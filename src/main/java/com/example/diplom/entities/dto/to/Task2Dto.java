package com.example.diplom.entities.dto.to;

import com.example.diplom.entities.TaskType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Task2Dto {

    private UUID id;

    private String name;

    private UUID subjectId;

    private UUID teacherId;

    private LocalDateTime last_date_of_delivery;

    private int min_rating;

    private int max_rating;

    private Boolean isTemporal;

    private Boolean isMandatory; // обязательное задание

    private TaskType taskType;

    private int count_of_attempts; // количество сдачь

    private String text;
}
