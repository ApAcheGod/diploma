package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "materials")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Material {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Название материала не может быть пустым")
    @Column(name = "material_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonBackReference
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonBackReference
    private Subject subject;

    private String text;

    public void addTeacher(Teacher teacher){
        this.teacher = teacher;
        teacher.getMaterials().add(this);
    }

    public void addTeacher(Set<Teacher> teachers){
        teachers.forEach(this::addTeacher);
    }

    public void addSubject(Subject subject){
        this.subject = subject;
        subject.getMaterials().add(this);
    }
    public void addSubject(Set<Subject> subjects){
        subjects.forEach(this::addSubject);
    }


//    public void addTeacher(Teacher teacher){
//        teacher.addMaterial(this); // TODO проверить как работает
//    }
//
//    public void removeTeacher(){
//        this.teacher = null;
//    }
//
//    public void addSubject(Subject subject){
//        subject.addMaterial(this);
//    }
//
//    public void removeSubject(){
//        this.subject = null;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Material material = (Material) o;
        return id != null && Objects.equals(id, material.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
