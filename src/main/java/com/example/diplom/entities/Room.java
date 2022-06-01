package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Table(schema = "diploma", name = "rooms")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@BatchSize(size = 20)
public class Room {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "room_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ToString.Exclude
    @BatchSize(size = 20)
    private Teacher teacher;

    @ManyToMany(mappedBy = "rooms")
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Subject> subjects = new HashSet<>();

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

}
