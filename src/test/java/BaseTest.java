import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
    static Properties prop = new Properties();
    static String token;
    static String username;
    static ResponseSpecification responseSpecification = null;
    static RequestSpecification reqSpec;

    @BeforeAll
    static void beforeAll() {
        loadProperties();
        username = prop.getProperty("username");
        token = prop.getProperty("token");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = prop.getProperty("base.url");

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .setContentType(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();

        //RestAssured.responseSpecification = responseSpecification;
        //RestAssured.requestSpecification = reqSpec;

    }

    private static void loadProperties() {
        try (InputStream file = new FileInputStream("src/test/resources/application.properties")) {
            prop.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}