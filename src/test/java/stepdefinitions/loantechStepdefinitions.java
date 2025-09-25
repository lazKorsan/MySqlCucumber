package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utilities.JDBCReusableMethods;

import java.sql.ResultSet;
import java.sql.SQLException;

public class loantechStepdefinitions {

    ResultSet resultSet ;

    @Given("kullanici loantech database'ine baglanir")
    public void kullanici_loantech_database_ine_baglanir() {
        JDBCReusableMethods.createMyConnection();
    }

    @When("deposits tablosunda amount degeri {int} ile {int} arasinda olan kayitlari sorgular")
    public void deposits_tablosunda_amount_degeri_ile_arasinda_olan_kayitlari_sorgular(Integer minAmount, Integer maxAmount) {
       // SQL sorgusunu dinamik olarak oluştur
       String query = "SELECT * FROM deposits WHERE amount BETWEEN " + minAmount + " AND " + maxAmount;
       
       // Sorguyu çalıştır ve sonuçları al
        resultSet = JDBCReusableMethods.executeMyQuery(query);
    }

    @Then("bulunan sonuc sayisinin {int} oldugunu test eder")
    public void bulunan_sonuc_sayisinin_oldugunu_test_eder(Integer expectedSonucSayisi) throws SQLException {
       int sayac = 0;
       while (resultSet.next()){
           sayac++;
       }
       Assert.assertEquals(sayac, expectedSonucSayisi.intValue());
    }

    @Then("database baglantisini kapatir")
    public void database_baglantisini_kapatir() {
        JDBCReusableMethods.closeMyConnection();
    }
}
