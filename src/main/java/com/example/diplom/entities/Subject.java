package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "subjects")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Subject {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Название предмета не может быть пустым")
    @Column(name = "subject_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ToString.Exclude
    private Teacher teacher;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Material> materials = new HashSet<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Group> groups = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Room room;

    public void addTeacher(Teacher teacher){
        this.teacher = teacher;
        teacher.getSubjects().add(this);
    }

    public void addMaterials(Material material){
        materials.add(material);
        material.setSubject(this);
    }

    public void addMaterials(Set<Material> materials){
        materials.forEach(this::addMaterials);
    }

    public void addTasks(Task task){
        tasks.add(task);
        task.setSubject(this);
    }

    public void adTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }

    public void addGroups(Group group){
        groups.add(group);
        group.getSubjects().add(this);
    }

    public void addGroups(Set<Group> groups){
        groups.forEach(this::addGroups);
    }

    public void addRoom(Room room){
        this.room = room;
        room.getSubjects().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subject subject = (Subject) o;
        return id != null && Objects.equals(id, subject.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
