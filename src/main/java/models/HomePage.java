package models;


import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.List;

public class HomePage {
    private final Page page;
    private final Locator search;

    public HomePage(Page page) {
        this.page = page;
        this.search = page.locator("a");

    }

    public void navigate(String url){
        page.navigate(url);
    }

    //Crear un metodo para abstraer el login

    public void setLogin(String user, String pass){
        page.querySelector("#user-name").fill(user);
        page.querySelector("#password").fill(pass);

    }

    public void perfLogin() throws InterruptedException {
        setLogin("standard_user", "secret_sauce");
        page.querySelector("#login-button").click();

        assertThat(page.getByText("Sauce Labs Backpack")).containsText("Sauce Labs Backpack");

    }

    public void perfLoginFail(){

        setLogin("standard_user", "secret_sauce");;
        page.querySelector("#login-button").click();
        //page.querySelector(".error-message-container");

        assertThat(page.locator(".error-message-container")).containsText("Epic sadface: Username and password do not match any user in this service");

    }

    public void perfPruchaseInCart(){
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Components").setExact(true)).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Show All Components")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Monitors (2)").setExact(true)).click();
        page.getByText("Samsung SyncMaster 941BW").click();
        page.getByLabel("Qty").click();
        page.getByLabel("Qty").fill("2");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" 2 item(s) - $484.00")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" View Cart")).click();

        page.waitForLoadState(LoadState.LOAD);
    }

    public void assertPurchase(){
        String totalText = page.querySelectorAll(".text-right").get(1).textContent();
        System.out.println("The total is: " + totalText);

        String unitText = page.querySelectorAll(".text-right").get(13).textContent();
        System.out.println("The unit price is: " + unitText);

        List<ElementHandle> elements = page.querySelectorAll(".text-right");

        for (ElementHandle element : elements){
            String elementText = element.textContent();
            System.out.println("Element is:" + elementText);
        }

        page.querySelector("[data-original-title='Update']").click();

        page.waitForSelector(".alert-success", new Page.WaitForSelectorOptions());

        String successMes = page.querySelector(".alert-success").textContent();
        System.out.println(successMes);

        assertThat(page.locator(".alert-success")).containsText("Success: You have modified your shopping cart!");
    }
}
