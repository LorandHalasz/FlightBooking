import entity.Booking;
import entity.Flight;
import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mapper.BookingMapper;
import mapper.FlightMapper;
import mapper.UserMapper;

import java.sql.Date;
import java.sql.SQLException;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

        /*Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment", "root", "root");

        UserDAO userDAO = new UserDAO(connection);
        UserMapper userMapper = new UserMapper(userDAO);

        FlightDAO flightDAO = new FlightDAO(connection);
        FlightMapper flightMapper = new FlightMapper(flightDAO);

        BookingDAO bookingDAO = new BookingDAO(connection);
        BookingMapper bookingMapper = new BookingMapper(bookingDAO);

        userDemo(userMapper);
        flightDemo(flightMapper);
        bookingDemo(bookingMapper);

        */
        launch(args);
    }


    private static void userDemo(UserMapper userMapper) throws SQLException {

        System.out.println("--- Initial findAll ---");

        for (User user : userMapper.findAll()) {
            System.out.println(String.format("User %s has username %s", user.getName(), user.getUsername()));
        }

        System.out.println("--- After insert ---");

        userMapper.insert(new User(null, "Admin", "admin", "a", "Str. Admin", "0700000000", "admin", 500.00));
        userMapper.insert(new User(null, "User", "user", "u", "Str. User", "0777777777", "user", 2500.00));
        userMapper.insert(new User(null, "Andra", "andra", "ardna.andra5*", "Str. Fabricii", "0742847283", "user", 700.00));
        userMapper.insert(new User(null, "Mircea", "mircea", "Mir.c3a10", "Str. Fericirii", "0724512451", "user", 330.00));

        User insertedUser = userMapper.insert(new User(null, "Sergiu", "SergiuSrg", "Sergiu2.", "Str. Dumbrava", "0732424242", "user", 550.00));
        for (User user : userMapper.findAll()) {
            System.out.println(String.format("User %s has username %s", user.getName(), user.getUsername()));
        }

        System.out.println("--- After remove ---");

     /*   userMapper.remove(insertedUser);
        for (User user : userMapper.findAll()) {
            System.out.println(String.format("User %s has email %s", user.getName(), user.getUsername()));
        }*/
    }

    private static void flightDemo(FlightMapper flightMapper) throws SQLException {

        System.out.println("--- Initial findAll ---");

        for (Flight flight : flightMapper.findAll()) {
            System.out.println(String.format("Flight from %s goes at %s", flight.getOrigin(), flight.getDestination()));
        }

        System.out.println("--- After insert ---");

        flightMapper.insert(new Flight(null, "Cluj", "Madrid", 230, "Wizz Air", 123.48, Date.valueOf("2019-05-24"),48));
        flightMapper.insert(new Flight(null, "Viena", "Paris", 120, "Fly Emirates", 133.98, Date.valueOf("2019-05-25"),2));
        flightMapper.insert(new Flight(null, "Cluj", "Israel", 570, "Turkish Airline", 319.83, Date.valueOf("2017-03-29"),5));
        flightMapper.insert(new Flight(null, "Dubai", "Washington", 875, "Fly Emirates", 1201.41, Date.valueOf("2019-06-07"),20));
        flightMapper.insert(new Flight(null, "Roma", "Cluj", 130, "Wizz Air", 70.14, Date.valueOf("2019-04-01"),0));
        flightMapper.insert(new Flight(null, "Budapesta", "Munchen", 75, "Wizz Air", 119.49, Date.valueOf("2018-03-24"),0));

        Flight insertedFlight = flightMapper.insert(new Flight(null, "Cluj", "Londra", 185, "Wizz Air", 49.98, Date.valueOf("2015-10-20"),124));
        for (Flight flight : flightMapper.findAll()) {
            System.out.println(String.format("Flight from %s goes at %s", flight.getOrigin(), flight.getDestination()));
        }

        /*System.out.println("--- After remove ---");

        flightMapper.remove(insertedFlight);
        for (Flight flight : flightMapper.findAll()) {
            System.out.println(String.format("Flight from %s goes at %s", flight.getOrigin(), flight.getDestination()));
        }*/
    }

    private static void bookingDemo(BookingMapper bookingMapper) throws SQLException {

        System.out.println("--- Initial findAll ---");

        for (Booking booking : bookingMapper.findAll()) {
            System.out.println(String.format("User%d's booking %d with flight %d", booking.getIdUser(), booking.getIdBooking(),booking.getIdFlight()));
        }

        System.out.println("--- After insert ---");

        bookingMapper.insert(new Booking(null, 2, 1));
        bookingMapper.insert(new Booking(null, 2, 2));
        bookingMapper.insert(new Booking(null, 2, 3));
        bookingMapper.insert(new Booking(null, 2, 4));
        bookingMapper.insert(new Booking(null, 2, 5));
        bookingMapper.insert(new Booking(null, 2, 6));
        bookingMapper.insert(new Booking(null, 2, 7));
        bookingMapper.insert(new Booking(null, 3, 3));
        bookingMapper.insert(new Booking(null, 4, 7));
        bookingMapper.insert(new Booking(null, 5, 1));
        bookingMapper.insert(new Booking(null, 5, 2));
        bookingMapper.insert(new Booking(null, 3, 6));
        bookingMapper.insert(new Booking(null, 4, 2));
        bookingMapper.insert(new Booking(null, 3, 4));

       /* Booking insertedBooking = bookingMapper.insert(new Booking(null, 1, 1));
        for (Booking booking : bookingMapper.findAll()) {
            System.out.println(String.format("User%d's booking %d with flight %d", booking.getIdUser(), booking.getIdBooking(),booking.getIdFlight()));
        }

        System.out.println("--- After remove ---");

        bookingMapper.remove(insertedBooking);
        for (Booking booking : bookingMapper.findAll()) {
            System.out.println(String.format("User%d's booking %d with flight %d", booking.getIdUser(), booking.getIdBooking(),booking.getIdFlight()));
        }*/
    }
}
