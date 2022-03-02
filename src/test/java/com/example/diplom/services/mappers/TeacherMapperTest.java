//package com.example.diplom.services.mappers;
//
//import com.example.diplom.entities.Teacher;
//import com.example.diplom.entities.dto.TeacherDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//
//@SpringBootTest
//class TeacherMapperTest {
//
//    @Autowired
//    TeacherMapper teacherMapper;
//
//    @Test
//    public void toEntity(){
//        TeacherDto dto = TeacherDto.builder()
//                .email("adsasdasd")
//                .first_name("asdasdasd")
//                .id(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"))
//                .build();
//
//        Assertions.assertEquals(dto.getId(), teacherMapper.toEntity(dto).getId());
//    }
//
//    @Test
//    public void toDto(){
//
//        Teacher teacher = new Teacher();
//        teacher.setFirst_name("teac");
//        teacher.setId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"));
//
//        Assertions.assertEquals(teacher.getId(), teacherMapper.toDto(teacher).getId());
//    }
//}