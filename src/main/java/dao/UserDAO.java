package dao;

import java.sql.*;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public ResultSet findAll() throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
        return statement.executeQuery();
    }

    public Integer insert (String name, String username, String password,
                           String address, String phoneNumber, String userRole,
                           Double balanceAccount) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user (name, username, password, address, phoneNumber, userRole, balanceAccount) VALUES (?, ?, ?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS );
        statement.setString(1, name);
        statement.setString(2, username);
        statement.setString(3, password);
        statement.setString(4, address);
        statement.setString(5, phoneNumber);
        statement.setString(6, userRole);
        statement.setDouble (7, balanceAccount);

        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } finally {
            statement.close();
        }
    }

    public void update (Integer idUser, String name, String username, String password,
                           String address, String phoneNumber, String userRole,
                           Double balanceAccount) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE user SET name = ?, username = ?, password = ?, address = ?, phoneNumber = ?, userRole = ?, balanceAccount = ? WHERE idUser = ?;");
        statement.setString(1, name);
        statement.setString(2, username);
        statement.setString(3, password);
        statement.setString(4, address);
        statement.setString(5, phoneNumber);
        statement.setString(6, userRole);
        statement.setDouble (7, balanceAccount);
        statement.setInt (8, idUser);

        statement.executeUpdate();
    }

    public void remove(Integer idUser) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE idUser = ?");
        statement.setInt(1, idUser);

        statement.executeUpdate();
        statement.close();
    }
}
