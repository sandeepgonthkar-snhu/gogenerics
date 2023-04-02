package org.drugs.projects.common;

import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Environment;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import javax.sql.DataSource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Slf4j
@Builder
public class MyCustomBatisModule extends PrivateModule {

    @NotEmpty
    private String name;

    @NotNull
    private JdbcHelper jdbcHelper;

    @NotNull
    private DataSourceFactory dataSourceFactory;

    @NotNull
    private Environment environment;

    @NotEmpty
    private Collection<Class<?>> mybatisMapperClasses;

    private static final String ENVIRONMENT_ID_KEY = "EnvironementId";
    private static final String DEFAULT_ENVIRONMENTID = "development";

    @Override
    protected void configure() {
        install(new CustomBatisModule());
        if (mybatisMapperClasses != null && !mybatisMapperClasses.isEmpty()) {
            mybatisMapperClasses.forEach(aClass -> expose(aClass));
        }
    }

    private class CustomBatisModule extends MyBatisModule {

        @Override
        protected void initialize() {
            environmentId(getEnvironmentId());
            Provider<DataSource> dataSourceProvider = () -> dataSourceFactory.build(environment.metrics(), name);
            install(jdbcHelper);
            bind(DataSource.class).toProvider(dataSourceProvider).in(Scopes.SINGLETON);
            bindTransactionFactoryType(JdbcTransactionFactory.class);
            addMapperClasses(mybatisMapperClasses);
        }

        private String getEnvironmentId() {
            if(dataSourceFactory != null && dataSourceFactory.getProperties() != null) {
                return dataSourceFactory.getProperties().getOrDefault(ENVIRONMENT_ID_KEY, DEFAULT_ENVIRONMENTID);
            }
            return DEFAULT_ENVIRONMENTID; // default environmentId
        }
    }
}
