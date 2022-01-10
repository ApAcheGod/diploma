package com.example.diplom.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@Entity
//@Table(schema = "diploma", name = "journals")
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Builder
public class Journal {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    @ManyToMany
//    @JoinColumn(name = "teacher_id")
//    private Set<Teacher> teachers = new HashSet<>();

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "subject_id", referencedColumnName = "id")
//    @ManyToMany
//    @JoinColumn(name = "subject_id")
//    private Set<Subject> subject = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "journal", fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private Set<Solution> solutions = new HashSet<>();

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "group_id", referencedColumnName = "id")
//    private Set<Group> groups = new HashSet<>();

//    private int mark;

//    public void addSubject(Subject subject){
//        this.subject = subject;
//        subject.setJournal(this);
//    }
//
//    public void addTeacher(Teacher teacher){
//        this.teacher = teacher;
//        teacher.setJournal(this);
//    }
//
//    public void addSolution(Solution solution){
//        solutions.add(solution);
//        solution.setJournal(this);
//    }
//
//    public void addGroup(Group group){
//        groups.add(group);
//        group.setJournal(this);
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Journal journal = (Journal) o;
//        return id != null && Objects.equals(id, journal.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}

