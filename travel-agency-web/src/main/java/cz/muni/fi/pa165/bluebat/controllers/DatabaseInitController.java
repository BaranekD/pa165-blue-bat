package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.ApiUris;
import cz.muni.fi.pa165.bluebat.dto.*;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.facade.ExcursionFacade;
import cz.muni.fi.pa165.bluebat.facade.TripFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Rudolf Madoran
 * @since : 22. 5. 2021, Sat
 **/

@RestController
@RequestMapping("/main")
public class DatabaseInitController {

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private ExcursionFacade excursionFacade;

    @Autowired
    private TripFacade tripFacade;

    @RequestMapping(value = "/database", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String populateData(){
        populate();
        return "Database was populated";
    }

    private static CustomerCreateDTO getDefaultCustomerCreateDTO() {
        CustomerCreateDTO result = new CustomerCreateDTO();

        result.setName("name");
        result.setSurname("surname");
        result.setBirthday(LocalDate.ofYearDay(1970, 1));
        result.setEmail("a@b.c");
        result.setAddress("address");
        result.setPhoneNumber(111111111L);

        return result;
    }

    private static TripCreateDTO getTrip(){
        TripCreateDTO testTrip3 = new TripCreateDTO();
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");
        List<PriceCreateDTO> prices = new ArrayList<>();
        PriceCreateDTO price = new PriceCreateDTO();
        price.setValidFrom(LocalDate.of(2022,5,15));
        price.setAmount(new BigDecimal(15L));
        PriceCreateDTO price2 = new PriceCreateDTO();
        price2.setValidFrom(LocalDate.of(2023,5,15));
        price2.setAmount(new BigDecimal(30L));
        prices.add(price);
        prices.add(price2);
        testTrip3.setPrices(prices);

        return testTrip3;

    }
    private static ExcursionCreateDTO getExcursion(long id){
        ExcursionCreateDTO result = new ExcursionCreateDTO();
        result.setName("Test excursion");
        result.setDateFrom(LocalDate.of(2022, 4, 25));
        result.setDuration(Duration.ofHours(4));
        result.setDestination("Madrid");
        result.setDescription("climbing");
        result.setParentId(id);

        List<PriceCreateDTO> prices3 = new ArrayList<>();
        PriceCreateDTO price3 = new PriceCreateDTO();
        price3.setValidFrom(LocalDate.of(2023,5,15));
        price3.setAmount(new BigDecimal(30L));
        prices3.add(price3);

        result.setPrices(prices3);
        return result;

    }

    public int populate(){
        TripDTO tripDTO = tripFacade.createTrip(getTrip());
        customerFacade.createCustomer(getDefaultCustomerCreateDTO());
        excursionFacade.createExcursion(getExcursion(tripDTO.getId()));

        return 200;

    }
}
