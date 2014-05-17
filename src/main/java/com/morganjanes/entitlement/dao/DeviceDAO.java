package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.Device;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@UseStringTemplate3StatementLocator
public interface DeviceDAO {

    @SqlQuery
    @Mapper(DeviceMapper.class)
    public List<Device> findByDeviceId(@Bind("deviceId")String deviceId);

    @SqlQuery
    @Mapper(DeviceMapper.class)
    public List<Device> findByAccountId(@Bind("deviceId")String accountId);


    @SqlUpdate
    public void addDevice(@BindBean Device device);

    @SqlUpdate
    public void removeDevice(@Bind("deviceId")String deviceId);


    class DeviceMapper implements ResultSetMapper<Device> {
        @Override
        public Device map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
            Device device = new Device();
            device.setDeviceId(r.getString("deviceId"));
            device.setDeviceType(r.getString("deviceType"));
            return device;
        }
    }
}
