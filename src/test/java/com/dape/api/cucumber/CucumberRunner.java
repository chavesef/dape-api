package com.dape.api.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = "com.dape.api.cucumber",
        tags = "@CadastroAposta or @AtualizacaoAposta")
public class CucumberRunner {

}
