package api.endpoints;

import static io.restassured.RestAssured.given;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class Endpoints {
	
	public static String bearerToken = "ee19944eacfe2d30c7837cbd59233afa049e367167504779f8d63b3a4b91c566";
	
	public static Response createUser(User userPayload) {
		
		Response response =
		
		given()
			.headers("Authorization", "Bearer " + bearerToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(userPayload)
		
		.when()
			.post(Routes.post_url);
		
		return response;
	}
	
	public static Response getUser(int userID) {
		
		Response response =
		
		given()
			.headers("Authorization", "Bearer " + bearerToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("userID", userID)
		
		.when()
			.get(Routes.get_url);
		
		return response;
	}
	
	public static Response updateUser(int userID, User userPayload){
		
		Response response =
		
		given()
			.headers("Authorization", "Bearer " + bearerToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("userID", userID)
			.body(userPayload)
		
		.when()
			.put(Routes.update_url);
		
		return response;
	}
	
	public static Response deleteUser(int userID) {
		
		Response response =
		
		given()
			.headers("Authorization", "Bearer " + bearerToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("userID", userID)
		
		.when()
			.delete(Routes.delete_url);
		
		return response;
	}
}
