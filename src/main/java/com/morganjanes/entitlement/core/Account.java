package com.morganjanes.entitlement.core;


import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.annotations.Siren4JSubEntity;
import com.google.code.siren4j.resource.BaseResource;
import com.google.code.siren4j.resource.CollectionResource;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.Date;

@Siren4JEntity(name = "account", uri = "/accounts/{id}")
@UseStringTemplate3StatementLocator
public class Account extends BaseResource {

    private String accountId;
    private Date dateCreated;
    @Siren4JSubEntity(uri = "/accounts/{parent.id}/devices")
    private CollectionResource<Device> devices;
    @Siren4JSubEntity(uri = "/accounts/{parent.id}/purchases")
    private CollectionResource<Product> purchases;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!accountId.equals(account.accountId)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return accountId.hashCode();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String id) {
        this.accountId = id;
    }

    public void setDevices(CollectionResource<Device> devices) {
        this.devices = devices;
    }

    public CollectionResource<Device> getDevices(){return this.devices;}

    public CollectionResource<Product> getPurchases() {
        return purchases;
    }

    public void setSubscriptions(CollectionResource<Product> purchases) {
        this.purchases = purchases;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}