package com.example.diplom.services;

import com.example.diplom.entities.Room;
import com.example.diplom.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public void save(Room room){
        roomRepository.save(room);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
