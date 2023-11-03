package models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {

    private final Page page;
    private final Locator search;

    public LoginPage(Page page){
        this.page = page;
        this.search = page.locator(null);
    }

    public void navigate(String url){
        page.navigate(url);
    }


    public void perfLogin() throws InterruptedException {
        page.querySelector("#user-name").fill("standard_user");
        page.querySelector("#password").fill("secret_sauce");
        page.querySelector("#login-button").click();


        assertThat(page.getByText("Sauce Labs Backpack")).containsText("Sauce Labs Backpack");

        //page.wait(3000);

    }

}
