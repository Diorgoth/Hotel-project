package com.example.demo.Controller;


import com.example.demo.Entity.Hotel;
import com.example.demo.Entity.Room;
import com.example.demo.Payload.HotelDto;
import com.example.demo.Payload.RoomDto;
import com.example.demo.Repository.HotelRepository;
import com.example.demo.Repository.RoomRepository;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    final HotelRepository hotelRepository;

    final RoomRepository roomRepository;

    public RoomController(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }


    @GetMapping()
    public List<Room> getRooms(){

        List<Room> roomList = roomRepository.findAll();

        return roomList;

    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Integer id){

        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            return room;
        }else {
            return new Room();
        }

    }

    @GetMapping("/byhotel/{id}")
    public Page<Room> getRoomslist(@PathVariable Integer id,@RequestParam int page,@RequestParam Integer size){

        Pageable pageable = (Pageable) PageRequest.of(page, size);

        Page<Room> roomPage = roomRepository.findAllByHotelId(id, pageable);
        return roomPage;
    }

    @PostMapping("/post")
    public String addRoom(@RequestBody RoomDto roomDto){

         Room room = new Room();

         room.setNumber(roomDto.getNumber());

         room.setFloor(roomDto.getFloor());

         room.setSize(roomDto.getSize());


        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotel_id());

        if (!optionalHotel.isPresent()){

               return "Hotel not found";

           }

           room.setHotel(optionalHotel.get());
        roomRepository.save(room);

        return "Successfully added";


    }


    @PutMapping("/put/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){


        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (!optionalRoom.isPresent()){

            return "Room not found";

        }


        Room room = optionalRoom.get();

        room.setFloor(roomDto.getFloor());

        room.setSize(roomDto.getSize());

        room.setNumber(roomDto.getNumber());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotel_id());

        if (!optionalHotel.isPresent()){

            return "Hotel not found";
        }

        room.setHotel(optionalHotel.get());


        roomRepository.save(room);

        return "Successfully edited";

    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Integer id){

       roomRepository.deleteById(id);

        return "Room deleted";

    }


}
