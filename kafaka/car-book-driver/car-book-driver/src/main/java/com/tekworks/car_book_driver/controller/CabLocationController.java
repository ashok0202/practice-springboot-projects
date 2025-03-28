package com.tekworks.car_book_driver.controller;


import com.tekworks.car_book_driver.service.CabLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/location")
public class CabLocationController {

    @Autowired
    private CabLocationService cabLocationService;


    @PostMapping
    public ResponseEntity<?> getLocation() throws InterruptedException {

        int range=100;
        while (range>0) {
            //System.out.println(Math.random()+" , "+Math.random());
            cabLocationService.updateLocation(Math.random()+" , "+Math.random());
            Thread.sleep(1000);
            range--;
        }
        return new ResponseEntity<>(Map.of("message",   "LocationUpdated"), HttpStatus.OK);
    }

}
