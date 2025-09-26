package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources", // Özellik dosyalarınızın yolu
        glue = "stepdefinitions", // Adım tanımları ve hooks class'inin oldugu paketiniz
        tags = "@testication", // Çalıştırılacak testlerin etiketleri
        plugin = {"pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"},
        monochrome = false
)
public class Runner_testication extends AbstractTestNGCucumberTests {
    // Burada herhangi bir özel kod bulunmasına gerek yok
}


