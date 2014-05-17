package com.morganjanes.entitlement.dao;

import com.morganjanes.entitlement.core.Account;
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
import java.util.Date;
import java.util.List;

@UseStringTemplate3StatementLocator
public interface AccountDAO {

    @SqlUpdate
    public void createAccountTable();

    @SqlQuery
    @Mapper(AccountMapper.class)
    public List<Account> getAllAccounts();

    @SqlQuery
    @Mapper(AccountMapper.class)
    public Account findByAccountId(@Bind("accountId") String accountId);

    @SqlUpdate
    public void addAccount(@BindBean Account account);

    @SqlUpdate
    public void removeAccount(@Bind("accountId") String accountId);

    class AccountMapper implements ResultSetMapper<Account> {
        @Override
        public Account map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
            Account account = new Account();
            account.setAccountId(r.getString("accountId"));
            account.setDateCreated(new Date());
            return account;
        }
    }
}
