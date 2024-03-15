package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.Endpoints;
import api.payload.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CreateUserTest {
	
	User userPayload;
	Logger logger = LogManager.getLogger(this.getClass());	
	
	@BeforeClass
	public void setup() {
		
		Faker faker = new Faker();
		
		// Generating user data
		userPayload = new User();
		userPayload.setName(faker.name().fullName());
		userPayload.setGender("male");
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setStatus("active");
	}
	
	@Test
	public void createUser(ITestContext context) {
		
		logger.info("------------------------Starting create user test------------------------");
		
		// Sending the request to create a user
		Response response = Endpoints.createUser(userPayload);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 201, "Unexpected status code");
		
		String gender = response.jsonPath().getString("gender");
		String status = response.jsonPath().getString("status");
		logger.info("Extracted Gender: {}", gender);
	    logger.info("Extracted Status: {}", status);
		Assert.assertEquals(gender, "male", "Unexpected gender");
		Assert.assertEquals(status, "active", "Unexpected status");
		
		logger.info("Content-Type Header: {}", response.getHeader("Content-Type"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8", "Unexpected Content-Type");
		
		// Validating JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createUserSchema.json"));
		
		logger.info("Response Body: {}", response.getBody().asString());
		response.then().log().body();
		
		// Extract, log and store the user ID in test context for future use
		int userID = response.jsonPath().getInt("id");
		logger.info("User ID: {}", userID);
		context.getSuite().setAttribute("userID", userID);
		
		logger.info("------------------------Finished create user test------------------------");
	}
}
