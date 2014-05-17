package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.User;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.junit.*;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import static org.mockito.Mockito.mock;


public class UserDAOTest {
    private final DBIFactory factory = new DBIFactory();
    private DBI dbi;
    private UserDAO userDao;
    private static User alice;
    private final DatabaseConfiguration hsqlConfig = new DatabaseConfiguration();
    {
        LoggingFactory.bootstrap();
        hsqlConfig.setUrl("jdbc:h2:mem:DbTest-" + System.currentTimeMillis());
        hsqlConfig.setUser("sa");
        hsqlConfig.setDriverClass("org.h2.Driver");
        hsqlConfig.setValidationQuery("SELECT 1");
    }

    private final Environment environment = mock(Environment.class);

    @BeforeClass
    public static void init(){
        alice = new User("alice@localhost.io","Alice", "Smith");
    }

    @Before
    public void setUp() throws Exception {
        dbi = factory.build(environment, hsqlConfig, "hsql");
        userDao = dbi.onDemand(UserDAO.class);
    }


    @After
    public void tearDown() throws Exception {
        this.dbi = null;
    }

    @Test
    public void testFindByEmail(){
        populateDb();
        User user = userDao.findByEmail(alice.getEmail());
        Assert.assertEquals(user, alice);
    }

    @Test
    public void testAddUser(){
        userDao.createUserTable();
        userDao.addUser(alice);
        List<User> users = userDao.getAllUsers();
        Assert.assertTrue(users.contains(alice));
    }

    @Test
    public void testRemoveUser(){
        populateDb();
        userDao.removeUser(alice.getEmail());
        List<User> users = userDao.getAllUsers();
        Assert.assertFalse(users.contains(alice));
    }


    private void populateDb(){
        userDao.createUserTable();
        userDao.addUser(alice);
    }

}
