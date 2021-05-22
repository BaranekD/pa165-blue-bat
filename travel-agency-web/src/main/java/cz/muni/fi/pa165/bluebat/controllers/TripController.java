package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.ApiUris;
import cz.muni.fi.pa165.bluebat.dto.*;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.facade.ExcursionFacade;
import cz.muni.fi.pa165.bluebat.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author : Rudolf Madoran
 * @since : 21. 5. 2021, Fri
 **/

@RestController
@RequestMapping("/main")
public class TripController {

    @Autowired
    private TripFacade tripFacade;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,value = ApiUris.ROOT_URI_TRIPS)
    public List<TripShowDTO> getAllTrips(){

        return tripFacade.getAllTrips();
    }
    @RequestMapping(value = ApiUris.ROOT_URI_TRIPS+"/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TripDTO getTripByID(@PathVariable("id") long id){

        return tripFacade.getTripDTO(id);
    }
    /*
    @RequestMapping(value = ApiUris.ROOT_URI_TRIPS+"/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createTrip(@RequestBody TripCreateDTO trip){
        tripFacade.createTrip(trip);
    }
    */
    @RequestMapping(value = ApiUris.ROOT_URI_TRIPS+"/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTripByID(@PathVariable("id") long id){
        tripFacade.deleteTrip(id);
    }
    /*
    @RequestMapping(value = ApiUris.ROOT_URI_TRIPS, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTripByID(@RequestBody TripDTO trip){
        tripFacade.updateTrip(trip);
    }
    */

}
