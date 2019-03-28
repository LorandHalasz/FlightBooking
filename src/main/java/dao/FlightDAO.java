package dao;

import java.sql.*;

public class FlightDAO {

    private final Connection connection;

    public FlightDAO(Connection connection) {
        this.connection = connection;
    }

    public ResultSet findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM flight");
        return statement.executeQuery();
    }

    public Integer insert (String origin, String destination, Integer flightTime,
                           String company, Double price, Date departure,
                           Integer freeSeats) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO flight (origin, destination, flightTime, company, price, departure, freeSeats) VALUES (?, ?, ?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS );
        statement.setString(1, origin);
        statement.setString(2, destination);
        statement.setInt(3, flightTime);
        statement.setString(4, company);
        statement.setDouble(5, price);
        statement.setDate(6, departure);
        statement.setInt(7, freeSeats);

        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating flight failed, no ID obtained.");
            }
        } finally {
            statement.close();
        }
    }

    public void update(Integer idFlight, String origin, String destination, Integer flightTime,
                       String company, Double price, Date departure,
                       Integer freeSeats) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE flight SET origin = ?, destination = ?, flightTime = ?, company = ?, price = ?, departure = ?, freeSeats = ? WHERE idFlight = ?;");
        statement.setString(1, origin);
        statement.setString(2, destination);
        statement.setInt(3, flightTime);
        statement.setString(4, company);
        statement.setDouble(5, price);
        statement.setDate(6, departure);
        statement.setInt(7, freeSeats);
        statement.setInt(8, idFlight);

        statement.executeUpdate();
    }

    public void remove(Integer idFlight) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM flight WHERE idFlight = ?");
        statement.setInt(1, idFlight);

        statement.executeUpdate();
        statement.close();
    }

    public ResultSet filterFlights(String origin, String destination) throws SQLException {
        String statementBuild = "";
        if (!origin.isEmpty() && !destination.isEmpty())
            statementBuild = "SELECT * FROM flight where origin = ? and destination = ?";
        else
            if (!origin.isEmpty())
                statementBuild = "SELECT * FROM flight where origin = ?";
            else
                if (!destination.isEmpty())
                    statementBuild = "SELECT * FROM flight where destination = ?";
                else
                    statementBuild = "SELECT * FROM flight";


        PreparedStatement statement = connection.prepareStatement(statementBuild);

        if (!origin.isEmpty() && !destination.isEmpty()) {
            statement.setString(1, origin);
            statement.setString(2, destination);
        }
        else
            if(!origin.isEmpty())
                statement.setString(1, origin);
            else
                if (!destination.isEmpty())
                    statement.setString(1, destination);

        return statement.executeQuery();
    }
}
