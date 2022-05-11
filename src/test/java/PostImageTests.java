import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class PostImageTests extends BaseTest {
    static final String INPUT_IMAGE_FILE_PATH = "avatar.jpeg";
    private String uploadImageId;

    private String fileString;

    @BeforeEach
    void setUp() {
        byte[] fileContent = getFileContent();
        fileString = Base64.getEncoder().encodeToString(fileContent);
    }


    @Test
    void uploadFileTest() {
        uploadImageId = given()
                .headers("Authorization", token)
                .multiPart("image", fileString)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deleteHash");
    }

    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("/account/{username}/image/{deleteHash}", username, uploadImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    private byte[] getFileContent() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource(INPUT_IMAGE_FILE_PATH)
                .getFile());

        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
