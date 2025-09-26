package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utilities.DatabaseUtility;
import utilities.JDBCReusableMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class loantechStepdefinitions {

    ResultSet resultSet;

    @Given("kullanici loantech database'ine baglanir")
    public void kullanici_loantech_database_ine_baglanir() {
        JDBCReusableMethods.createMyConnection();
    }

    @Then("database baglantisini kapatir")
    public void database_baglantisini_kapatir() {
        JDBCReusableMethods.closeMyConnection();
    }

    // < -- ================ US1001 ======================== -- >

    @When("deposits tablosunda amount degeri {int} ile {int} arasinda olan kayitlari sorgular")
    public void deposits_tablosunda_amount_degeri_ile_arasinda_olan_kayitlari_sorgular(Integer minAmount, Integer maxAmount) {
        String query = "SELECT * FROM deposits WHERE amount BETWEEN " + minAmount + " AND " + maxAmount;
        resultSet = JDBCReusableMethods.executeMyQuery(query);
    }

    @Then("bulunan sonuc sayisinin {int} oldugunu test eder")
    public void bulunan_sonuc_sayisinin_oldugunu_test_eder(Integer expectedSonucSayisi) throws SQLException {
        int sayac = 0;
        while (resultSet.next()) {
            sayac++;
        }
        Assert.assertEquals(sayac, expectedSonucSayisi.intValue());
    }

    // < -- ================ US1003 ======================== -- >

    @When("kullanici cron_schedules tablosundaki {string} bilgilerini sorgular")
    public void kullanici_cron_schedules_tablosundaki_bilgilerini_sorgular(String columnName) {
        String query = "SELECT " + columnName + " FROM cron_schedules";
        resultSet = JDBCReusableMethods.executeMyQuery(query);
    }

    @Then("ilk iki ismin {string} ve {string} olduğunu doğrular")
    public void ilk_iki_ismin_ve_olduğunu_doğrular(String expectedIlkIsim, String expectedIkinciIsim) throws SQLException {
        List<String> actualNames = new ArrayList<>();
        while(resultSet.next()) {
            actualNames.add(resultSet.getString("name"));
        }
        System.out.println("Veritabanından Gelen İsimler: " + actualNames);
        Assert.assertTrue(actualNames.size() >= 2, "Sonuçlarda en az 2 isim bulunamadı.");
        Assert.assertEquals(actualNames.get(0), expectedIlkIsim);
        Assert.assertEquals(actualNames.get(1), expectedIkinciIsim);
    }

    // < -- ================ US1004 ======================== -- >

    @When("subscribers tablosundaki {string} bilgilerini sorgular")
    public void subscribers_tablosundaki_bilgilerini_sorgular(String columnName) {
        // .feature'dan gelen sütun adını kullanarak sorguyu dinamik hale getiriyoruz.
        String query = "SELECT " + columnName + " FROM subscribers";
        resultSet = JDBCReusableMethods.executeMyQuery(query);
    }

    @Then("{string} sutununda {string} kaydının bulunduğunu test eder")
    public void sutununda_kaydının_bulunduğunu_test_eder(String columnName, String expectedValue) throws SQLException {
        List<String> actualValues = new ArrayList<>();
        while(resultSet.next()) {
            // .feature'dan gelen sütun adını kullanarak doğru veriyi alıyoruz.
            actualValues.add(resultSet.getString(columnName));
        }
        System.out.println("Sorgu sonucu bulunan emailler: " + actualValues);
        // Listenin, beklenen değeri içerip içermediğini kontrol ediyoruz.
        Assert.assertTrue(actualValues.contains(expectedValue), "Email listesinde '" + expectedValue + "' bulunamadı.");
    }

    // < -- ================ US1005 ======================== -- >

    @When("users tablosunda username'i {string} olan kaydın soyadini büyük harfe çevirir")
    public void users_tablosunda_username_i_olan_kaydın_soyadini_büyük_harfe_çevirir(String string) {
        String query = "update users set username = 'darkdark' where  username ='DARKDARK';";
        DatabaseUtility.executeQuery(query);

    }

    @Then("Update işlemi sonucunda username'i {string} olan kaydın soyisim değerinin büyük harfe dönüştüğünü test eder")
    public void update_işlemi_sonucunda_username_i_olan_kaydın_soyisim_değerinin_büyük_harfe_dönüştüğünü_test_eder(String string){
        String query = "SELECT lastname FROM users WHERE username = '" + string + "'";
        resultSet = JDBCReusableMethods.executeMyQuery(query);

    }


    @Given("kullanıcı bazi testler yapar")
    public void kullanıcıBaziTestlerYapar() {

        DatabaseUtility.createConnection();
        String query = "update users set username = 'darkdark' where  username ='Darkdark';";
        DatabaseUtility.executeQuery(query);

        DatabaseUtility.closeConnection();
    }
}
