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
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", scope = Group.class)
@BatchSize(size = 20)
public class Group {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "group_name")
//    @NaturalId(mutable = true)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE } )
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY , cascade = {CascadeType.ALL})
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Subject> subjects = new HashSet<>();

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
        deleteSubjects();
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
    private void deleteSubjects(){
        this.subjects.forEach(subject -> subject.getGroups().remove(this));
    }

    public Set<Task> getTasks(){
        Set<Task> tasks = new HashSet<>();
        subjects.forEach(subject -> tasks.addAll((subject.getTasks())));
        return tasks;
    }
    public Set<Material> getMaterials(){
        Set<Material> materials = new HashSet<>();
        subjects.forEach(subject -> materials.addAll((subject.getMaterials())));
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

    public void addSubjects(Set<Subject> subjects){
        subjects.forEach(subject -> subject.getGroups().add(this));
        this.subjects.addAll(subjects);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != null ? !id.equals(group.id) : group.id != null) return false;
        return name != null ? name.equals(group.name) : group.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
