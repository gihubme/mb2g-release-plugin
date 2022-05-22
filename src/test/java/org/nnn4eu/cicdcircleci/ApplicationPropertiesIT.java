package org.nnn4eu.cicdcircleci;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"},
                    properties = {"example.firstProperty=annotation"})
public class ApplicationPropertiesIT {
    @Value("${server.port}")
    String serverPort;// from application-test.properties
    @Value("${test1}")
    String test1;// from application-test.properties
    @Value("${management.server.port}")
    String managmentPort; // from application.properties

    @Value("${example.firstProperty}")
    String firstProperty; // from application.properties

    @Test
    void test() {
        MatcherAssert.assertThat(serverPort, Matchers.equalTo("8082"));
        MatcherAssert.assertThat(managmentPort, Matchers.equalTo("9090"));
        MatcherAssert.assertThat(test1, Matchers.containsString("some value"));
        MatcherAssert.assertThat(firstProperty, Matchers.equalTo("annotation"));

    }
}
