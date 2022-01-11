package com.example.diplom.entities;


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
//@Builder
@Getter
@Setter
@ToString
public class Student extends User{

//    @Id
//    @GeneratedValue
    private UUID id;

    private String first_name;

    private String last_name;

    private String patronymic;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ToString.Exclude
    private Group group;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Solution> solutions = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();

    public String getName(){
        return last_name + " " + first_name + " " + patronymic;
    }

    public void addGroup(Group group){
        group.addStudent(this);
    }

    public void addSolution(Solution solution){
        solutions.add(solution);
        solution.setStudent(this);
    }

    public void removeSolution(Solution solution){
        solutions.remove(solution);
        solution.setStudent(null);
    }

    public void addSubject(Subject subject){
        subjects.add(subject);
        subject.setStudent(this);
    }

    public void removeSubject(Subject subject){
        subjects.remove(subject);
        subject.setStudent(null);
    }

    public void addTask(Task task){
        tasks.add(task);
        task.addStudent(this);
    }

    public void removeTask(Task task){
        tasks.remove(task);
        task.getStudents().remove(this);
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
}
