package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "tasks")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Название задания не может быть пусытм")
    @Column(name = "task_name")
    private String name;

    @ManyToMany(mappedBy = "tasks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Student> students = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Group> groups = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // work
    @JsonFormat(pattern = "dd-MMMM-yyyy HH:mm")
    private LocalDateTime date_of_creation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // work
    @JsonFormat(pattern = "dd-MMMM-yyyy HH:mm")
    private LocalDateTime last_date_of_delivery;

    private int min_rating;

    private int max_rating;

    private Boolean isTemporal;

    private Boolean isMandatory; // обязательное задание

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private int count_of_attempts; // количество сдачь

    private String text;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Solution> solutions = new HashSet<>();

    public void addStudents(Student student){
        students.add(student);
        student.getTasks().add(this);
    }

    public void addStudents(Set<Student> students){
        students.forEach(this::addStudents);
    }

    public void addGroups(Group group){
        groups.add(group);
        group.getTasks().add(this);
    }

    public void addGroups(Set<Group> groups){
        groups.forEach(this::addGroups);
    }

    public void addSubjects(Subject subject){
        this.subject = subject;
        subject.getTasks().add(this);
    }
    public void addSubjects(Set<Subject> subjects){
        subjects.forEach(this::addSubjects);
    }

    public void addTeacher(Teacher teacher){
        this.teacher = teacher;
        teacher.getTasks().add(this);
    }

    public void addSolutions(Solution solution){
        solutions.add(solution);
        solution.setTask(this);
    }

    public void addSolutions(Set<Solution> solutions){
        solutions.forEach(this::addSolutions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
