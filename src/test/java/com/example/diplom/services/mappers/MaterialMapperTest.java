//package com.example.diplom.services.mappers;
//
//import com.example.diplom.entities.Material;
//import com.example.diplom.entities.Subject;
//import com.example.diplom.entities.Teacher;
//import com.example.diplom.entities.dto.MaterialDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//
//
//@SpringBootTest
//class MaterialMapperTest {
//    @Autowired
//    private MaterialMapper materialMapper;
//
//    @Test
//    public void toEntity(){
//        Teacher teacher = new Teacher();
//        teacher.setFirst_name("teac");
//        teacher.setId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"));
//        MaterialDto materialDto = MaterialDto
//                .builder()
//                .subjectId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"))
//                .teacherId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"))
//                .name("material")
//                .build();
//
//        Assertions.assertEquals(materialDto.getSubjectId(), materialMapper.toEntity(materialDto).getSubject().getId());
//        Assertions.assertEquals(materialDto.getTeacherId(), materialMapper.toEntity(materialDto).getTeacher().getId());
//    }
//
//    @Test
//    public void toDto(){
//        Teacher teacher = new Teacher();
//        teacher.setFirst_name("teac");
//        teacher.setId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"));
//        Material material = Material.builder()
//                .id(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"))
//                .subject(Subject.builder()
//                        .name("subject").id(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19")).build())
//                .teacher(teacher)
//                .build();
//
//        Assertions.assertEquals(material.getSubject().getId(), materialMapper.toDto(material).getSubjectId());
//        Assertions.assertEquals(material.getTeacher().getId(), materialMapper.toDto(material).getTeacherId());
//    }
//
//}