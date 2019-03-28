package dao;

import java.sql.*;

public class BookingDAO {

    private final Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    public ResultSet findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM booking");
        return statement.executeQuery();
    }

    public Integer insert (Integer userId, Integer flightId) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO booking (idUser, idFlight) VALUES (?, ?);",
                Statement.RETURN_GENERATED_KEYS );
        statement.setInt(1, userId);
        statement.setInt(2, flightId);

        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating booking failed, no ID obtained.");
            }
        } finally {
            statement.close();
        }
    }

    public void remove(Integer idBooking) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM booking WHERE idBooking = ?");
        statement.setInt(1, idBooking);

        statement.executeUpdate();
        statement.close();
    }

    public ResultSet report() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT company, COUNT(idBooking) FROM booking JOIN flight WHERE flight.idFlight = booking.idFlight GROUP BY flight.company;");
        return statement.executeQuery();
    }
}
