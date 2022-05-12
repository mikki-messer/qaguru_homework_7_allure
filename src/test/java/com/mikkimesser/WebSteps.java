package com.mikkimesser;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class WebSteps {
    //locators
    SelenideElement searchInput = $(".header-search-input");

    //URL
    String pageURL = "https://github.com";

    //actions
    @Step("Открываем страницу")
    public void openPage(){
        Selenide.open(pageURL);
    }

    @Step("ищем репозиторий {repo}")
    public void searchForRepository(String repo){
        searchInput.setValue(repo).pressEnter();
    }

    @Step("переходим по ссылке {repo}")
    public void openRepository(String repo){
        $(linkText(repo)).click();
    }

    @Step("кликаем по табу {tabHeader}")
    public void openTab(String tabHeader){
        $(partialLinkText(tabHeader)).click();
    }

    @Step("проверяем видимость сущности под названием {entityName}")
    public void checkIsEntityVisible(String entityName){
        String issueNameNormalized = entityName.trim().replaceAll(" +", " ");
        $(withText(issueNameNormalized)).shouldBe(Condition.visible);
    }
}
