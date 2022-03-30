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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomRest {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping("/rooms")
    public List<RoomDto> allRooms(){
        return roomService.findAll().stream().map(roomMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<RoomDto> oneRoom(@PathVariable("id") UUID uuid){
        return new ResponseEntity<>(roomMapper.toDto(roomService.findById(uuid)), HttpStatus.OK);
    }

    @PostMapping("/room")
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto roomDto) {
        Room room = roomMapper.toEntity(roomDto);
        roomService.save(room);
        return new ResponseEntity<>(roomMapper.toDto(room), HttpStatus.CREATED);
    }

    @PutMapping("/room")
    public ResponseEntity<RoomDto> update(@RequestBody RoomDto roomDto) {
        Room room = roomMapper.toEntity(roomDto);
        roomService.save(room);
        return new ResponseEntity<>(roomMapper.toDto(room), HttpStatus.OK);
    }

    @DeleteMapping("room/{id}")
    public void delete(@PathVariable("id") UUID id) {
        roomService.deleteById(id);
    }
}
