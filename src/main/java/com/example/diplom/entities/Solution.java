package com.example.diplom.entities;

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
public class Solution {

//    @EmbeddedId TODO
//    private StudentTask id;

    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    private LocalDateTime date_of_delivery;

//    private LocalDateTime date_od_valuation; // TODO перенести в новую сущность оценки решения

    private String text;

//    private int count_of_attempts; // количество сдачь  // TODO перенести в новую сущность оценки решения

//    private int mark; // TODO перенести в новую сущность оценки решения

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Task task;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "journal_id", referencedColumnName = "id")
//    @ToString.Exclude
//    private Set<Journal> journals = new HashSet<>();

    public void addStudent(Student student){
        student.addSolution(this); // TODO проверить как работает
    }

//    public void removeSolution(){
//
//    }

    public void addTask(Task task){
//        task.addSolution(this);
        this.task = task;
        task.getSolutions().add(this);
    }

//    public void addJournal(Journal journal){
//        journal.addSolution(this);
//    }


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
}
