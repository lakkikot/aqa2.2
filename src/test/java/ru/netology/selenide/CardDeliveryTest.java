package ru.netology.selenide;

import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.ParseException;
import java.time.*;
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
    void shouldBookMeetingAutoSet() {
        //готовим дату
        String rightDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE, rightDate);
        $("[name='name']").setValue("Василий");
        $("[name='phone']").setValue("+79991234567");
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $(withText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + rightDate));
    }


    @Test
    void shouldBookMeetingByClickingCityAndDate() throws ParseException {

        $("[data-test-id=city] input").setValue("Са");
        $$("[class='menu-item__control']").findBy(text("Саратов")).click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE); //очистить поле ввода даты
        $(".icon_name_calendar").click();

        //дата через неделю в unix-формате - как дни в календаре
        String unixDate = String.valueOf(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(7).toInstant().toEpochMilli());

        //проверяем, есть ли дата на следующей строке на том же месте
        try {
            $("[data-day='" + unixDate + "']").click();
        } catch (ElementNotFound e) {
            $("[data-step='1']").click(); //если не оказалось, то кликаем на след месяц
            $("[data-day='" + unixDate + "']").click(); //ищем нужную unix-дату
        }

        $("[name='name']").setValue("Василий");
        $("[name='phone']").setValue("+79991234567");
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $(withText("Забронировать")).click();

        //дата через неделю для итоговой проверки
        String rightDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + rightDate));
    }


}
