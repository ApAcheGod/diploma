package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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

//    @NotNull(message = "Название задания не может быть пусытм")
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

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (min_rating != task.min_rating) return false;
        if (max_rating != task.max_rating) return false;
        if (count_of_attempts != task.count_of_attempts) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (students != null ? !students.equals(task.students) : task.students != null) return false;
        if (subject != null ? !subject.equals(task.subject) : task.subject != null) return false;
        if (teacher != null ? !teacher.equals(task.teacher) : task.teacher != null) return false;
        if (date_of_creation != null ? !date_of_creation.equals(task.date_of_creation) : task.date_of_creation != null)
            return false;
        if (last_date_of_delivery != null ? !last_date_of_delivery.equals(task.last_date_of_delivery) : task.last_date_of_delivery != null)
            return false;
        if (isTemporal != null ? !isTemporal.equals(task.isTemporal) : task.isTemporal != null) return false;
        if (isMandatory != null ? !isMandatory.equals(task.isMandatory) : task.isMandatory != null) return false;
        if (taskType != task.taskType) return false;
        if (text != null ? !text.equals(task.text) : task.text != null) return false;
        return solutions != null ? solutions.equals(task.solutions) : task.solutions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (date_of_creation != null ? date_of_creation.hashCode() : 0);
        result = 31 * result + (last_date_of_delivery != null ? last_date_of_delivery.hashCode() : 0);
        result = 31 * result + min_rating;
        result = 31 * result + max_rating;
        result = 31 * result + (isTemporal != null ? isTemporal.hashCode() : 0);
        result = 31 * result + (isMandatory != null ? isMandatory.hashCode() : 0);
        result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
        result = 31 * result + count_of_attempts;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (solutions != null ? solutions.hashCode() : 0);
        return result;
    }
}
