package com.example.demo.Controllers;

import java.util.Map;
import java.io.IOException;
import com.example.demo.Dto.VenuePictures;
import com.example.demo.Services.FOURQBService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FOURQBController {

    @Autowired
    FOURQBService fourqbService;

    @CrossOrigin
    @RequestMapping(value = "getvenues/{venueplace}/{venuetype}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getData(@PathVariable("venueplace")String venuePlace, @PathVariable("venuetype")String venueType)
            throws IOException, ParseException {
        return new ResponseEntity<>(fourqbService.getVenues(venuePlace, venueType), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "getpic/{veneueid}", method = RequestMethod.GET)
    public ResponseEntity<VenuePictures> getPictures(@PathVariable("veneueid")String venueId) throws IOException, ParseException {
        return new ResponseEntity<>(fourqbService.getPictures(venueId), HttpStatus.OK);
    }
}