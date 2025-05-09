package com.dape.api.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = "com.dape.api.cucumber")
public class CucumberRunner {
    public static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse("confluentinc/cp-kafka:latest");

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DOCKER_IMAGE_NAME)
            .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS));

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
