package com.example.demo.Controller;

import com.example.demo.Entity.Hotel;
import com.example.demo.Payload.HotelDto;
import com.example.demo.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping()
    public List <Hotel> getHotels(){

        List<Hotel> hotelList = hotelRepository.findAll();

        return hotelList;


    }
    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable Integer id){

        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isPresent()){
           Hotel hotel = optionalHotel.get();;
           return hotel;
        }else {
            return new Hotel();
        }

    }

    @PostMapping("/post")
    public String addHotel(@RequestBody HotelDto hoteldto){

     Hotel hotel = new Hotel();

     hotel.setName(hoteldto.getName());

     hotelRepository.save(hotel);

     return "Hotel added";

    }

    @PutMapping("/put/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody HotelDto hoteldto){

        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isPresent()){


            Hotel hotel = new Hotel();

            hotel.setName(hoteldto.getName());

            hotelRepository.save(hotel);

            return "Hotel edited";

        }

        return "Hotel not found";


    }

    @DeleteMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Integer id){

        hotelRepository.deleteById(id);

        return "Hotel deleted";

    }



}
