package StarWars;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static InterFace.EndPoints.People1;
import static InterFace.EndPoints.Schema;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Test
public class StarWars {


    @BeforeTest()
    public void requestSpec() {
        RestAssured.baseURI = "https://swapi.co/api/";
    }


    @Test(description = "Тут может быть описаие теста + здесть мы сравниваем значение в масиве которе является 2 по счету используем  Matchers.is")
   // @Description("здесть мы сравниваем значение в масиве которе является 2 по счету используем  Matchers.is")

    public void StarTestPeople() {

        given()
                .when().get(People1)
                .then()
                .log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Luke Skywalker"))
                .body("films[3]", Matchers.is("https://swapi.co/api/films/1/"));

    }

    @Test
   // @Description("Тут мы учимся создавать мапу и лист")
    public void StarTestschema() {
        StarWars schema = new StarWars();
        given()
                .when().get(Schema)
                .then()
                .log().body()
                .statusCode(200)
                .body("required[0]", Matchers.is("name"))

                .extract().path("required");

      //  List list = (List) given()
        //        .when().get(Schema)
        //        .jsonPath().getList("required");

      //  list.stream().forEach(element -> System.out.println("вот элемент в листе " + element));


        HashMap mapa = (HashMap) given()
                .when().get(Schema)
                .jsonPath().getMap("properties.created");
        System.out.println(mapa);


        given()
                .when().get(Schema)
                .then().assertThat().body("properties.created.description", Matchers.is("The ISO 8601 date format of the time that this resource was created."))
               .log().all()
                .statusCode(200);


    }

    @Test(description = "Извличение значений из тела сообщения в переменную nextParams")
    public void StarTest() {
        String nextParams = // тут мы задаем переменную и присваеваем ей ответ
                given()//если нужно указать параметры то их можно указать сюда
                        .when().get(Schema)
                        .then()
                        .body("description", equalTo("O person within the Star Wars universe")) // здесь мы проверили что описание соответствует описанию)
                        .body("type", equalTo("object"))
                        .log().all()
                        .extract()// извличение значения в нашем случаии по пути , путь представлен нижеgit
                        .path("title");// а вот и путь)
        get(nextParams); // получаем переменную возможно для последующей передачи в параметрах
        System.out.println(nextParams);// ну и печатаем
    }


    @Test(description = "Извличения всего тела ответа в переменные")

    public void StarTest2() {
        Response response =
                given()// тут могут быть параметры
                        .when().get(Schema)
                        .then()
                        .body("$schema", Matchers.is("http://json-schema.org/draft-04/schema"))
                        .log().all()
                        .extract()
                        .response();
        String nextParams = response.path("description");
        String headerValue = response.header("headerName");
        System.out.println();
        System.out.println(nextParams);
        System.out.println(headerValue);
    }
}
