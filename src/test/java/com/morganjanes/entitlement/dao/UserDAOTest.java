package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.User;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.util.StringMapper;

import java.util.ArrayList;
import java.util.Arrays;
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
                "CREATE TABLE user ( email varchar(100) primary key, first_name varchar(255), last_name varchar(255))")
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
        Assert.assertEquals(user, buildUser("alice@localhost.io", "Alice", "Smith"));
    }

    @Test
    public void testAddUser(){
        populateDb();
        Query<Map<String, Object>> q =
                handle.createQuery("SELECT first_name FROM user");
        Query<String> q2 = q.map(StringMapper.FIRST);
        List<String> rs = q2.list();
        Assert.assertEquals(rs, Arrays.asList("Alice"));
    }

    @Test
    public void testDeleteUser(){
        populateDb();
        User user = new User();
        user.setEmail("alice@localhost.io");
        userDao.deleteUser(user);
        Query<Map<String, Object>> q =
                handle.createQuery("SELECT first_name FROM user");
        Query<String> q2 = q.map(StringMapper.FIRST);
        List<String> rs = q2.list();
        Assert.assertEquals(rs, new ArrayList());
    }

    @Test
    public void testGetAuthTokens(){}

    private void populateDb(){
        userDao.addUser(buildUser("alice@localhost.io", "Alice", "Smith"));
    }

    private User buildUser(String email, String firstName, String lastName){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}
