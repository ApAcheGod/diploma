package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "rooms")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
//@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Room {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Название комнаты не может быть пустым")
    @Column(name = "room_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonBackReference
    private Teacher teacher;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
//    @JsonBackReference
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Subject> subjects = new HashSet<>();

//    public void addSubject(Subject subject){
//        subjects.add(subject);
//        subject.setRoom(this);
//    }
//    public void removeSubject(Subject subject){
//        subjects.remove(subject);
//        subject.setRoom(null);
//    }
//
//    public void addTeacher(Teacher teacher){
//        this.teacher = teacher;
//        teacher.getRooms().add(this);
////        teacher.addRoom(this); // TODO проверить как рбаотает
//    }
//
//    public void removeTeacher(){
//        teacher.getRooms().remove(this);
//        this.teacher = null;
//    }
//
//    public void setGroup(Set<Group> group){
//        group.forEach(this::addGroup);
//    }
//
//    public void addGroup(Group group){
//        groups.add(group);
//        group.addRoom(this);
//    }
//
//    public void removeGroup(Group group){
//        groups.remove(group);
//        group.getRooms().remove(this);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addTeacher(Teacher teacher){
        this.teacher = teacher;
        teacher.getRooms().add(this);
    }

    public void addSubjects(Subject subject){
        this.subjects.add(subject);
        subject.setRoom(this);
    }

    public void addSubjects(Set<Subject> subjects){
        subjects.forEach(this::addSubjects);
    }


}
