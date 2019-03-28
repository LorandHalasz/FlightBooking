import dao.BookingDAO;
import dao.FlightDAO;
import dao.UserDAO;
import entity.Booking;
import entity.Flight;
import entity.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import mapper.BookingMapper;
import mapper.FlightMapper;
import mapper.UserMapper;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class Controller implements Initializable {

    @FXML private TableView<User> tableViewUser;
    @FXML private TableColumn<User, Integer> idUserColumn;
    @FXML private TableColumn<User,String> nameColumn;
    @FXML private TableColumn<User,String> usernameColumn;
    @FXML private TableColumn<User,String> passwordColumn;
    @FXML private TableColumn<User,String> addressColumn;
    @FXML private TableColumn<User,String> phoneNumberColumn;
    @FXML private TableColumn<User,String> userRoleColumn;
    @FXML private TableColumn<User,Double> balanceAccountColumn;

    @FXML private TableView<Flight> tableViewFlight;
    @FXML private TableColumn<Flight, Integer> idFlightColumn;
    @FXML private TableColumn<Flight,String> originColumn;
    @FXML private TableColumn<Flight,String> destinationColumn;
    @FXML private TableColumn<Flight,Integer> flightTimeColumn;
    @FXML private TableColumn<Flight,String> companyColumn;
    @FXML private TableColumn<Flight,Double> priceColumn;
    @FXML private TableColumn<Flight,Date> departureColumn;
    @FXML private TableColumn<Flight,Integer> freeSeatsColumn;

    @FXML private TableView<Flight> tableViewBooking;
    @FXML private TableColumn<Flight,String> originBookingColumn;
    @FXML private TableColumn<Flight,String> destinationBookingColumn;
    @FXML private TableColumn<Flight,Double> priceBookingColumn;
    @FXML private TableColumn<Flight,Date> departureBookingColumn;

    @FXML private TableView<Booking> tableViewBookingAdmin;
    @FXML private TableColumn<Booking, Integer> idBookingColumn;
    @FXML private TableColumn<Booking, Integer> idUserBookingColumn;
    @FXML private TableColumn<Booking, Integer> idFlightBookingColumn;


    private Connection connection;
    private UserDAO userDAO;
    private UserMapper userMapper;
    private static User user = new User();
    private static Flight selectedFlight = new Flight();
    private static User selectedUser = new User();

    private FlightDAO flightDAO;
    private FlightMapper flightMapper;

    private BookingDAO bookingDAO;
    private BookingMapper bookingMapper;

    private Stage stage = new Stage();

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment", "root", "root");
            userDAO = new UserDAO(connection);
            userMapper = new UserMapper(userDAO);

            flightDAO = new FlightDAO(connection);
            flightMapper = new FlightMapper(flightDAO);

            bookingDAO = new BookingDAO(connection);
            bookingMapper = new BookingMapper(bookingDAO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public TextField usernameField;
    public PasswordField passwordField;

    public TextField nameField;
    public PasswordField confirmPasswordField;
    public TextField addressField;
    public TextField phoneNumberField;
    public TextField balanceAccountField;
    public TextField newBalanceAccountField;
    public TextField originFilterField;
    public TextField destinationFilterField;

    public TextField originField;
    public TextField destinationField;
    public TextField flightTimeField;
    public TextField companyField;
    public TextField priceField;
    public TextField departureField;
    public TextField freeSeatsField;

    public Text errorMessageText;
    public Text passwordDoesNotMatchText;
    public Text passwordStrongEnounghText;
    public Text loginErrorMessage;
    public Text currentUsernameText;
    public Text currentSoldText;
    public Text buyOrCancelFlightErrorText;

    public TextArea reportTextArea;

    public ComboBox<String> userRoleComboBox;

    public Button createUserButton;
    public Button updateModeUserButton;
    public Button updateUserButton;
    public Button cancelUpdateUserButton;
    public Button deleteUserButton;

    public Button createFlightButton;
    public Button updateModeFlightButton;
    public Button updateFlightButton;
    public Button cancelUpdateFlightButton;
    public Button deleteFlightButton;

    private Boolean ok = false;

    @FXML
    public void buttonLoginPressed(Event event) throws SQLException, IOException {
        if (userMapper.checkUserAndPass(usernameField.getText(), passwordField.getText())) {
            user = userMapper.getUser(usernameField.getText());
            if(user.getUserRole().equals("user")){
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("userInterface.fxml"));
                stage.setTitle("Welcome, " + user.getUsername() + "!");
                stage.setScene(new Scene(root, 1280, 860));
                stage.show();
            }
            else{
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("adminInterface.fxml"));
                stage.setTitle("Welcome, " + user.getUsername() + "!");
                stage.setScene(new Scene(root, 1280, 860));
                stage.show();
            }
        }
        else
            loginErrorMessage.setText("Username or Password is incorrect!");
    }

    @FXML
    public void buttonRegisterPressed(Event event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage.setTitle("Register!");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    @FXML
    public void passwordConf() {
        if (!passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                passwordDoesNotMatchText.setText("Password doesn't match");
                passwordDoesNotMatchText.setFill(Color.RED);
            } else {
                if (ok) {
                    passwordDoesNotMatchText.setText("Password matches");
                    passwordDoesNotMatchText.setFill(Color.GREEN);
                }
            }
        }
        else
            if(!passwordField.getText().isEmpty()){
                ok = true;
                if (passwordField.getText().length() > 8) {
                    String regex = "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)" +
                            "(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$";
                    if (!Pattern.compile(regex).matcher(passwordField.getText()).matches()) {
                        passwordStrongEnounghText.setText("Password doesn't contain at least 1 uppercase letter 1 lowercase letter 1 number and 1 special char");
                        passwordStrongEnounghText.setFill(Color.RED);
                    }
                    else
                        passwordStrongEnounghText.setText("");
                }
                else {
                    passwordStrongEnounghText.setText("Password isn't strong enough");
                    passwordStrongEnounghText.setFill(Color.RED);
                }
            }
    }

    @FXML
    public void buttonRegisteredPressed(Event event) throws IOException, SQLException {
         try {
             if (!nameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                     !confirmPasswordField.getText().isEmpty() && !addressField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() &&
                     !balanceAccountField.getText().isEmpty()) {
                 if(phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*"))
                     errorMessageText.setText("Phone number is incorrect");
                 else
                 {
                     if (passwordField.getText().equals(confirmPasswordField.getText())) {
                         if (ok) {
                             userMapper.insert(new User(null, nameField.getText(), usernameField.getText(), passwordField.getText(), addressField.getText(),
                                     phoneNumberField.getText(), "user", Double.parseDouble(balanceAccountField.getText())));
                             ((Node) (event.getSource())).getScene().getWindow().hide();

                             Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                             stage.setTitle("Welcome!");
                             stage.setScene(new Scene(root, 400, 400));
                             stage.show();
                             errorMessageText.setText("");
                         }
                     } else {
                         errorMessageText.setText("Password doesn't match");
                         errorMessageText.setFill(Color.RED);
                     }
                 }
             }
             else {
                 errorMessageText.setText("All fields must be completed");
                 errorMessageText.setFill(Color.RED);
             }
         }catch (NumberFormatException e){
             errorMessageText.setText("Balance account must be a number");
         }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(location.getPath().contains("userInterface") || location.getPath().contains("adminInterface")) {
            currentUsernameText.setText("Hi, " + user.getUsername() + "!");
            ObservableList<User> userObservableList = FXCollections.observableArrayList();
            ObservableList<Flight> flightObservableListForBooking = FXCollections.observableArrayList();
            List<Integer> flightsBooking = new LinkedList<>();

            try {
                flightsBooking = bookingMapper.findAllBookingForAnUser(user.getIdUser());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(location.getPath().contains("userInterface")){
                currentSoldText.setText(String.valueOf(user.getBalanceAccount()));
                for (Integer aFlightsBooking : flightsBooking)
                    try {
                        flightObservableListForBooking.add(flightMapper.getFlight(aFlightsBooking));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                originBookingColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("origin"));
                destinationBookingColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
                priceBookingColumn.setCellValueFactory(new PropertyValueFactory<Flight, Double>("price"));
                departureBookingColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("departure"));
                tableViewBooking.setItems(flightObservableListForBooking);
            }


            if(location.getPath().contains("adminInterface")){
                idUserColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("idUser"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
                usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
                passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
                addressColumn.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
                phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
                userRoleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userRole"));
                balanceAccountColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("balanceAccount"));
                try {
                    tableViewUser.setItems(userMapper.populateUserTable());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                idFlightColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("idFlight"));

                idBookingColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("idBooking"));
                idUserBookingColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("idUser"));
                idFlightBookingColumn.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("idFlight"));
                try {
                    tableViewBookingAdmin.setItems(bookingMapper.populateBooking());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                ObservableList<String> cmbUserRole = FXCollections.observableArrayList();
                cmbUserRole.add("user");
                cmbUserRole.add("admin");
                userRoleComboBox.setItems(cmbUserRole);

                updateUserButton.setDisable(true);
                cancelUpdateUserButton.setDisable(true);

                updateFlightButton.setDisable(true);
                cancelUpdateFlightButton.setDisable(true);

            }

            originColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("origin"));
            destinationColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
            flightTimeColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightTime"));
            companyColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("company"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<Flight, Double>("price"));
            departureColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("departure"));
            freeSeatsColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("freeSeats"));
            try {
                tableViewFlight.setItems(flightMapper.populateFlightTable());
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }
    }

    @FXML
    public void buttonSetBalanceAccountPressed() throws SQLException {
        try {
            user.setBalanceAccount(Double.parseDouble(newBalanceAccountField.getText()));

            userMapper.update(user);
            currentSoldText.setText(String.valueOf(user.getBalanceAccount()));
        }catch (NumberFormatException e){
            buyOrCancelFlightErrorText.setText("Balance account must be a number");
        }
    }

    @FXML
    public void selectFlight() throws SQLException {
        selectedFlight = tableViewFlight.getSelectionModel().selectedItemProperty().get();
    }

    @FXML
    public void buttonBuyFlightPressed() {
        selectedFlight = tableViewFlight.getSelectionModel().selectedItemProperty().get();
        try {
            if (selectedFlight.getDeparture().after(new Date(System.currentTimeMillis())))
            {
                if (selectedFlight.getFreeSeats() > 0) {
                    if (user.getBalanceAccount() > selectedFlight.getPrice()) {
                        user.setBalanceAccount(user.getBalanceAccount() - selectedFlight.getPrice());
                        userMapper.update(user);
                        currentSoldText.setText(String.valueOf(user.getBalanceAccount()));

                        selectedFlight.setFreeSeats(selectedFlight.getFreeSeats() - 1);
                        flightMapper.update(selectedFlight);

                        bookingMapper.insert(new Booking(null, user.getIdUser(), selectedFlight.getIdFlight()));
                        ObservableList<Flight> flightObservableListForBooking = FXCollections.observableArrayList();
                        List<Integer> flightsBooking = new LinkedList<Integer>();
                        try {
                            flightsBooking = bookingMapper.findAllBookingForAnUser(user.getIdUser());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        for (Integer aFlightsBooking : flightsBooking)
                            flightObservableListForBooking.add(flightMapper.getFlight(aFlightsBooking));

                        tableViewFlight.setItems(flightMapper.populateFlightTable());
                        tableViewBooking.setItems(flightObservableListForBooking);
                        buyOrCancelFlightErrorText.setText("");
                    }
                    else
                        buyOrCancelFlightErrorText.setText("You don't have enough money!");
                }
                else
                    buyOrCancelFlightErrorText.setText("There are no more free places");
            }
            else
                buyOrCancelFlightErrorText.setText("The flight is no longer available!");
        }catch(Exception ignored){

        }
    }

    @FXML
    public void buttonCancelFlightPressed() {
        selectedFlight = tableViewBooking.getSelectionModel().selectedItemProperty().get();
        try {
            System.out.println(new Date(System.currentTimeMillis()));
            if (selectedFlight.getDeparture().after(new Date(System.currentTimeMillis())))
            {
                user.setBalanceAccount(user.getBalanceAccount() + selectedFlight.getPrice());
                userMapper.update(user);
                currentSoldText.setText(String.valueOf(user.getBalanceAccount()));

                selectedFlight.setFreeSeats(selectedFlight.getFreeSeats() + 1);
                flightMapper.update(selectedFlight);

                bookingMapper.remove(bookingMapper.getBooking(user.getIdUser(), selectedFlight.getIdFlight()));
                ObservableList<Flight> flightObservableListForBooking = FXCollections.observableArrayList();
                List<Integer> flightsBooking = new LinkedList<>();
                flightsBooking = bookingMapper.findAllBookingForAnUser(user.getIdUser());

                for (Integer aFlightsBooking : flightsBooking)
                    flightObservableListForBooking.add(flightMapper.getFlight(aFlightsBooking));

                tableViewFlight.setItems(flightMapper.populateFlightTable());
                tableViewBooking.setItems(flightObservableListForBooking);
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void buttonFilterPressed() throws SQLException {
        tableViewFlight.setItems(flightMapper.filterFlights(originFilterField.getText(), destinationFilterField.getText()));
    }

    @FXML
    public void buttonReportGeneratePressed() throws SQLException{
        String report = "";
        HashMap<String, Integer> hashMap = bookingMapper.report();

        for(Map.Entry<String, Integer> entry: hashMap.entrySet()){
            report += entry.getKey() + " sells " + String.valueOf(entry.getValue()) + " ticket/tickets \n";
        }

        reportTextArea.setText(report);
    }

    private Boolean areNotUserFieldsEmpty(){
        return !nameField.getText().isEmpty() && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
                !addressField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() && !balanceAccountField.getText().isEmpty() &&
                !userRoleComboBox.getSelectionModel().isEmpty();
    }

    @FXML
    public void buttonCreateUserPressed() throws SQLException {
        try{
            if (areNotUserFieldsEmpty()) {
                    if(phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*"))
                        errorMessageText.setText("Phone number is incorrect");
                    else {
                        userMapper.insert(new User(null, nameField.getText(), usernameField.getText(), passwordField.getText(), addressField.getText(),
                                phoneNumberField.getText(), userRoleComboBox.getValue(), Double.parseDouble(balanceAccountField.getText())));
                        tableViewUser.setItems(userMapper.populateUserTable());
                        errorMessageText.setText("");
                    }
            }
            else {
                errorMessageText.setText("All fields must be completed");
                errorMessageText.setFill(Color.RED);
            }
        }catch (NumberFormatException e){
            errorMessageText.setText("Balance account must be a number");
        }
    }

    @FXML
    public void buttonUpdateUserModePressed() {
        createUserButton.setDisable(true);
        updateModeUserButton.setDisable(true);
        updateUserButton.setDisable(false);
        cancelUpdateUserButton.setDisable(false);
        deleteUserButton.setDisable(true);
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        balanceAccountField.setText("");
        balanceAccountField.setDisable(true);
        userRoleComboBox.setValue("");
    }

    @FXML
    public void selectUser() {
        selectedUser = tableViewUser.getSelectionModel().selectedItemProperty().get();
        nameField.setText(selectedUser.getName());
        usernameField.setText(selectedUser.getUsername());
        passwordField.setText(selectedUser.getPassword());
        addressField.setText(selectedUser.getAddress());
        phoneNumberField.setText(selectedUser.getPhoneNumber());
        balanceAccountField.setText(String.valueOf(selectedUser.getBalanceAccount()));
        userRoleComboBox.setValue(selectedUser.getUserRole());
    }

    @FXML
    public void buttonCancelUpdateUserPressed() {
        createUserButton.setDisable(false);
        updateModeUserButton.setDisable(false);
        updateUserButton.setDisable(true);
        cancelUpdateUserButton.setDisable(true);
        deleteUserButton.setDisable(false);
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        balanceAccountField.setText("");
        balanceAccountField.setDisable(false);
        userRoleComboBox.setValue("");
    }

    @FXML
    public void buttonUpdateUserPressed() throws SQLException {
        try{
            if (areNotUserFieldsEmpty()) {
                if(phoneNumberField.getText().matches(".*[a-z].*") || phoneNumberField.getText().matches(".*[A-Z].*"))
                    errorMessageText.setText("Phone number is incorrect");
                else {
                    userMapper.update(new User(selectedUser.getIdUser(), nameField.getText(), usernameField.getText(), passwordField.getText(), addressField.getText(),
                        phoneNumberField.getText(), userRoleComboBox.getValue(), Double.parseDouble(balanceAccountField.getText())));
                    errorMessageText.setText("");
                    tableViewUser.setItems(userMapper.populateUserTable());
                    createUserButton.setDisable(false);
                    updateModeUserButton.setDisable(false);
                    updateUserButton.setDisable(true);
                    cancelUpdateUserButton.setDisable(true);
                    deleteUserButton.setDisable(false);
                    nameField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    addressField.setText("");
                    phoneNumberField.setText("");
                    balanceAccountField.setText("");
                    balanceAccountField.setDisable(false);
                    userRoleComboBox.setValue("");
                }
            }
            else {
                errorMessageText.setText("All fields must be completed");
                errorMessageText.setFill(Color.RED);
            }
        }catch (NumberFormatException e){
            errorMessageText.setText("Balance account must be a number");
        }
    }

    @FXML
    public void buttonDeleteUserPressed() throws SQLException {

        userMapper.remove(selectedUser);
        tableViewUser.setItems(userMapper.populateUserTable());
    }

    private Boolean areNotFlightFieldsEmpty() {
        return !originField.getText().isEmpty() && !destinationField.getText().isEmpty() && !flightTimeField.getText().isEmpty() &&
                !companyField.getText().isEmpty() && !priceField.getText().isEmpty() && !departureField.getText().isEmpty() &&
                !freeSeatsField.getText().isEmpty();
    }

    @FXML
    public void buttonCreateFlightPressed() throws SQLException {
        try{
            if (areNotFlightFieldsEmpty()) {
                flightMapper.insert(new Flight(null, originField.getText(), destinationField.getText(), Integer.parseInt(flightTimeField.getText()), companyField.getText(),
                        Double.parseDouble(priceField.getText()), Date.valueOf(departureField.getText()), Integer.parseInt(freeSeatsField.getText())));
                errorMessageText.setText("");
                tableViewFlight.setItems(flightMapper.populateFlightTable());
            }
            else {
                errorMessageText.setText("All fields must be completed");
                errorMessageText.setFill(Color.RED);
            }
        }catch (NumberFormatException e){
            errorMessageText.setText("One of the following fields does not have the proper format: flight time, price or free seats ");
        }catch (IllegalArgumentException e){
            errorMessageText.setText("Departure does not have the proper format (yyyy-mm-dd)");
        }
    }

    @FXML
    public void buttonUpdateFlightModePressed() {
        createFlightButton.setDisable(true);
        updateModeFlightButton.setDisable(true);
        updateFlightButton.setDisable(false);
        cancelUpdateFlightButton.setDisable(false);
        deleteFlightButton.setDisable(true);
        originField.setText("");
        destinationField.setText("");
        flightTimeField.setText("");
        companyField.setText("");
        priceField.setText("");
        departureField.setText("");
        freeSeatsField.setText("");
    }

    @FXML
    public void selectFlightToModify() {
        selectedFlight = tableViewFlight.getSelectionModel().selectedItemProperty().get();
        originField.setText(selectedFlight.getOrigin());
        destinationField.setText(selectedFlight.getDestination());
        flightTimeField.setText(String.valueOf(selectedFlight.getFlightTime()));
        companyField.setText(selectedFlight.getCompany());
        priceField.setText(String.valueOf(selectedFlight.getPrice()));
        departureField.setText(String.valueOf(selectedFlight.getDeparture()));
        freeSeatsField.setText(String.valueOf(selectedFlight.getFreeSeats()));
    }

    @FXML
    public void buttonCancelUpdateFlightPressed() {
        createFlightButton.setDisable(false);
        updateModeFlightButton.setDisable(false);
        updateFlightButton.setDisable(true);
        cancelUpdateFlightButton.setDisable(true);
        deleteFlightButton.setDisable(false);
        nameField.setText("");
        originField.setText("");
        destinationField.setText("");
        flightTimeField.setText("");
        companyField.setText("");
        priceField.setText("");
        departureField.setText("");
        freeSeatsField.setText("");
    }

    @FXML
    public void buttonUpdateFlightPressed() throws SQLException {
        try {
            if (areNotFlightFieldsEmpty()) {
                flightMapper.update(new Flight(selectedFlight.getIdFlight(), originField.getText(), destinationField.getText(), Integer.parseInt(flightTimeField.getText()), companyField.getText(),
                        Double.parseDouble(priceField.getText()), Date.valueOf(departureField.getText()), Integer.parseInt(freeSeatsField.getText())));
                errorMessageText.setText("");
                tableViewFlight.setItems(flightMapper.populateFlightTable());
                createFlightButton.setDisable(false);
                updateModeFlightButton.setDisable(false);
                updateFlightButton.setDisable(true);
                cancelUpdateFlightButton.setDisable(true);
                deleteFlightButton.setDisable(false);
                originField.setText("");
                destinationField.setText("");
                flightTimeField.setText("");
                companyField.setText("");
                priceField.setText("");
                departureField.setText("");
                freeSeatsField.setText("");
            } else {
                errorMessageText.setText("All fields must be completed");
                errorMessageText.setFill(Color.RED);
            }
        }catch (NumberFormatException e){
            errorMessageText.setText("One of the following fields does not have the proper format: flight time, price or free seats ");
        }catch (IllegalArgumentException e){
            errorMessageText.setText("Departure does not have the proper format (yyyy-mm-dd)");
        }

    }

    @FXML
    public void buttonDeleteFlightPressed() throws SQLException {

        flightMapper.remove(selectedFlight);
        tableViewFlight.setItems(flightMapper.populateFlightTable());
    }

    @FXML
    public void logout(Event event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("Welcome, " + user.getUsername() + "!");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    @FXML
    public void buttonBackPressed(Event event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("Welcome, " + user.getUsername() + "!");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }
}