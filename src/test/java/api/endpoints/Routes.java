package api.endpoints;

public class Routes {
	
	public static String base_uri = "https://gorest.co.in/public/v2";
	
	public static String post_url = base_uri + "/users";
	public static String get_url = base_uri + "/users/{userID}";
	public static String update_url = base_uri + "/users/{userID}";
	public static String delete_url = base_uri + "/users/{userID}";
}
