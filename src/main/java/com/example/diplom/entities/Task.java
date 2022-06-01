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
@BatchSize(size = 20)
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "task_name")
    private String name;

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


    public void addSubjects(Subject subject){
        this.subject = subject;
        subject.getTasks().add(this);
    }

    public void addTeacher(Teacher teacher){
        this.teacher = teacher;
        teacher.getTasks().add(this);
    }

    public void addSolutions(Solution solution){
        solutions.add(solution);
        solution.setTask(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!Objects.equals(id, task.id)) return false;
        if (!Objects.equals(name, task.name)) return false;
        if (!Objects.equals(subject, task.subject)) return false;
        if (!Objects.equals(teacher, task.teacher)) return false;
        if (!Objects.equals(date_of_creation, task.date_of_creation))
            return false;
        if (!Objects.equals(text, task.text)) return false;
        return Objects.equals(solutions, task.solutions);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (date_of_creation != null ? date_of_creation.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (solutions != null ? solutions.hashCode() : 0);
        return result;
    }
}
