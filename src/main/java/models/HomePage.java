package models;


import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

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

        assertThat(successMes)

    }
}
