package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.Account;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.junit.*;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AccountDAOTest {
    private final DBIFactory factory = new DBIFactory();
    private DBI dbi;
    private Handle handle;
    private AccountDAO accountDao;
    private static Account testAccount1;
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
        testAccount1 = new Account();
        testAccount1.setAccountId("1");
        testAccount1.setDateCreated(new Date());
    }

    @Before
    public void setup() throws Exception {
        this.dbi = factory.build(environment, hsqlConfig, "hsql");
        handle = dbi.open();
        handle.createCall(
                "CREATE TABLE AccountDevice ( accountId varchar(100), deviceId varchar(100))")
                .invoke();
        handle.createCall(
                "CREATE TABLE Device ( deviceId varchar(100) primary key, deviceType varchar(100))")
                .invoke();
        handle.createCall(
                "CREATE TABLE AccountSubscription (accountId varchar(100), subscriptionId varchar(100))")
                .invoke();
        handle.createCall(
                "CREATE TABLE Subscription (subscriptionId varchar(100) primary key, startDate date, endDate date, brand varchar(100))")
                .invoke();
        accountDao = dbi.onDemand(AccountDAO.class);
    }




    @After
    public void cleanUp() throws Exception {
        this.handle.close();
        this.dbi = null;
    }

    @Test
    public void testFindByAccountId(){

    }


    @Test
    public void testAddAccount(){
        accountDao.createAccountTable();
        accountDao.addAccount(testAccount1);
        List<Account> accounts = accountDao.getAllAccounts();
        Assert.assertTrue(accounts.contains(testAccount1));
    }

    @Test
    public void testRemoveAccount(){
        populateDb();
        accountDao.removeAccount(testAccount1.getAccountId());
        List<Account> accounts = accountDao.getAllAccounts();
        Assert.assertFalse(accounts.contains(testAccount1));
    }

    private void populateDb(){
        accountDao.createAccountTable();
        accountDao.addAccount(testAccount1);
    }

}
