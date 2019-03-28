import dao.UserDAO;
import entity.User;
import mapper.UserMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserMapperTest {

    private UserMapper target;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private UserDAO userDAO;

    @Before
    public void setup() throws SQLException {
        target = new UserMapper(userDAO);
    }

    @Test
    public void testInsert() throws SQLException {
        String name = "User";
        String username = "user";
        String password = "u";
        String address = "user";
        String phoneNumber = "0777777777";
        String userRole = "user";
        Double balanceAccount = 2500.00;
        User user = new User(null, name, username, password, address, phoneNumber, userRole, balanceAccount);
        when(userDAO.insert(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyDouble())).thenReturn(10);

        User returnedUser = target.insert(user);

        verify(userDAO, Mockito.times(1)).insert(name, username, password, address, phoneNumber, userRole, balanceAccount);
        assertEquals(10, returnedUser.getIdUser().intValue());

    }
}
