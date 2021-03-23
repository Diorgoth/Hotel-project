package com.example.demo.Repository;

import com.example.demo.Entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;


public interface RoomRepository extends JpaRepository<Room,Integer> {
    Page<Room> findAllByHotelId(Integer hotelId, Pageable pageable);
}
