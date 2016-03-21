package Server;

/*************************************************************************
 * Class provides constant variables utilized by the ServerInterface
 * 
 * @author Anthony Botelho 
 * @since 2016-03-13
 */
public class ServerConstants {
	/**
	 * Address of the airline reservation database
	 */
	public static final String RESERVATION_URL = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
	
	/**
	 * Name of the team for accessing the database.
	 * <p>
	 * Also acts as the username for accessing the timezone database.
	 */
	public static final String TEAM_ID = "Team02";
	
	/**
	 * Address of the timezone database
	 */
	public static final String TIMEZONE_URL = "http://api.geonames.org/timezone";
	
	/**
	 * The millisecond timeout period for each server connection
	 */
	public static final int TIMEOUT_MILLISECONDS = 120000;
	
	
}
