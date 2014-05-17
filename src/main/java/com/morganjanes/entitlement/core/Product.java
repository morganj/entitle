package com.morganjanes.entitlement.core;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.BaseResource;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

@Siren4JEntity(name = "product", uri = "/products/{id}")
@UseStringTemplate3StatementLocator
public abstract class Product extends BaseResource {
    private String productId;
    private String title;
    private String description;
    private Double retailPrice;
    private Double promotionalPrice;


}
