package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.AuthToken;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface AuthTokenDAO  {
    @SqlQuery
    @Mapper(AuthTokenMapper.class)
    public List<AuthToken> findById(@Bind("id") String id);

    @SqlQuery
    @Mapper(AuthTokenMapper.class)
    public AuthToken getAuthToken(@BindBean("AuthToken") AuthToken token);

    class AuthTokenMapper implements ResultSetMapper<AuthToken> {
        @Override
        public AuthToken map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return null;
        }
    }

}
