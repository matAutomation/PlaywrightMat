import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import models.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

public class TestPlay {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();



            HomePage homePage = new HomePage(page);

            homePage.navigate("https://www.saucedemo.com/");
            homePage.perfLoginFail();

    }
    }
}
