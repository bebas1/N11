import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;


public class full {

    @Test

    public  void RunTestCase() {
        //Step 1 // Anasayfa açılır. Sayfanın body sinde bulunan title ile girilen URL karşılaştırılır. Doğru ise test başarılıdır.
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User1\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.n11.com/");
        String URL = webDriver.getTitle();
        System.out.println("Girilen sayfa: " + URL);
        String currentURL = "n11.com - Alışverişin Uğurlu Adresi";
        Assert.assertEquals(URL, currentURL);
        System.out.println("Test başarılı tamamlandı.");

        //Step 2 // Login işlemi yapılır.
        WebElement girisyap = webDriver.findElement(By.className("btnSignIn"));
        girisyap.click();
        webDriver.findElement(By.cssSelector("#email")).sendKeys("brlbsdmr@gmail.com");
        webDriver.findElement(By.cssSelector("#password")).sendKeys("B123456");
        webDriver.findElement(By.cssSelector("#loginButton")).click();
        webDriver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);

        //Step 3 // Samsuung için arama işlemi yapılır
        webDriver.findElement(By.cssSelector("#searchData")).sendKeys("samsung");
        WebElement arama = webDriver.findElement(By.className("iconSearch"));
        arama.click();

        //Step 4
        String sonuc2 = ""; //Arama yapılan samsung için kaç adet sonuç döndüğü kontrol edilir.
        WebElement sonuc = webDriver.findElement(By.className("resultText"));
        if(sonuc != null){
            sonuc2 = webDriver.findElement(By.cssSelector("div.wrapper:nth-child(7) div.content:nth-child(3) div.container div.listingHolder:nth-child(2) div.productArea section.group.listingGroup.resultListGroup:nth-child(5) div.header div.resultText > strong:nth-child(2)")).getText();
            System.out.println("Samsung için"+" "+sonuc2+" "+"sonuç bulundu.");
        }

        //Step 5
        WebElement sayfa = ((ChromeDriver) webDriver).findElementByXPath("//a[@href='https://www.n11.com/arama?q=samsung&pg=2'][contains(text(),'2')]");
        sayfa.click();//2. sayfa tıklanır

        WebElement link = webDriver.findElement(By.linkText("2"));//2. sayfada olup olmadığı kontrolü için kullanılır.
        WebElement link2 = webDriver.findElement(By.className("active"));//Tıklamadan sonra gelen active class ile sayfanın aktif olup olmadığı kontrol edilmek için kullanılır.
        if (link != null && link2 != null){
            System.out.println("2. sayfa açılmıştır.");
        }
        else
            System.out.println("2. sayfa açılmamıştır.");
        webDriver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);

        //Step 6 & 7 & 8
        ((ChromeDriver) webDriver).findElementByXPath("//body/div[@id='wrapper']/div[@id='contentListing']/div[@class='container']/div[@class='listingHolder']/div[@class='productArea']/section[@class='group listingGroup resultListGroup']/div[@id='view']/ul/li[3]/div[1]/div[2]/span[1]").click();//Favorilere eklemeden önce elementin yerini bulunur ve tıklanır.
        WebElement favorieklendi = webDriver.findElement(By.cssSelector("div.wrapper:nth-child(8) div.content:nth-child(3) div.container div.listingHolder:nth-child(2) div.productArea section.group.listingGroup.resultListGroup:nth-child(5) div.listView ul:nth-child(2) li.column:nth-child(3) div.columnContent:nth-child(2) div.proDetail > span.textImg.followBtn.ok"));
        if (favorieklendi != null) {
            System.out.println("Seçtiğiniz ürün favorilere eklendi"); //Favorilere seçilen ürünün seçim sonrası oluşan followBtn class ı ile kontrolü sağlanır.
        }

        webDriver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
        String eklenen = "";
        String eklenen2 = "";

        eklenen = webDriver.findElement(By.cssSelector("div.wrapper:nth-child(8) div.content:nth-child(3) div.container div.listingHolder:nth-child(2) div.productArea section.group.listingGroup.resultListGroup:nth-child(5) div.listView li.column:nth-child(3) div.columnContent:nth-child(2) div.pro a.plink > h3.productName.bold")).getText();

        Actions actions = new Actions(webDriver);//hover aksiyonu başlatılr.
        actions.moveToElement(webDriver.findElement(By.xpath("//div[@class='myAccount']"))).perform();//Hesabım sekmesi üzerindeyken dropdown açılır.
        actions.moveToElement(webDriver.findElement(By.xpath("//a[@title='İstek Listem / Favorilerim']"))).perform();//Dropdown açıkken Favorilerim seçilir.
        WebElement favoritikla = webDriver.findElement(By.linkText("İstek Listem / Favorilerim"));//Favorilerim seçimi tıklanır.
        favoritikla.click();
        WebElement favorilerim = webDriver.findElement(By.className("listItemTitle"));
        favorilerim.click();

        eklenen2 = webDriver.findElement(By.cssSelector("div.wrapper.myPage:nth-child(4) div.content:nth-child(3) div.container div.clearfix div.accContent:nth-child(3) div.group.listingGroup.wishListGroup:nth-child(2) div.listView:nth-child(3) li.column.wishListColumn div.columnContent.adBg:nth-child(2) div.pro a.plink > h3.productName.bold:nth-child(3)")).getText();

        Assert.assertEquals(eklenen,eklenen2);
        System.out.println("İki Ürün de birbirine eşittir.==>" +eklenen);

        //Step 9 & 10
        webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        WebElement sil = webDriver.findElement(By.className("deleteProFromFavorites"));
        sil.click(); //Ürünü favorilerden silmek için tıklanır.
        WebElement tamamBtn = webDriver.findElement(By.className("btnHolder"));
        tamamBtn.click(); //Çıkan popup ta silme işlemine onay verilir.

        WebElement bosMu = webDriver.findElement(By.className("emptyWatchList")); //Tek bir ürün olması sebebiyle sayfanın boş olup olmadığı kontol edilir. emptyWatchList silme işleminden sonra oluşan bir classtır.
        if(bosMu != null){
            System.out.println("Eee favorilere ekleseydin burada görürdün :) Favorilerinde ürün yok ! ");
        }
        webDriver.quit(); // Sayfa kapatılır.
    }
}

