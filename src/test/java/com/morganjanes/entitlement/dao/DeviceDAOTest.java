package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.Device;
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

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class DeviceDAOTest {
    private final DBIFactory factory = new DBIFactory();
    private DBI dbi;
    private Handle handle;
    private DeviceDAO deviceDao;
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
        handle.createCall("DROP TABLE Device IF EXISTS").invoke();
        handle.createCall(
                "CREATE TABLE Device ( deviceId varchar(100) primary key, deviceType varchar(100))")
                .invoke();
        deviceDao = dbi.onDemand(DeviceDAO.class);
    }


    @After
    public void tearDown() throws Exception {
        this.handle.close();
        this.dbi = null;
    }

    @Test
    public void testAddDevice(){
        deviceDao.addDevice(new Device("1","ios"));
        Query<Map<String, Object>> q =
                handle.createQuery("SELECT deviceId FROM Device");
        Query<String> q2 = q.map(StringMapper.FIRST);
        List<String> rs = q2.list();
        Assert.assertTrue(rs.contains("1"));
    }

    @Test
    public void testRemoveDevice(){
        populateDb();
        deviceDao.removeDevice("1");
        Query<Map<String, Object>> q =
                handle.createQuery("SELECT deviceId FROM Device");
        Query<String> q2 = q.map(StringMapper.FIRST);
        List<String> rs = q2.list();
        Assert.assertTrue(rs.isEmpty());
    }

    private void populateDb(){
        deviceDao.addDevice(new Device("1", "ios"));
    }

}
