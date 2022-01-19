package com.example.diplom.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Embeddable
public class StudentTask implements Serializable {

    @Column(name = "id")
    private UUID id;

    @Column(name = "task_id")
    private UUID task;

}
