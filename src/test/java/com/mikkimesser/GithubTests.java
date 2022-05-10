package com.mikkimesser;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class GithubTests {
    public final String pageURL = "https://github.com";
    public final String searchText = "eroshenkoam/allure-example";
    public final String tabHeader = "Issues";
    public final String issueName = "с днем археолога!";

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1280x720";
        Configuration.holdBrowserOpen = true;
    }

    @Test
    public void checkIssuesSimple() {
        Selenide.open(pageURL);
        $(".header-search-input").setValue(searchText).pressEnter();
        $(By.linkText(searchText)).click();
        $(By.partialLinkText(tabHeader)).click();
        //$(By.linkText("С Новым Годом  (2022)")).click();
        //$("#issue_76_link").click();
        //$(byAttribute("aria-label*","С Новым Годом  (2022)")).click();
        String issueNameNormalized = issueName.trim().replaceAll(" +", " ");
        System.out.println(issueNameNormalized);
        $(withText(issueNameNormalized)).click();
    }
}
