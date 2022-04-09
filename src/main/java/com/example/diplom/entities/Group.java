package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "groups")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Group {

    @Id
    @GeneratedValue
    private UUID id;

//    @NotNull(message = "Название группы не может быть пустым")
    @Column(name = "group_name")
//    @NaturalId(mutable = true)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.MERGE } )
    @JoinTable(
            name = "groups_rooms",
            joinColumns = {@JoinColumn(name = "groups_id")},
            inverseJoinColumns = {@JoinColumn(name = "rooms_id")}
    )
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Room> rooms = new HashSet<>();

    public void deleteLinks(){
        deleteRooms();
        deleteTeachers();
        deleteStudents();
    }

    private void deleteRooms(){
        this.rooms.forEach(room -> room.getGroups().remove(this));
    }

    private void deleteTeachers(){
        this.teachers.forEach(teacher -> teacher.getGroups().remove(this));
    }

    private void deleteStudents(){
        this.students.forEach(student -> student.setGroup(null));
    }


    public Set<Subject> getSubjects(){
        Set<Subject> subjects = new HashSet<>();
        rooms.forEach(room -> subjects.addAll(room.getSubjects()));
        return subjects;
    }

    public Set<Task> getTasks(){
        Set<Task> tasks = new HashSet<>();
        rooms.forEach(room -> room.getSubjects().forEach(subject -> tasks.addAll(subject.getTasks())));
        return tasks;
    }

    public Set<Material> getMaterials(){
        Set<Material> materials = new HashSet<>();
        rooms.forEach(room -> room.getSubjects().forEach(subject -> materials.addAll(subject.getMaterials())));
        return materials;
    }

    public void addStudents(Student student){
        students.add(student);
        student.setGroup(this);
    }

    public void addStudents(Set<Student> students){
        students.forEach(this::addStudents);
    }

    public void addTeachers(Teacher teacher){
        teachers.add(teacher);
        teacher.getGroups().add(this);
    }

    public void addTeachers(Set<Teacher> teachers){
        teachers.forEach(this::addTeachers);
    }

    public void addRooms(Room room){
        rooms.add(room);
        room.getGroups().add(this);
    }
    public void addRooms(Set<Room> rooms){
        rooms.forEach(this::addRooms);
    }

}
