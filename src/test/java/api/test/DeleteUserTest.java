package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import api.endpoints.Endpoints;
import io.restassured.response.Response;

public class DeleteUserTest {
	
	Logger logger = LogManager.getLogger(DeleteUserTest.class);
	
	@Test
	public void deleteUser(ITestContext context) {
		
		logger.info("------------------------Starting delete user test------------------------");
		
		// Retrieving the user ID from the test context
		int userID = (int) context.getSuite().getAttribute("userID");
		
		// Sending the request to delete the user
		Response response = Endpoints.deleteUser(userID);
		
		// Validations
		logger.info("Response Status Code: {}", response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 204, "Unexpected status code");
		
		logger.info("Value of x-content-type-options Header: {}", response.getHeader("x-content-type-options"));
		Assert.assertEquals(response.getHeader("x-content-type-options"), "nosniff", "Unexpected x-content-type-options header value");
		
		logger.info("------------------------Finished delete user test------------------------");
	}
}
