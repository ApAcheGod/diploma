package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
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
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Group {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Название группы не может быть пустым")
    @Column(name = "group_name")
    @NaturalId(mutable = true)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference
    private Set<Task> tasks = new HashSet<>();

//    public void addStudent(Student student){
//        students.add(student);
//        student.setGroup(this);
//    }
//
//    public void setStudents(Set<Student> students){
//        students.forEach(this::addStudent);
//    }
//
//    public void removeStudent(Student student){
//        students.remove(student);
//        student.setGroup(null);
//    }
//
//    public void setSubjects(Set<Subject> subjects){
//        subjects.forEach(this::addSubject);
//    }
//
//    public void addSubject(Subject subject){
//        subjects.add(subject); // TODO если добавить предмет группе - он должен добавиться всем студентам группы
//        subject.getGroups().add(this);
//        this.setTask(subject.getTasks());
//        students.forEach(student -> {
//            student.addSubject(subject);
//        });
//    }
//
//    public void removeSubject(Subject subject){
//        subjects.remove(subject); // TODO если удалить предмет у группы - он должен удалиться у всех студентов этой группы, но если студент прошел этот предмет что будет
//        subject.getGroups().remove(this);
//    }
//
//    public void addTeacher(Teacher teacher){
//        teachers.add(teacher);
//        teacher.getGroups().add(this);
//    }
//
//    public void removeTeacher(Teacher teacher){
//        teachers.remove(teacher);
//        teacher.getGroups().remove(this);
//    }
//
//    public void addRoom(Room room){
//        rooms.add(room);
//        room.getGroups().add(this);
//        teachers.add(room.getTeacher());
//        this.setSubjects(room.getSubjects());
//    }
//
//    public void removeRoom(Room room){
//        rooms.remove(room);
//        room.getGroups().remove(this);
//    }
//
//
//    public void setTask(Set<Task> tasks){
//        tasks.forEach(this::addTask);
//    }
//
//    public void addTask(Task task){
//        tasks.add(task);
//        task.getGroups().add(this);
//        students.forEach(student -> student.addTask(task));
//    }
//
//    public void removeTask(Task task){
//        tasks.remove(task);
//        task.getGroups().remove(this);
//    }

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
