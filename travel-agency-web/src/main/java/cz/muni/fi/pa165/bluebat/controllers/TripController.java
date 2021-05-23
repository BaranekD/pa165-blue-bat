package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.ApiUris;
import cz.muni.fi.pa165.bluebat.dto.*;

import cz.muni.fi.pa165.bluebat.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Rudolf Madoran
 * @since : 21. 5. 2021, Fri
 **/

@RestController
@RequestMapping(ApiUris.ROOT_URI_TRIPS)
public class TripController {

    @Autowired
    private TripFacade tripFacade;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TripShowDTO> getAllTrips(){

        return tripFacade.getAllTrips();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TripDTO getTripByID(@PathVariable("id") long id){

        return tripFacade.getTripDTO(id);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TripShowDTO getTripShowByID(@PathVariable("id") long id){

        return tripFacade.getTripShowDTO(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createTrip(@RequestBody TripCreateDTO trip){
        tripFacade.createTrip(trip);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTripByID(@PathVariable("id") long id){
        tripFacade.deleteTrip(id);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateTripByID(@RequestBody TripDTO trip){
        tripFacade.updateTrip(trip);
    }


}
