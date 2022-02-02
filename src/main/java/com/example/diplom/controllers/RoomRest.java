package com.example.diplom.controllers;

import com.example.diplom.entities.Room;
import com.example.diplom.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomRest {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public List<Room> allRooms(){
        return roomService.findAll();
    }

    @GetMapping("/room/{id}")
    public Room oneRoom(@PathVariable("id") UUID uuid){
        return roomService.findById(uuid);
    }

    @PostMapping("/room")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Room room) {
        roomService.save(room);
    }

    @PutMapping("/room/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) UUID id, @RequestBody Room room) {
        roomService.save(room);
    }

    @DeleteMapping("room/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        roomService.deleteById(id);
    }
}
