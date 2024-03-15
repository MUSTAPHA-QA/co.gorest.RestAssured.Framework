package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.Endpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import api.payload.User;

public class UpdateUserTest {
	
	Logger logger = LogManager.getLogger(UpdateUserTest.class);
	Faker faker = new Faker();
	
	@Test
	public void updateUser(ITestContext context) {
		
		logger.info("------------------------Starting update user test------------------------");
		
		// Generating new user data
		User userPayload = new User();
		userPayload.setName(faker.name().fullName());
		userPayload.setGender("male");
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setStatus("inactive");
		
		// Retrieving the user ID from the test context
		int userID = (int) context.getSuite().getAttribute("userID");
		
		// Sending the request to update user details
		Response response = Endpoints.updateUser(userID, userPayload);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");
		
		String gender = response.jsonPath().getString("gender");
		String status = response.jsonPath().getString("status");
		logger.info("Extracted Gender: {}", gender);
        logger.info("Extracted Status: {}", status);
		Assert.assertEquals(gender, "male", "Unexpected gender");
		Assert.assertEquals(status, "inactive", "Unexpected status");
		
		logger.info("Content-Type Header: {}", response.getHeader("Content-Type"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8", "Unexpected Content-Type");
		
		// Validating JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("updateUserSchema.json"));
		
		logger.info("Response Body: {}", response.getBody().asString());
		response.then().log().body();
		
		logger.info("------------------------Finished update user test------------------------");
	}
}
