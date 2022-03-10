import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardTest {
    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldSendFormToCard() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $(".menu-item").shouldBe(Condition.appear, Duration.ofSeconds(5)).click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Иванова Алиса");
        $("[data-test-id='phone'] input").setValue("+79050001234");
        $("span.checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(30));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }


}
