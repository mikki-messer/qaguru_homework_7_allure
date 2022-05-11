package com.mikkimesser;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class GithubTests {
    public final String pageURL = "https://github.com";
    public final String searchText = "eroshenkoam/allure-example";
    public final String tabHeader = "Issues";
    public final String issueName = "с днем археологга!";

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1280x720";
        Configuration.holdBrowserOpen = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    public void checkIssuesSimple() {
        Selenide.open(pageURL);
        $(".header-search-input").setValue(searchText).pressEnter();
        $(By.linkText(searchText)).click();
        $(By.partialLinkText(tabHeader)).click();
        String issueNameNormalized = issueName.trim().replaceAll(" +", " ");
        System.out.println(issueNameNormalized);
        $(withText(issueNameNormalized)).click();
    }
}
