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
    @NaturalId(mutable = true)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
    public void addTasks(Task task){
        tasks.add(task);
        task.getGroups().add(this);
    }
    public void addTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != null ? !id.equals(group.id) : group.id != null) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        if (students != null ? !students.equals(group.students) : group.students != null) return false;
        if (subjects != null ? !subjects.equals(group.subjects) : group.subjects != null) return false;
        if (teachers != null ? !teachers.equals(group.teachers) : group.teachers != null) return false;
        return rooms != null ? rooms.equals(group.rooms) : group.rooms == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        result = 31 * result + (subjects != null ? subjects.hashCode() : 0);
        result = 31 * result + (teachers != null ? teachers.hashCode() : 0);
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }
}
