package utilities;

import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    public static Faker faker = new Faker();

    public static Map<String, String> getRandomSpartanMap() {

        Map<String, String> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", faker.name().firstName());
        bodyMap.put("gender", faker.demographic().sex());
        bodyMap.put("phone", String.valueOf(faker.number().numberBetween(5_000_000_000L, 10_000_000_000L)));

        return bodyMap;
    }
}
