package entity;

public class User {
    private Integer idUser;
    private String name;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;
    private String userRole;
    private Double balanceAccount;

    public User() {

    }

    public User(Integer idUser, String name, String username, String password, String address, String phoneNumber, String userRole, Double balanceAccount) {
        this.idUser = idUser;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.balanceAccount = balanceAccount;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Double getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(Double balanceAccount) {
        this.balanceAccount = balanceAccount;
    }
}
