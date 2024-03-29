package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.User;
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
public interface UserDAO {
    @SqlUpdate
    public void createUserTable();

    @SqlQuery
    @Mapper(UserMapper.class)
    public List<User> getAllUsers();

    @SqlQuery
    @Mapper(UserMapper.class)
    public User findByUserId(@Bind("id") String id);

    @SqlQuery
    @Mapper(UserMapper.class)
    public User findByEmail(@Bind("email") String email);

    @SqlUpdate
    public void addUser(@BindBean User user);

    @SqlUpdate
    public void removeUser(@Bind("email") String id);


    class UserMapper implements ResultSetMapper<User>
    {
        @Override
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            User user = new User();
            user.setFirstName(r.getString("firstName"));
            user.setLastName(r.getString("lastName"));
            user.setEmail(r.getString("email"));
            return user;
        }
    }

}
