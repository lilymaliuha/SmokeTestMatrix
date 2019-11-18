package cdssmoketest.brandingpagetests;

import cdssmoketest.BaseCDSTest;
import com.xyleme.bravais.DriverMaster;
import com.xyleme.bravais.web.pages.testssl.SSLLoginPage;
import org.testng.annotations.Test;

public class Test_For_Matrix extends BaseCDSTest {
private SSLLoginPage sslLoginPage;
    @Test
    public void testSSLCertificate() {
        //super.setUp();
            driver = DriverMaster.checkIfOldDriverClosedAndCreateNewDriverInstance();
            driver.manage().window().maximize();
            driver.get("https://bilshe.mastercard.ua/ua/signup/login");
        sslLoginPage = new SSLLoginPage(driver);
        sslLoginPage.inputId();
    }
}
