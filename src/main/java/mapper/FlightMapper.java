package mapper;

import dao.FlightDAO;
import entity.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FlightMapper {

    private FlightDAO flightDAO;

    public FlightMapper(FlightDAO flightDAO) throws SQLException {
        this.flightDAO = flightDAO;
    }

    public List<Flight> findAll() throws SQLException {
        List<Flight> flights = new LinkedList<>();
        ResultSet resultSet = flightDAO.findAll();

        while (resultSet.next()) {
            Flight flight = new Flight();

            flight.setIdFlight(resultSet.getInt("idFlight"));
            flight.setOrigin(resultSet.getString("origin"));
            flight.setDestination(resultSet.getString("destination"));
            flight.setFlightTime(resultSet.getInt("flightTime"));
            flight.setCompany(resultSet.getString("company"));
            flight.setPrice(resultSet.getDouble("price"));
            flight.setDeparture(resultSet.getDate("departure"));
            flight.setFreeSeats(resultSet.getInt("freeSeats"));

            flights.add(flight);
        }

        resultSet.close();

        return flights;
    }

    public Flight insert(Flight flight) throws SQLException {
        Integer idFlight = flightDAO.insert(flight.getOrigin(), flight.getDestination(), flight.getFlightTime(), flight.getCompany(),
                flight.getPrice(), flight.getDeparture(), flight.getFreeSeats());

        flight.setIdFlight(idFlight);

        return flight;
    }

    public void update(Flight flight) throws SQLException {
        flightDAO.update(flight.getIdFlight(), flight.getOrigin(), flight.getDestination(), flight.getFlightTime(), flight.getCompany(), flight.getPrice(), flight.getDeparture(), flight.getFreeSeats());
    }

    public void remove(Flight flight) throws SQLException {
        flightDAO.remove(flight.getIdFlight());
    }

    public ObservableList<Flight> populateFlightTable() throws SQLException {
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        ResultSet resultSet = flightDAO.findAll();

        while (resultSet.next()) {
            Flight flight = new Flight();

            flight.setIdFlight(resultSet.getInt("idFlight"));
            flight.setOrigin(resultSet.getString("origin"));
            flight.setDestination(resultSet.getString("destination"));
            flight.setFlightTime(resultSet.getInt("flightTime"));
            flight.setCompany(resultSet.getString("company"));
            flight.setPrice(resultSet.getDouble("price"));
            flight.setDeparture(resultSet.getDate("departure"));
            flight.setFreeSeats(resultSet.getInt("freeSeats"));

            flights.add(flight);
        }

        resultSet.close();

        return flights;
    }


    public Flight getFlight(Integer idFlight) throws SQLException {
        List<Flight> flights = findAll();
        for (Flight flight:
             flights) {
            if(flight.getIdFlight().equals(idFlight))
                return flight;
        }
        return null;
    }

    public ObservableList<Flight> filterFlights(String origin, String destination) throws SQLException {
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        ResultSet resultSet = flightDAO.filterFlights(origin, destination);

        while (resultSet.next()) {
            Flight flight = new Flight();

            flight.setIdFlight(resultSet.getInt("idFlight"));
            flight.setOrigin(resultSet.getString("origin"));
            flight.setDestination(resultSet.getString("destination"));
            flight.setFlightTime(resultSet.getInt("flightTime"));
            flight.setCompany(resultSet.getString("company"));
            flight.setPrice(resultSet.getDouble("price"));
            flight.setDeparture(resultSet.getDate("departure"));
            flight.setFreeSeats(resultSet.getInt("freeSeats"));

            flights.add(flight);
        }

        resultSet.close();

        return flights;
    }
}
