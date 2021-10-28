package ru.netology.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void ShouldSendForm() {
        //готовим дату
        String rightDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE, rightDate);
        //   $("[data-test-id='date']").setValue(String.valueOf(LocalDate.now().plusDays(4)));
        $("[name='name']").setValue("Василий");
        $("[name='phone']").setValue("+79991234567");
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $(withText("Забронировать")).click();
        $(withText(rightDate)).shouldBe(visible, Duration.ofSeconds(15));
    }


    //$$("[class='menu-item__control']")

    @Test
    void ShouldSendFormV2() {
        //готовим дату
        String rightDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Са");
        $$("[class='menu-item__control']").last().click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $(".icon_name_calendar").click();
        $("[data-step='1']").click();
        $("tr:nth-child(3) > td:nth-child(2)").click();
        $("[name='name']").setValue("Василий");
        $("[name='phone']").setValue("+79991234567");
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $(withText("Забронировать")).click();
        $(withText("Встреча успешно забронирована")).shouldBe(visible, Duration.ofSeconds(15));
    }



}
