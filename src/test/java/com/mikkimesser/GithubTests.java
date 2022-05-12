package com.mikkimesser;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class GithubTests {
    private final String pageURL = "https://github.com";
    private final String repository = "eroshenkoam/allure-example";
    private final String tabHeader = "Issues";
    private final String entityName = "с днем археолога!";
    private String entityNameNormalized = "";

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
        entityNameNormalized = entityName.trim().replaceAll(" +", " ");
        $(withText(entityNameNormalized)).shouldBe(Condition.visible);
    }

    @Test
    public void checkIssuesStepLambda() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем страницу", ()->{
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

        step(String.format("%s %s","проверяем видимость сущности под названием", entityName), ()->{
            entityNameNormalized = entityName.trim().replaceAll(" +", " ");
            $(withText(entityNameNormalized)).shouldBe(Condition.visible);
        });
    }

    @Test
    public void checkIssuesAnnotated(){
        WebSteps webSteps = new WebSteps();

        webSteps.openPage();
        webSteps.searchForRepository(repository);
        webSteps.openRepository(repository);
        webSteps.openTab(tabHeader);
        webSteps.checkIsEntityVisible(entityName);
    }
}
