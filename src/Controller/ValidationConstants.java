package Controller;

import java.util.HashMap;
import java.util.Map;

public class ValidationConstants {
	public static final String CONFIG_DIRECTORY = "resources//config.txt";
	
	public static final int MIN_LAYOVER_MINUTES = 15;
	public static final int MAX_LAYOVER_MINUTES = 60;
	public static final int MAX_HOPS = 2;
	public static final boolean SAFE_SEARCH = false;
	
	public static final String[] DEFAULT_LABEL = {
			"res_system_url",
			"timezone_url",
			"team_name",
			"min_layover_minutes",
			"max_layover_minutes",
			"max_hops",
			"timeout_milliseconds",
			"safe_search",
			};
	
	public static final String TIMEZONE_CACHE_DIRECTORY = "resources//timezone.txt";
	public static final String AIRPLANE_CACHE_DIRECTORY = "resources//airplane.txt";
	
	public static Map<Integer,String> RESPONSE_MESSAGE = new HashMap<Integer,String>();
	
	static
	{
		RESPONSE_MESSAGE.put(200, "OK");
		RESPONSE_MESSAGE.put(304, "Not Modified");
		RESPONSE_MESSAGE.put(400, "Bad Request");
		RESPONSE_MESSAGE.put(401, "Unauthorized");
		RESPONSE_MESSAGE.put(403, "Forbidden");
		RESPONSE_MESSAGE.put(404, "Not Found");
		RESPONSE_MESSAGE.put(408, "Request Timeout");
		RESPONSE_MESSAGE.put(409, "Conflict");
		RESPONSE_MESSAGE.put(421, "Misdirected Request");
		RESPONSE_MESSAGE.put(600, "Internal Error");
		RESPONSE_MESSAGE.put(500, "Internal Server Error");
		RESPONSE_MESSAGE.put(502, "Bad Gateway");
		RESPONSE_MESSAGE.put(503, "Service Unavailable");
	}
}
