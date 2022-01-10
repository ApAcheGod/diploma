package com.example.diplom.entities;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Student student;

    public void addTeacher(Teacher teacher){
        teacher.addSubject(this);
    }

    public void removeTeacher(Teacher teacher){
        teacher.removeSubject(this);
    }

    public void addMaterial(Material material){
        materials.add(material);
        material.setSubject(this);
    }

    public void removeMaterial(Material material){
        materials.remove(material);
        material.setSubject(null);
    }

    public void addTask(Task task){
        tasks.add(task);
        task.setSubject(this);
    }

    public void removeTask(Task task){
        tasks.remove(task);
        task.setSubject(null);
    }

    public void addGroup(Group group){
        group.addSubject(this);
    }

    public void removeGroup(Group group){
        group.removeSubject(this);
    }

    public void addRoom(Room room){
        room.addSubject(this);
    }

    public void removeRoom(Room room){
        room.removeSubject(this);
    }

    public void addStudent(Student student){
        student.addSubject(this);
    }

    public void removeStudent(Student student){
        student.removeSubject(this);
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
