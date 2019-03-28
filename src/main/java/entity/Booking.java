package entity;

public class Booking {
    private Integer idBooking;
    private Integer idUser;
    private Integer idFlight;

    public Booking() {
    }

    public Booking(Integer idBooking, Integer idUser, Integer idFlight) {
        this.idBooking = idBooking;
        this.idUser = idUser;
        this.idFlight = idFlight;
    }

    public Integer getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Integer idFlight) {
        this.idFlight = idFlight;
    }
}
