package com.dape.api.cucumber;

import com.dape.api.ApiApplication;
import com.dape.api.cucumber.config.KafkaIntegrationTestConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan(basePackages = "ps.regulatory.dimp",
        excludeFilters=@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApiApplication.class) )
@EnableAutoConfiguration
@ContextConfiguration(initializers = { CucumberRunner.Initializer.class }, classes = { KafkaIntegrationTestConfig.class })
@DirtiesContext
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

}
