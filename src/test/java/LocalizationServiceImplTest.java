import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.hamcrest.MatcherAssert.assertThat;


public class LocalizationServiceImplTest {
    @BeforeAll
    public static void StartLocalizationServiceImplTest() {
        System.out.println("Начало тестирования класса LocalizationServiceImplTest");
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    public void methodLocaleTest(Country country) {
        String[] returnString = {"Welcome", "Добро пожаловать"};
        LocalizationServiceImpl localizationService = Mockito.spy(new LocalizationServiceImpl());
        assertThat(localizationService.locale(country), Matchers.isOneOf(returnString));
    }

    @AfterAll
    public static void FinishLocalizationServiceImplTest() {
        System.out.println();
        System.out.println("Завершение  тестирования класса LocalizationServiceImplTest");
    }

}
