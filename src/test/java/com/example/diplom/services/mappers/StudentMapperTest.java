package com.example.diplom.services.mappers;

import com.example.diplom.entities.Group;
import com.example.diplom.entities.Student;
import com.example.diplom.entities.dto.StudentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;



@SpringBootTest
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void toEntity(){
        StudentDto studentDto = StudentDto
                .builder()
                .groupId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"))
                .first_name("nik")
                .last_name("alp")
                .patronymic("ser")
                .email("email")
                .build();

        Assertions.assertEquals(studentDto.getGroupId(), studentMapper.toEntity(studentDto).getGroup().getId());
    }

    @Test
    public void toDto(){
        Group group = new Group();
        group.setId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"));
        Student student = new Student();
                student.setLast_name("asd");
                student.setEmail("asd");
                student.setPatronymic("fsdf");
                student.setGroup(group);
                student.setFirst_name("dasdaxsd");

        Assertions.assertEquals(student.getGroup().getId(), studentMapper.toDto(student).getGroupId());
    }
}