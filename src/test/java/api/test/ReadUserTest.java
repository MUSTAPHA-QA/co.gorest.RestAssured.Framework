package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import api.endpoints.Endpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ReadUserTest {
	
	Logger logger = LogManager.getLogger(ReadUserTest.class);

	@Test
	public void readUser(ITestContext context) {
		
		logger.info("------------------------Starting read user test------------------------");
		
		// Retrieving the user ID from the test context
		int userID = (int) context.getSuite().getAttribute("userID");
		
		// Sending the request to get user details
		Response response = Endpoints.getUser(userID);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");
		
		String gender = response.jsonPath().getString("gender");
		String status = response.jsonPath().getString("status");
		logger.info("Extracted Gender: {}", gender);
	    logger.info("Extracted Status: {}", status);
		Assert.assertEquals(gender, "male", "Unexpected gender");
		Assert.assertEquals(status, "active", "Unexpected status code");
		
		logger.info("Content-Type Header: {}", response.getHeader("Content-Type"));
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8", "Unexpected Content-Type");
		
		// Validating JSON schema
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("readUserSchema.json"));
		
		logger.info("Response Body: {}", response.getBody().asString());
		response.then().log().body();
		
		logger.info("------------------------Finished read user test------------------------");
	}
}
