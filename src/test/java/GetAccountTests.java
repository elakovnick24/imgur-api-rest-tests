import models.account_repsonse.GetAccountResponse;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccountTests extends BaseTest {
    static private Map<String, String> headers = new HashMap<>();

    @Test
    void getAccountInfoPositiveTest() {
        given()
                .log()
                .all()
                //.headers("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/account/{userName}", username)
                .prettyPeek()
                .then();
        //.spec(responseSpecification);
    }

    @Test
    void getAccountInfoNegativeTest() {
        when()
                .get("https://api.imgur.com/3/account/{userName}", username);
    }

    @Test
    void getAccountInfoPositiveWithManyChecksTest() {
        given()
                .headers("Authorization", token)
                .expect()
                .body(CoreMatchers.containsString(username))
                .when()
                .get("https://api.imgur.com/3/account/{userName}", username)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoPositiveWithObjectTest() {
        GetAccountResponse response =
                given()
                        .when()
                        .get("https://api.imgur.com/3/account/AnnaSmotrova")
                        .prettyPeek()
                        .then()
                        .extract()
                        .body()
                        .as(GetAccountResponse.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getData().getUrl()).contains(username);

    }
}
