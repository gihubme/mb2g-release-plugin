package org.nnn4eu.cicdcircleci;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class PostgreSQLContainerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static PostgreSQLContainer sqlContainer = new PostgreSQLContainer("postgres:12");

    static {

        sqlContainer.start();
    }

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues.of(
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.datasource.driver-class-name=",
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect",
                "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
                "spring.sql.init.platform=postgres"
        ).applyTo(configurableApplicationContext.getEnvironment());
    }

}
