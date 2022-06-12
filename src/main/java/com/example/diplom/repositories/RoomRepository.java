package com.example.diplom.repositories;

import com.example.diplom.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    Room findRoomById(UUID uuid);

    @Query("select r from Room r order by r.name")
    @Override
    List<Room> findAll();
}
