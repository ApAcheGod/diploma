package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "solutions")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Solution {

    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    private LocalDateTime dateOfDelivery;

    private String text;

//    private int count_of_attempts; // количество сдачь  // TODO перенести в новую сущность оценки решения

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Solution solution = (Solution) o;
        return id != null && Objects.equals(id, solution.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addStudent(Student student){
        this.student = student;
        student.getSolutions().add(this);
    }

    public void addTask(Task task){
        this.task = task;
        task.getSolutions().add(this);
    }
}
