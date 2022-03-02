//package com.example.diplom.services.mappers.mappers2;
//
//import com.example.diplom.entities.Group;
//import com.example.diplom.services.GroupService;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.stream.Collectors;
//
//@SpringBootTest
//@RequiredArgsConstructor
//class Group2MapperTest {
//    @Autowired
//    private Group2Mapper groupMapper;
//
//    @Autowired
//    private Task2Mapper task2Mapper;
//
//    @Autowired
//    private GroupService groupService;
//
//    @Test
//    public void toDto(){
//        Group group = groupService
//                .findAll()
//                .stream()
//                .map(Group::getTasks)
//
//                .map(task2Mapper::toDto)
//                .forEach(System.out::println);
//    }
//}