package com.example.diplom.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "students")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@BatchSize(size = 100)
public class Student extends User{

    private UUID id;

    private String first_name;

    private String last_name;

    private String patronymic;

    private String login;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ToString.Exclude
    private Group group;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private Set<Solution> solutions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();

    @JsonIgnore
    public String getName(){
        return last_name + " " + first_name + " " + patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return id != null && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addGroup(Group group){
        this.group = group;
        group.getStudents().add(this);
    }

    public void addSolution(Solution solution){
        this.solutions.add(solution);
        solution.setStudent(this);
    }

    public void addSolution(Set<Solution> solutions){
        solutions.forEach(this::addSolution);
    }

    public void addTasks(Task task){
        tasks.add(task);
        task.getStudents().add(this);
    }

    public void addTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }

}