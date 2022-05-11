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
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class GithubTests {
    private final String pageURL = "https://github.com";
    private final String repository = "eroshenkoam/allure-example";
    private final String tabHeader = "Issues";
    private final String issueName = "с днем археологга!";
    private String issueNameNormalized = "";

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1280x720";
        Configuration.holdBrowserOpen = true;
    }

    @Test
    public void checkIssuesSimple() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Selenide.open(pageURL);
        $(".header-search-input").setValue(repository).pressEnter();
        $(linkText(repository)).click();
        $(partialLinkText(tabHeader)).click();
        String issueNameNormalized = issueName.trim().replaceAll(" +", " ");
        System.out.println(issueNameNormalized);
        $(withText(issueNameNormalized)).click();
    }

    @Test
    public void checkIssuesStepLambda() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем страницы", ()->{
            Selenide.open(pageURL);
        });

        step(String.format("%s %s", "ищем репозиторий", repository), ()->{
            $(".header-search-input").setValue(repository).pressEnter();
        });

        step(String.format("%s %s", "открываем репозиторий", repository), ()-> {
            $(linkText(repository)).click();
        });

        step(String.format("%s %s", "кликаем по табу",tabHeader), ()->{
            $(partialLinkText(tabHeader)).click();
        });

        step(String.format("нормализуем название сущности %s для поиска", issueName), ()->{
            issueNameNormalized = issueName.trim().replaceAll(" +", " ");
        });

        step(String.format("%s %s","кликаем по сущности под названием",issueName), ()->{
            $(withText(issueNameNormalized)).click();
        });
    }
}
