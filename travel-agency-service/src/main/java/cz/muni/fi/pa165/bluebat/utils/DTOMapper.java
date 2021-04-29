package cz.muni.fi.pa165.bluebat.utils;

import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;

import java.util.stream.Collectors;

public class DTOMapper {

//    public static Trip mapTripCreateDTOToTrip(TripCreateDTO tripCreateDTO) {
//        Trip result = new Trip();
//
//        result.setName(tripCreateDTO.getName());
//        result.setDateFrom(tripCreateDTO.getDateFrom());
//        result.setDateTo(tripCreateDTO.getDateTo());
//        result.setDestination(tripCreateDTO.getDestination());
//        result.setDestination(tripCreateDTO.getDestination());
//        result.setAvailableTrips(tripCreateDTO.getAvailableTrips());
//
//        return result;
//    }
//
//    public static Trip mapTripDTOToTrip(TripDTO tripDTO) {
//        Trip result = new Trip();
//
//        result.setId(tripDTO.getId());
//        result.setName(tripDTO.getName());
//        result.setDateFrom(tripDTO.getDateFrom());
//        result.setDateTo(tripDTO.getDateTo());
//        result.setDestination(tripDTO.getDestination());
//        result.setDestination(tripDTO.getDestination());
//        result.setAvailableTrips(tripDTO.getAvailableTrips());
//
//        return result;
//    }
//
//    private static Excursion mapExcursionCreateDTOToExcursion(ExcursionCreateDTO excursionCreateDTO) {
//        Excursion result = new Excursion();
//
//        result.setName(excursionCreateDTO.getName());
//        result.setDateFrom(excursionCreateDTO.getDateFrom());
//        result.setDuration(excursionCreateDTO.getDuration());
//        result.setDestination(excursionCreateDTO.getDestination());
//        result.setDescription(excursionCreateDTO.getDescription());
//        result.setPrices(excursionCreateDTO.getPrices().stream().map(this::getPrice).collect(Collectors.toList()));
//
//        return result;
//    }
//
//    private static Excursion mapExcursionDTOToExcursion(ExcursionDTO excursionDTO) {
//        Excursion result = new Excursion();
//
//        result.setId(excursionDTO.getId());
//        result.setName(excursionDTO.getName());
//        result.setDateFrom(excursionDTO.getDateFrom());
//        result.setDuration(excursionDTO.getDuration());
//        result.setDestination(excursionDTO.getDestination());
//        result.setDescription(excursionDTO.getDescription());
//        result.setPrices(excursionDTO.getPrices().stream().map(this::getPrice).collect(Collectors.toList()));
//
//        return result;
//    }
//
//    private static Customer mapCustomerCreateDTOToCustomer(CustomerCreateDTO customerCreateDTO) {
//        Customer result = new Customer();
//
//
//
//        return result;
//    }


}
