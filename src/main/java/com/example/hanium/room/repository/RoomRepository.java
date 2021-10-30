package com.example.hanium.room.repository;

import com.example.hanium.Auth.model.User;
import com.example.hanium.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomId(Long RoomId);
}