import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class GeoServiceImplTest {
    @BeforeAll
    public static void StartTestGeoserviceImpl() {
        System.out.println("Начало тестирования класса GeoServiceImpl");
    }


    @ParameterizedTest
    @ValueSource(strings = {GeoServiceImpl.LOCALHOST, GeoServiceImpl.MOSCOW_IP, GeoServiceImpl.NEW_YORK_IP, "172.0.32.13", "96.44.33.11"})
    public void methodByIpTest(String ip) {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(null, null, null, 0));
        locations.add(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        locations.add(new Location("New York", Country.USA, " 10th Avenue", 32));
        locations.add(new Location("Moscow", Country.RUSSIA, null, 0));
        locations.add(new Location("New York", Country.USA, null, 0));
        GeoServiceImpl geoService = Mockito.spy(new GeoServiceImpl());
        assertThat(locations, hasItem(geoService.byIp(ip)));
    }

    @Test
    public void ByIpShouldReturnNull() {
        GeoServiceImpl geoService = Mockito.spy(new GeoServiceImpl());
        String ip = "100.2.34.00";
        Assertions.assertNull(geoService.byIp(ip));

    }

    @AfterAll
    public static void FinishTestGeoserviceImpl() {
        System.out.println();
        System.out.println("Завершение  тестирования класса GeoServiceImplTest");
    }


}
