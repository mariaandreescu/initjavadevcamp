import com.adobe.devcamp.dao.GenericDao;
import com.adobe.devcamp.model.Gender;
import com.adobe.devcamp.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    @Mock
    private DataSource ds;
    @Mock
    private Connection connection;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    private User user;

    @Before
    public void setup() throws SQLException {
        user = new User("Ana", "Maria", "somemail@yahoo.com",
                new User.Profile(Gender.FEMALE, null, null));
        when(ds.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        doReturn(true).doReturn(false).when(resultSet).next();
        when(resultSet.getInt(any())).thenReturn(1);
        when(resultSet.getString(any())).thenReturn(user.getFirstName());
    }
    @Test
    public void shouldGetUsers() throws SQLException {
        GenericDao<User> dao = new GenericDao<User>(ds);
        Map<Integer, String> allUsers = dao.selectAll(User.class);
        assertEquals(12, allUsers.size());

    }
}
