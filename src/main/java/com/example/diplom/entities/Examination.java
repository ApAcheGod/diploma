package com.example.diplom.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "diploma", name = "examinations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@EntityListeners(AuditingEntityListener.class)
@BatchSize(size = 20)
public class Examination {

    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    private LocalDateTime dateOfValuation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @BatchSize(size = 20)
    private Solution solution;

    @Enumerated(EnumType.STRING)
    @BatchSize(size = 20)
    private ExaminationStatus examinationStatus;

    private String comment;

}
