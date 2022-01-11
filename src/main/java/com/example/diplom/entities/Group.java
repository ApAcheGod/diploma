package com.example.diplom.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
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
public class Group {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Название группы не может быть пустым")
    @Column(name = "group_name")
    @NaturalId(mutable = true)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public void addStudent(Student student){
        students.add(student);
        student.setGroup(this);
    }

    public void setStudents(Set<Student> students){
        students.forEach(this::addStudent);
    }

    public void removeStudent(Student student){
        students.remove(student);
        student.setGroup(null);
    }

    public void addSubject(Subject subject){
        subjects.add(subject); // TODO если добавить предмет группе - он должен добавиться всем студентам группы
        subject.getGroups().add(this);
    }

    public void removeSubject(Subject subject){
        subjects.remove(subject); // TODO если удалить предмет у группы - он должен удалиться у всех студентов этой группы, но если студент прошел этот предмет что будет
        subject.getGroups().remove(this);
    }

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
        teacher.getGroups().add(this);
    }

    public void removeTeacher(Teacher teacher){
        teachers.remove(teacher);
        teacher.getGroups().remove(this);
    }

    public void addRoom(Room room){
        rooms.add(room);
        room.getGroups().add(this);
    }

    public void removeRoom(Room room){
        rooms.remove(room);
        room.getGroups().remove(this);
    }

    public void addTask(Task task){
        tasks.add(task);
        task.getGroups().add(this);
    }

    public void removeTask(Task task){
        tasks.remove(task);
        task.getGroups().remove(this);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Group group = (Group) o;
//        return id != null && Objects.equals(id, group.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!Objects.equals(id, group.id)) return false;
        if (!Objects.equals(name, group.name)) return false;
        if (!Objects.equals(students, group.students)) return false;
        if (!Objects.equals(subjects, group.subjects)) return false;
        if (!Objects.equals(teachers, group.teachers)) return false;
        if (!Objects.equals(rooms, group.rooms)) return false;
        return Objects.equals(tasks, group.tasks);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        result = 31 * result + (subjects != null ? subjects.hashCode() : 0);
        result = 31 * result + (teachers != null ? teachers.hashCode() : 0);
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}
