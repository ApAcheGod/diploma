package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.BatchSize;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "tasks_groups",
            joinColumns = {@JoinColumn(name = "tasks_id")},
            inverseJoinColumns = {@JoinColumn(name = "groups_id")}
    )
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Group> groups = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Teacher teacher;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // work
    @JsonFormat(pattern = "dd-MMMM-yyyy HH:mm")
    private LocalDateTime date_of_creation;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // work
    @JsonFormat(pattern = "dd-MMMM-yyyy HH:mm")
    private LocalDateTime last_date_of_delivery;

    private String text;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Solution> solutions = new HashSet<>();

    public void deleteLinks(){
        removeSolutions();
    }

    private void removeSolutions(){
        this.solutions.forEach(solution -> solution.setTask(null));
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

        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (subject != null ? !subject.equals(task.subject) : task.subject != null) return false;
        if (teacher != null ? !teacher.equals(task.teacher) : task.teacher != null) return false;
        if (date_of_creation != null ? !date_of_creation.equals(task.date_of_creation) : task.date_of_creation != null)
            return false;
        if (last_date_of_delivery != null ? !last_date_of_delivery.equals(task.last_date_of_delivery) : task.last_date_of_delivery != null)
            return false;
        if (text != null ? !text.equals(task.text) : task.text != null) return false;
        return solutions != null ? solutions.equals(task.solutions) : task.solutions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (date_of_creation != null ? date_of_creation.hashCode() : 0);
        result = 31 * result + (last_date_of_delivery != null ? last_date_of_delivery.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (solutions != null ? solutions.hashCode() : 0);
        return result;
    }
}
