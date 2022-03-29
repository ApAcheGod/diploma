package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.NaturalId;

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
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    @ToString.Exclude
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.MERGE } )
    @JoinTable(
            name = "groups_rooms",
            joinColumns = {@JoinColumn(name = "groups_id")},
            inverseJoinColumns = {@JoinColumn(name = "rooms_id")}
    )
    @ToString.Exclude
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();

    public void addStudents(Student student){
        students.add(student);
        student.setGroup(this);
    }

    public void addStudents(Set<Student> students){
        students.forEach(this::addStudents);
    }

    public void addSubjects(Subject subject){
        subjects.add(subject);
        subject.getGroups().add(this);
    }

    public void addSubjects(Set<Subject> subjects){
        subjects.forEach(this::addSubjects);
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

//    public void setRooms(Room room){
//        this.rooms.add(room);
//    }
//    public void setRooms(Set<Room> rooms){
//        this.
//    }

    public void addTasks(Task task){
        tasks.add(task);
        task.getGroups().add(this);
    }
    public void addTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Group group = (Group) o;
//
//        if (id != null ? !id.equals(group.id) : group.id != null) return false;
//        return name != null ? name.equals(group.name) : group.name == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }
}
