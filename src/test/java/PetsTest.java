import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import io.qameta.allure.Step;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Owner("Alexandra")
@DisplayName("Тесты с питомцами")
@Tag("pets")
public class PetsTest {
    public final static String url = "https://petstore.swagger.io/";

    static {
        RestAssured.filters(new AllureRestAssured());
    }

    @Order(1)
    @DisplayName("Добавление питомца в мазазин")
    @Description("Инициалтзация питомца, отправка запроса на сервер, получение ответа и его проверка на соответствие по json схеме и по коду ответа - 200")
    @Tag("put")
    @Test
    public void putPet(){
        Specifications.installSpec(Specifications.requestSpec(url),Specifications.responseSpec(200));

        step("Инициализация питомца");
        PetsAdd pets = PetsAdd.Add(123,"Volodymyr","url11");

        step("Отправка PUT-запроса для добавления данных о животном");
        Response postResponse = given()
                .body(pets)
                .when()
                .put("v2/pet");

        step("Проверка соответствия ответа схеме JSON");
        ValidatableResponse validation = postResponse.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("petSchema.json"));

        step("Извлечение данных о животном из ответа");
        SuccessAddPet updatePets = validation.extract().as(SuccessAddPet.class);


    }
    @Order(2)
    @DisplayName("Возврат питомцев с статусом pending")
    @Description("Отправка запроса на сервер, получение ответа и его проверка на соответствие по json схеме и по коду ответа - 200")
    @Tag("get")
    @Test
    public void getStatus(){
        Specifications.installSpec(Specifications.requestSpec(url),Specifications.responseSpec(200));

        step("Отправка GET-запроса для возврата данных о животном");
        Response postResponse = given()
                .when()
                .get("v2/pet/findByStatus?status=pending");

        step("Проверка соответствия ответа схеме JSON");
        ValidatableResponse validation = postResponse.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("statusPetSchema.json"));

        step("Извлечение данных о животном из ответа");
        List<Pets> petsStatus = validation.extract().jsonPath().getList("$.body",Pets.class);
    }
    @Order(3)
    @DisplayName("Обновление имени питомца по id")
    @Description("Инициалтзация обновленных данных питомца, отправка запроса на сервер, получение ответа и его проверка на соответствие по json схеме и по коду ответа - 200")
    @Tag("post")
    @Test
    public void postName(){
        Specifications.installSpec(Specifications.requestSpec(url),Specifications.responseSpec(200));

        step("Отправка POST-запроса для обновления данных о животном");
        Response postResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("name","Valera")
                .when()
                .post(String.format("v2/pet/123"));

        step("Проверка соответствия ответа схеме JSON");
        ValidatableResponse validation = postResponse.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("petResponseSchema.json"));

        step("Извлечение данных о животном из ответа");
        SuccessUpdatePet updatePets = validation.extract().as(SuccessUpdatePet.class);
    }
    @Order(4)
    @DisplayName("Удаление питомца из мазазина")
    @Description("Отправка запроса на сервер с id питомца, получение ответа и его проверка на соответствие по json схеме и по коду ответа -200")
    @Tag("delete")
    @Test
    public void deletePet(){
        Specifications.installSpec(Specifications.requestSpec(url),Specifications.responseSpec(200));

        step("Отправка DELETE-запроса для удаления данных о животном");
        Response postResponse = given()
                .when()
                .delete(String.format("v2/pet/123"));

        step("Проверка соответствия ответа схеме JSON");
        ValidatableResponse validation = postResponse.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("petResponseSchema.json"));

    }
}
