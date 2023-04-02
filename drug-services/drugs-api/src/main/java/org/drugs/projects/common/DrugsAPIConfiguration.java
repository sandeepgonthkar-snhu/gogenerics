package org.drugs.projects.common;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DrugsAPIConfiguration extends Configuration {

    @Valid
    @NotNull
    @Getter
    @Setter
    private DataSourceFactory database;
}
