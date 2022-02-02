package com.example.diplom.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

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
public class Student extends User{

    private UUID id;

    private String first_name;

    private String last_name;

    private String patronymic;

    private String login;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonBackReference
    private Group group;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Solution> solutions = new HashSet<>();

//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @JsonManagedReference
//    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonBackReference
    private Set<Task> tasks = new HashSet<>();

    @JsonIgnore
    public String getName(){
        return last_name + " " + first_name + " " + patronymic;
    }

//    public void addGroup(Group group){
//        group.addStudent(this);
//    }
//
//    public void addSolution(Solution solution){
//        solutions.add(solution);
//        solution.setStudent(this);
//    }
//
//    public void removeSolution(Solution solution){
//        solutions.remove(solution);
//        solution.setStudent(null);
//    }
//
//    public void addSubject(Subject subject){
//        subjects.add(subject);
//        subject.addStudent(this);
//    }
//
//    public void removeSubject(Subject subject){
//        subjects.remove(subject);
//        subject.setStudent(null);
//    }
//
//    public void addTask(Task task){
//        tasks.add(task);
//        task.addStudent(this);
//    }
//
//    public void removeTask(Task task){
//        tasks.remove(task);
//        task.getStudents().remove(this);
//    }

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
