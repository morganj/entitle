package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.User;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.junit.*;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.util.StringMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;


public class UserDAOTest {
    private final DBIFactory factory = new DBIFactory();
    private DBI dbi;
    private Handle handle;
    private UserDAO userDao;
    private final DatabaseConfiguration hsqlConfig = new DatabaseConfiguration();
    {
        LoggingFactory.bootstrap();
        hsqlConfig.setUrl("jdbc:h2:mem:DbTest-" + System.currentTimeMillis());
        hsqlConfig.setUser("sa");
        hsqlConfig.setDriverClass("org.h2.Driver");
        hsqlConfig.setValidationQuery("SELECT 1");
    }

    private final Environment environment = mock(Environment.class);

    @Before
    public void setUp() throws Exception {
        this.dbi = factory.build(environment, hsqlConfig, "hsql");
        handle = dbi.open();
        handle.createCall(
                "CREATE TABLE user ( email varchar(100) primary key, firstName varchar(255), lastName varchar(255))")
                .invoke();
        userDao = dbi.onDemand(UserDAO.class);
    }


    @After
    public void tearDown() throws Exception {
        this.handle.close();
        this.dbi = null;
    }

    @Test
    public void testFindByEmail(){
        populateDb();
        User user = userDao.findByEmail("alice@localhost.io");
        Assert.assertEquals(user, new User("alice@localhost.io", "Alice", "Smith"));
    }

    @Test
    public void testAddUser(){
        userDao.addUser(new User("alice@localhost.io", "Alice", "Smith"));
        Query<Map<String, Object>> q =
                handle.createQuery("SELECT firstName FROM user");
        Query<String> q2 = q.map(StringMapper.FIRST);
        List<String> rs = q2.list();
        Assert.assertTrue(rs.contains("Alice"));
    }

    @Test
    public void testRemoveUser(){
        populateDb();
        userDao.removeUser("alice@localhost.io");
        Query<Map<String, Object>> q =
                handle.createQuery("SELECT firstName FROM user");
        Query<String> q2 = q.map(StringMapper.FIRST);
        List<String> rs = q2.list();
        Assert.assertTrue(rs.isEmpty());
    }


    private void populateDb(){
        userDao.addUser(new User("alice@localhost.io", "Alice", "Smith"));
    }

}
