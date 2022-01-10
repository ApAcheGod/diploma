package com.example.diplom.entities;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Embeddable
public class StudentTask implements Serializable {

//    @JoinColumn(name = "id", referencedColumnName = "id")
    @Column(name = "id")
    private UUID id;

//    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @Column(name = "task_id")
    private UUID task;

}
