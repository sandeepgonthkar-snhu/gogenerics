package org.drugs.projects;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import io.dropwizard.Application;
import io.dropwizard.configuration.DefaultConfigurationFactoryFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.drugs.projects.common.DrugsAPIConfiguration;
import org.drugs.projects.common.MyCustomBatisModule;
import org.drugs.projects.persistance.DrugMapper;
import org.drugs.projects.resources.DrugServiceResource;
import org.eclipse.jetty.servlets.DoSFilter;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DrugsAPIApplication extends Application<DrugsAPIConfiguration> {

    private Injector injector;
    private List<Module> guiceModules;

    @Override
    public void run(DrugsAPIConfiguration drugsAPIConfiguration, Environment environment) throws Exception {

        // DOS filter
        environment.servlets().addFilter("DoSFilter", DoSFilter.class);

        // Modules
        this.guiceModules = new ArrayList<>();
        this.guiceModules.add(getDatabaseModule(drugsAPIConfiguration, environment));

        injector = Guice.createInjector(guiceModules);

        /*
        environment.servlets().addServletListeners(new GuiceServletContextListener() {
            @Override
            protected Injector getInjector() {
                return this.getInjector();
            }
        });

         */

        for (Class<?> resourceClass : getResources()) {
            environment.jersey().register(injector.getInstance(resourceClass));
        }
    }

    private List<Class<?>> getResources() {
        return Arrays.asList(DrugServiceResource.class);
    }

    private Module getDatabaseModule(DrugsAPIConfiguration configuration, Environment environment) {
        return MyCustomBatisModule.builder()
                .name("Drugs-Services")
                .environment(environment)
                .jdbcHelper(JdbcHelper.MySQL)
                .mybatisMapperClasses(Arrays.asList(DrugMapper.class))
                .dataSourceFactory(configuration.getDatabase())
                .build();
    }

    public static void main(String[] args) throws Exception {
        new DrugsAPIApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DrugsAPIConfiguration> bootstrap) {
        bootstrap.setConfigurationFactoryFactory(new DefaultConfigurationFactoryFactory<DrugsAPIConfiguration>());

        super.initialize(bootstrap);
    }

}
