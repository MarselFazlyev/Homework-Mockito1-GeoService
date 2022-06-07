import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageSenderImplTest {
    @BeforeAll
    public static void StartMessageSenderImplTest() {
        System.out.println("Начало тестирования класса MessageSenderImplTest");

    }

//    @BeforeEach
//    public static void asdadd ()  {
//        GeoServiceImpl geoService = Mockito.spy(new GeoServiceImpl());
//        LocalizationServiceImpl localizationService = Mockito.spy(new LocalizationServiceImpl());
//        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
//        Map<String, String> headers = new HashMap<>();
//    }


    @ParameterizedTest
    @MethodSource("argumentsForTest")
    public void MessageSenderShouldSendRussianMessageToRussianIp(String argument) {
//        Подготовка данных
        GeoServiceImpl geoService = Mockito.spy(new GeoServiceImpl());
        LocalizationServiceImpl localizationService = Mockito.spy(new LocalizationServiceImpl());
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);

        Mockito.doReturn(new Location("NewYork", Country.USA, "KennedyStreet", 1)).when(geoService).byIp(Mockito.any());
        Mockito.doReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15)).when(geoService).byIp(Mockito.startsWith("172"));

        assertThat(messageSender.send(headers), Matchers.anyOf(Matchers.equalTo("Welcome"), Matchers.equalTo("Добро пожаловать")));
    }

    public static Stream<String> argumentsForTest() {
        return Stream.of("172.123.12.19", "96.123.12.19");
    }

    @Test
    public void MessageSenderShouldThrowNewNPException() {
        String argument = "98.000.00.00";
        GeoServiceImpl geoService = Mockito.spy(new GeoServiceImpl());
        LocalizationServiceImpl localizationService = Mockito.spy(new LocalizationServiceImpl());
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);
        assertThrows(NullPointerException.class, () -> {
            messageSender.send(headers);
        });
    }

    @AfterAll
    public static void FinishTestGeoserviceImpl() {
        System.out.println();
        System.out.println("Завершение  тестирования класса MessageSenderImplTest");
    }
}
