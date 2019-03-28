import dao.FlightDAO;
import entity.Flight;
import mapper.FlightMapper;
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

public class FlightMapperTest {

    private FlightMapper target;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private FlightDAO flightDAO;

    @Before
    public void setup() throws SQLException {
        target = new FlightMapper(flightDAO);
    }

    @Test
    public void testInsert() throws SQLException {
        String origin = "Cluj";
        String destination = "Paris";
        Integer flightTime = 120;
        String company = "Wizz Air";
        Double price = 53.96;
        Date departure = Date.valueOf("2019-03-27");
        Integer freeSeats = 152;
        Flight flight = new Flight(null, origin, destination, flightTime, company, price, departure, freeSeats);
        when(flightDAO.insert(anyString(), anyString(), anyInt(), anyString(), anyDouble(), any(), anyInt())).thenReturn(10);;

        Flight returnedFlight = target.insert(flight);

        verify(flightDAO, Mockito.times(1)).insert(origin, destination, flightTime, company, price, departure, freeSeats);
        assertEquals(10, returnedFlight.getIdFlight().intValue());

    }
}
