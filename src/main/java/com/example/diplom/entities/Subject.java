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
@Table(schema = "diploma", name = "subjects")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Subject {

    @Id
    @GeneratedValue
    private UUID id;

//    @NotNull(message = "Название предмета не может быть пустым")
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

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Set<Task> tasks = new HashSet<>();

    public void deleteLinks(){
        deleteMaterials();
        deleteTasks();
    }

    private void deleteMaterials(){
        this.materials.forEach(material -> material.setSubject(null));
    }

    private void deleteTasks(){
        this.tasks.forEach(task -> task.setSubject(null));
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

    public void addMaterials(Set<Material> materials){
        materials.forEach(this::addMaterials);
    }

    public void addTasks(Task task){
        tasks.add(task);
        task.setSubject(this);
    }

    public void addTasks(Set<Task> tasks){
        tasks.forEach(this::addTasks);
    }

    public void addRoom(Room room){
        this.room = room;
        room.getSubjects().add(this);
    }
}
