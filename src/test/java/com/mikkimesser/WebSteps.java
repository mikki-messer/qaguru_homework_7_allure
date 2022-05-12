package com.mikkimesser;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class WebSteps {
    //locators
    SelenideElement searchRepoInput = $(".header-search-input");
    SelenideElement searchEntityInput = $("#js-issues-search");
    //URL
    String pageURL = "https://github.com";

    //actions
    @Step("Открываем страницу")
    public void openPage(){
        Selenide.open(pageURL);
    }

    @Step("ищем репозиторий {repo}")
    public void searchForRepository(String repo){
        searchRepoInput.setValue(repo).pressEnter();
    }

    @Step("переходим по ссылке {repo}")
    public void openRepository(String repo){
        $(linkText(repo)).click();
    }

    @Step("кликаем по табу {tabHeader}")
    public void openTab(String tabHeader){
        $(partialLinkText(tabHeader)).click();
    }

    @Step("ищем сущность под названием {entityName}")
    public void searchForEntity(String entityName){
        searchEntityInput.clear();
        searchEntityInput.setValue(entityName).pressEnter();
    }

    @Step("проверяем видимость сущности под названием {entityName}")
    public void checkIsEntityVisible(String entityName){
        String issueNameNormalized = entityName.trim().replaceAll(" +", " ");
        $(withText(issueNameNormalized)).shouldBe(Condition.visible);
        attachScreenshot();
    }

    @Attachment(value = "screenshot01", type = "image/png", fileExtension = "png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
