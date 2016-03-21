package Controller;

public class ValidationConstants {
	public static final String CONFIG_DIRECTORY = "resources//config.txt";
	
	public static final int MIN_LAYOVER_MINUTES = 15;
	public static final int MAX_LAYOVER_MINUTES = 60;
	public static final int MAX_HOPS = 2;
	
	public static final String[] DEFAULT_LABEL = {
			"res_system_url",
			"timezone_url",
			"team_name",
			"min_layover_minutes",
			"max_layover_minutes",
			"max_hops",
			"timeout_milliseconds"
			};
}
