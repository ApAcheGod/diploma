package com.example.diplom.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "materials")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
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
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @ToString.Exclude
    private Subject subject;

    private String text;

    public void addTeacher(Teacher teacher){
//        this.teacher = teacher;
        teacher.addMaterial(this); // TODO проверить как работает
    }

    public void removeTeacher(){
        this.teacher = null;
    }

    public void addSubject(Subject subject){
        subject.addMaterial(this);
    }

    public void removeSubject(){
        this.subject = null;
    }

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
