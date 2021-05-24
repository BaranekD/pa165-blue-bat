package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.ApiUris;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.facade.ReservationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Rudolf Madoran
 * @since : 22. 5. 2021, Sat
 **/

@RestController
@RequestMapping(ApiUris.ROOT_URI_RESERVATIONS)
public class ReservationController {

    @Autowired
    private ReservationFacade reservationFacade;

    @RequestMapping(value ="/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void createReservation(@RequestBody ReservationCreateDTO reservationCreateDTO, @RequestAttribute("authenticatedUser") CustomerDTO customerDTO){
        reservationCreateDTO.setCustomerId(customerDTO.getId());
        reservationFacade.createReservation(reservationCreateDTO);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationDTO getReservationByID(@PathVariable("id") long id){
        return reservationFacade.getReservationById(id);
    }
}
