package mapper;

import dao.UserDAO;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UserMapper {

    private UserDAO userDAO;

    public UserMapper(UserDAO userDAO) throws SQLException{
        this.userDAO = userDAO;
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new LinkedList<>();
        ResultSet resultSet = userDAO.findAll();

        while (resultSet.next()) {
            User user = new User();

            user.setIdUser(resultSet.getInt("idUser"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAddress(resultSet.getString("address"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            user.setUserRole(resultSet.getString("userRole"));
            user.setBalanceAccount(resultSet.getDouble("balanceAccount"));

            users.add(user);
        }

        resultSet.close();

        return users;
    }

    public User insert(User user) throws SQLException {
        Integer idUser = userDAO.insert(user.getName(), user.getUsername(), user.getPassword(), user.getAddress(),
                user.getPhoneNumber(), user.getUserRole(), user.getBalanceAccount());

        user.setIdUser(idUser);

        return user;
    }

    public void update(User user) throws SQLException {
        userDAO.update(user.getIdUser(), user.getName(), user.getUsername(), user.getPassword(), user.getAddress(), user.getPhoneNumber(), user.getUserRole(), user.getBalanceAccount());
    }

    public void remove(User user) throws SQLException {
        userDAO.remove(user.getIdUser());
    }

    public Boolean checkUserAndPass(String username, String password) throws SQLException {
        List<User> users;
        users = this.findAll();
        Iterator<User> it = users.iterator();
        while(it.hasNext()){
            User user = it.next();
            if(user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public User getUser(String username) throws SQLException {
        List<User> users;
        User user = new User();

        users = this.findAll();
        Iterator<User> it = users.iterator();
        while(it.hasNext()){
            User userIterator = it.next();
            if(userIterator.getUsername().equals(username))
                user = userIterator;
        }
        return user;
    }

    public ObservableList<User> populateUserTable() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        ResultSet resultSet = userDAO.findAll();

        while (resultSet.next()) {
            User user = new User();

            user.setIdUser(resultSet.getInt("idUser"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAddress(resultSet.getString("address"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            user.setUserRole(resultSet.getString("userRole"));
            user.setBalanceAccount(resultSet.getDouble("balanceAccount"));

            users.add(user);
        }

        resultSet.close();

        return users;
    }
}
