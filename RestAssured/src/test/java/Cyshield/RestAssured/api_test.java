package Cyshield.RestAssured;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class api_test {
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in/";
	}

	@Test
	public void testGetRequest() {
		Response response = given().auth().none().header("Accept", "*/*")
				.when()
				.get("/api/users?page2")
				.then()
				.extract().response();
		try {
			int statusCode = response.getStatusCode();
			assertEquals(statusCode, 200, "Expected status code 200 but found " + statusCode);
			int expectedNumberOfObjects = 6;
			int actualNumberOfObjects = response.jsonPath().getList("data").size();
			Assert.assertEquals(actualNumberOfObjects, expectedNumberOfObjects,
					"Expected " + expectedNumberOfObjects + " objects but found " + actualNumberOfObjects);
			System.out.println("Response Body: " + response.getBody().asString());
		} catch (AssertionError e) {
			System.out.println("Unexpected status code");
			throw e;
		}
	}

	@Test
	public void testPostRequest() {
		Map<String, String> data = new HashMap<>();
		data.put("name", "asmaa");
		data.put("job", "Software Engineer");
		String jsonBody = new Gson().toJson(data);
		Response response = given()
				.contentType(ContentType.JSON)
				.body(jsonBody)
				.when()
				.post("/api/users")
				.then()
				.extract().response();
		try {
			int statusCode = response.getStatusCode();
			assertEquals(statusCode, 201, "Expected status code 201 but found " + statusCode);
			System.out.println("Response Body: " + response.getBody().asString());
			response.then()
			.body("name", equalTo("asmaa"))
			.body("job", equalTo("Software Engineer"));
		} catch (AssertionError e) {
			System.out.println("Unexpected status code");
			throw e;
		}
	}

	@Test
	public void testUpdateRequest() {
		Map<String, String> data = new HashMap<>();
		data.put("name", "Ahmed");
		data.put("job", "Software Test Engineer");
		String updateRequestBody = new Gson().toJson(data);
		Response response = given()
				.contentType(ContentType.JSON)
				.body(updateRequestBody)
				.when()
				.put("/api/users/2")
				.then()
				.statusCode(200)
				.extract().response();
		try {
			System.out.println("Update Response Body: " + response.getBody().asString());
			int statusCode = response.getStatusCode();
			assertEquals(statusCode, 200, "Expected status code 200 but found " + statusCode);
			response.then()
			.body("name", equalTo("Ahmed"))
			.body("job", equalTo("Software Test Engineer"));
		} catch (AssertionError e) {
			System.out.println("Unexpected status code");
			throw e;
		}
	}
}
