package mapper;

import dao.BookingDAO;
import entity.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BookingMapper {
    private BookingDAO bookingDAO;

    public BookingMapper(BookingDAO bookingDAO) throws SQLException {
        this.bookingDAO = bookingDAO;
    }

    public List<Booking> findAll() throws SQLException {
        List<Booking> bookings = new LinkedList<>();
        ResultSet resultSet = bookingDAO.findAll();

        while (resultSet.next()) {
            Booking booking = new Booking();

            booking.setIdBooking(resultSet.getInt("idBooking"));
            booking.setIdUser(resultSet.getInt("idUser"));
            booking.setIdFlight(resultSet.getInt("idFlight"));

            bookings.add(booking);
        }

        resultSet.close();

        return bookings;
    }

    public Booking insert(Booking booking) throws SQLException {
        Integer idBooking = bookingDAO.insert(booking.getIdUser(), booking.getIdFlight());

        booking.setIdBooking(idBooking);

        return booking;
    }

    public void remove(Booking booking) throws SQLException {
        bookingDAO.remove(booking.getIdBooking());
    }

    public List<Integer> findAllBookingForAnUser(Integer idUser) throws SQLException {
        List<Integer> flights = new LinkedList<>();
        ResultSet resultSet = bookingDAO.findAll();

        while (resultSet.next()) {
            Booking booking = new Booking();

            booking.setIdBooking(resultSet.getInt("idBooking"));
            booking.setIdUser(resultSet.getInt("idUser"));
            booking.setIdFlight(resultSet.getInt("idFlight"));

            if(booking.getIdUser().equals(idUser))
                flights.add(booking.getIdFlight());
        }

        resultSet.close();

        return flights;
    }

    public Booking getBooking(Integer idUser, Integer idFlight) throws SQLException {
        List<Booking> bookings = findAll();
        for (Booking booking:
             bookings) {
            if(booking.getIdUser().equals(idUser) && booking.getIdFlight().equals(idFlight))
                return booking;
        }
        return null;
    }

    public ObservableList<Booking> populateBooking() throws SQLException {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        ResultSet resultSet = bookingDAO.findAll();

        while (resultSet.next()) {
            Booking booking = new Booking();

            booking.setIdBooking(resultSet.getInt("idBooking"));
            booking.setIdUser(resultSet.getInt("idUser"));
            booking.setIdFlight(resultSet.getInt("idFlight"));

            bookings.add(booking);
        }

        resultSet.close();

        return bookings;
    }

    public HashMap<String, Integer> report() throws SQLException {
        HashMap<String, Integer> hashMap = new HashMap<>();
        ResultSet resultSet = bookingDAO.report();

        while (resultSet.next())
            hashMap.put(resultSet.getString("company"), resultSet.getInt("count(idBooking)"));

        resultSet.close();

        return hashMap;
    }
}
