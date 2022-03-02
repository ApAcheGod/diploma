//package com.example.diplom.services.mappers;
//
//import com.example.diplom.entities.Group;
//import com.example.diplom.entities.dto.GroupDto;
//import com.example.diplom.entities.dto.RoomDto;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Set;
//import java.util.UUID;
//
//@SpringBootTest
//@RequiredArgsConstructor
//class GroupMapperTest {
//
//    @Autowired
//    private GroupMapper groupMapper;
//
//    @Test
//    public void toEntity(){
//        GroupDto groupDto = GroupDto
//                .builder()
//                .id(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"))
//                .name("эма")
//                .rooms(Set.of(RoomDto.builder().name("heev").id(UUID.fromString("86bac443-c896-1233-b45e-bf7956d68a19")).build()))
//                .build();
//
//        System.out.println(groupMapper.toEntity(groupDto));
////        Assertions.assertEquals(groupDto.getRooms(), groupMapper.toEntity(groupDto).getRooms());
//    }
//
//    @Test
//    public void toDto(){
//        Group group = new Group();
//        group.setId(UUID.fromString("86bac443-c896-4cf9-b45e-bf7956d68a19"));
//
//        group.setName(";vf");
//        System.out.println(groupMapper.toDto(group));
//    }
//
//}