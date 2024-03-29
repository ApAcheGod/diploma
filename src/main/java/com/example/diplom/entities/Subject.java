package com.example.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "subjects")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@BatchSize(size = 20)
public class Subject {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "subject_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ToString.Exclude
    @BatchSize(size = 20)
    private Teacher teacher;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Material> materials = new HashSet<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "subjects_groups",
            joinColumns = {@JoinColumn(name = "subjects_id")},
            inverseJoinColumns = {@JoinColumn(name = "groups_id")}
    )
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Group> groups = new HashSet<>();

    public void deleteLinks(){
        deleteMaterials();
        deleteTasks();
        deleteGroups();
    }

    private void deleteMaterials(){
        this.materials.forEach(material -> material.setSubject(null));
    }

    private void deleteTasks(){
        this.tasks.forEach(task -> task.setSubject(null));
    }

    private void deleteGroups(){
        this.getGroups().forEach(group -> group.getSubjects().remove(this));
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Room room;

    public void addTeacher(Teacher teacher){
        this.teacher = teacher;
        teacher.getSubjects().add(this);
    }

    public void addMaterials(Material material){
        materials.add(material);
        material.setSubject(this);
    }


    public void addTasks(Task task){
        tasks.add(task);
        task.setSubject(this);
    }


    public void addRoom(Room room){
        this.room = room;
        room.getSubjects().add(this);
    }

    public void addGroups(Group group) {
        this.groups.add(group);
        group.getSubjects().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (!Objects.equals(id, subject.id)) return false;
        return Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
