package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "teachers")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Teacher extends User{

    private UUID id;

//    @NotNull(message = "Имя преподавателя не может быть пустым")
    private String first_name;

//    @NotNull(message = "Фамилия преподавателя не может быть пустым")
    private String last_name;

    private String patronymic;

    private String login;

    private String email;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Material> materials = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "teacher" ,fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();

    @JsonIgnore
    public String getTeacherName(){
        return last_name + " " + first_name + " " + patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Teacher teacher = (Teacher) o;
        return id != null && Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addSubjects(Subject subject){
        this.subjects.add(subject);
        subject.setTeacher(this);
    }

    public void addSubjects(Set<Subject> subjects){
        subjects.forEach(this::addSubjects);
    }

    public void addRooms(Room room){
        this.rooms.add(room);
        room.setTeacher(this);
    }

    public void addRooms(Set<Room> rooms){
        rooms.forEach(this::addRooms);
    }

    public void addMaterials(Material material){
        this.materials.add(material);
        material.setTeacher(this);
    }

    public void addMaterials(Set<Material> materials){
        materials.forEach(this::addMaterials);
    }

    public void addGroups(Group group){
        this.groups.add(group);
        group.getTeachers().add(this);
    }

    public void addGroups(Set<Group> groups){
        groups.forEach(this::addGroups);
    }

    public void addTasks(Task task){
        this.tasks.add(task);
        task.setTeacher(this);
    }

    public void addTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }
}
