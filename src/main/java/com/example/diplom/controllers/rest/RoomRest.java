package com.example.diplom.controllers.rest;

import com.example.diplom.entities.Room;
import com.example.diplom.entities.dto.RoomDto;
import com.example.diplom.services.RoomService;
import com.example.diplom.services.mappers.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomRest {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping("/rooms")
    public List<Room> allRooms(){
        return roomService.findAll();
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<RoomDto> oneRoom(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>(roomMapper.toDto(roomService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/room")
    public ResponseEntity<Room> create(@RequestBody RoomDto roomDto) {
        Room room = roomMapper.toEntity(roomDto);
        roomService.save(room);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @PutMapping("/room/{id}")
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Room room) {
        roomService.save(room);
    }

    @DeleteMapping("room/{id}")
    public void delete(@PathVariable("id") UUID id) {
        roomService.deleteById(id);
    }
}
