package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.ApiUris;
import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;
import cz.muni.fi.pa165.bluebat.facade.ReservationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Rudolf Madoran
 * @since : 22. 5. 2021, Sat
 **/

@RestController
@RequestMapping("/main")
public class ReservationController {

    @Autowired
    private ReservationFacade reservationFacade;

    @RequestMapping(value = ApiUris.ROOT_URI_RESERVATIONS+"/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createReservation(@RequestBody ReservationCreateDTO reservationCreateDTO){
        reservationFacade.createReservation(reservationCreateDTO);
    }

    @RequestMapping(value = ApiUris.ROOT_URI_RESERVATIONS+"/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationDTO getTripByID(@PathVariable("id") long id){

        return reservationFacade.getReservationById(id);
    }

    @RequestMapping(value = ApiUris.ROOT_URI_RESERVATIONS+"/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int deleteReservation(@PathVariable("id") long id){
        reservationFacade.deleteReservation(id);
        return 200;
    }
    /*
    @RequestMapping(value = ApiUris.ROOT_URI_RESERVATIONS, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateReservation(@RequestBody ReservationDTO trip){
        reservationFacade.updateReservation(trip);
    }
    */
}
