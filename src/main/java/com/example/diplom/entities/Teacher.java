package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(schema = "diploma", name = "teachers")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
//@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Teacher extends User{

//    @Id
//    @GeneratedValue
    private UUID id;

    @NotNull(message = "Имя преподавателя не может быть пустым")
    private String first_name;

    @NotNull(message = "Фамилия преподавателя не может быть пустым")
    private String last_name;

    private String patronymic;

    private String login;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonManagedReference
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonManagedReference
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonManagedReference
    private Set<Material> materials = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonBackReference
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "teacher" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonManagedReference
    private Set<Task> tasks = new HashSet<>();

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "journal_id", referencedColumnName = "id")
//    private Journal journal;

    @JsonIgnore
    public String getName(){
        return last_name + " " + first_name + " " + patronymic;
    }

//    public void addSubject(Subject subject){
//        subjects.add(subject);
//        subject.setTeacher(this);
//    }
//
//    public void removeSubject(Subject subject){
//        subjects.remove(subject);
//        subject.setTeacher(null);
//    }
//
//    public void addRoom(Room room){
//        rooms.add(room);
//        room.setTeacher(this);
//    }
//
//    public void removeRoom(Room room){
//        rooms.remove(room);
//        room.setTeacher(null);
//    }
//
//    public void addMaterial(Material material){
//        materials.add(material);
//        material.setTeacher(this);
//    }
//
//    public void removeMaterial(Material material){
//        materials.remove(material);
//        material.setTeacher(null);
//    }
//
//    public void addGroup(Group group){
//        groups.add(group);
//        group.getTeachers().add(this);
//    }
//
//    public void removeGroup(Group group){
//        groups.remove(group);
//        group.getTeachers().remove(this);
//    }
//
//    public void addTask(Task task){
//        tasks.add(task);
//        task.setTeacher(this);
//    }
//
//    public void removeTask(Task task){
//        tasks.remove(task);
//        task.setTeacher(null);
//    }

//    public void addJournal(Journal journal){
//        journal.addTeacher(this);
//    }

//    public void removeJournal(){
//    }

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
