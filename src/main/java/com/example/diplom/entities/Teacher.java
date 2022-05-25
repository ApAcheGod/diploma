package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;

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
@BatchSize(size = 20)
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
    @BatchSize(size = 20)
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Material> materials = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "teachers_groups",
//            joinColumns = {@JoinColumn(name = "teachers_id")},
//            inverseJoinColumns = {@JoinColumn(name = "groups_id")}
//    )
//    @ToString.Exclude
//    @BatchSize(size = 20)
//    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "teacher" ,fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
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

    public void deleteLinks(){
        this.removeMaterials();
//        this.removeGroups();
        this.removeRooms();
        this.removeSubjects();
        this.removeTasks();
    }
    private void removeMaterials(){
        this.materials.forEach(material -> material.setTeacher(null));
        this.materials = new HashSet<>();
    }

    private void removeRooms(){
        this.rooms.forEach(room -> room.setTeacher(null));
        this.rooms = new HashSet<>();
    }

    private void removeTasks(){
        this.tasks.forEach(task -> task.setTeacher(null));
        this.tasks = new HashSet<>();
    }

    private void removeSubjects(){
        this.subjects.forEach(subject -> subject.setTeacher(null));
        this.subjects = new HashSet<>();
    }

//    private void removeGroups(){
//        this.groups.forEach(group -> group.getTeachers().remove(this));
//        this.groups = new HashSet<>();
//    }

    public void addMaterials(Set<Material> materials){
        materials.forEach(this::addMaterials);
    }

//    public void addGroups(Group group){
//        this.groups.add(group);
//        group.getTeachers().add(this);
//    }
//
//    public void addGroups(Set<Group> groups){
//        groups.forEach(this::addGroups);
//    }

    public void addTasks(Task task){
        this.tasks.add(task);
        task.setTeacher(this);
    }

    public void addTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }
}
