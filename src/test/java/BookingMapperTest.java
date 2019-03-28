import dao.BookingDAO;
import entity.Booking;
import mapper.BookingMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingMapperTest {

    private BookingMapper target;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private BookingDAO bookingDAO;

    @Before
    public void setup() throws SQLException {
        target = new BookingMapper(bookingDAO);
    }

    @Test
    public void testInsert() throws SQLException {
        Integer idUser = 6;
        Integer idFlight = 15;
        Booking booking = new Booking(null, idUser, idFlight);
        when(bookingDAO.insert(anyInt(), anyInt())).thenReturn(10);

        Booking returnedBooking = target.insert(booking);

        verify(bookingDAO, Mockito.times(1)).insert(idUser, idFlight);
        assertEquals(10, returnedBooking.getIdBooking().intValue());

    }
}
