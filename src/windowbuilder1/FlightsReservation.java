/*
 * Class provide a visible window for searching flights.
 * @author Hao Liu
 * @since 2016-3-22
 */

package windowbuilder1;

import TripPlanner.TripPlanner;
import AirFlight.Airports;
import Controller.ValidationController;

import org.dom4j.DocumentException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class FlightsReservation
{
	
	public JFrame frmFlightsReservation;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	// define user information
	public static String triptype;
	public static String seatclass;
	private static String flyfrom;
	private static String flyto;
	private static String departdate;
	private static String returndate;
	// define date of int-type, it is used to compare with each other to judge
	// the correct date.
	private static int year1;
	private static int month1;
	private static int day1;
	private static int year2;
	private static int month2;
	private static int day2;
	// define the date of today.
	private static Calendar now = Calendar.getInstance();
	private static int todayyear = now.get(Calendar.YEAR);
	private static int todaymonth = now.get(Calendar.MONTH) + 1;
	private static int todaydate = now.get(Calendar.DAY_OF_MONTH);
	// define DateChooser2 for return part. In order to realize the dynamic
	// display of round trip.
	DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");
	JPanel ReturnDatePanel = new JPanel();
	JLabel lblReturning = new JLabel("Returning");
	JLabel ReturnDate1 = new JLabel("click to get date");
	JLabel lblNewLabel = new JLabel("New label");
	
	// get departure airport
	public static String getDeparture()
	{
		return flyfrom;
	}
	
	// get arrival airport
	public static String getArrival()
	{
		return flyto;
	}
	
	// get departure date
	public static String getDepartureDate()
	{
		return departdate;
	}
	
	// get return date
	public static String getReturnDate()
	{
		return returndate;
	}
	
	// get trip type
	public static String getTripType()
	{
		return triptype;
	}
	
	// get seat class
	public static String getSeatClass()
	{
		return seatclass;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					FlightsReservation window = new FlightsReservation();
					window.frmFlightsReservation.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public FlightsReservation()
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmFlightsReservation = new JFrame();
		frmFlightsReservation.setResizable(false);
		frmFlightsReservation.setTitle("Flights Reservation");
		frmFlightsReservation.setBackground(Color.WHITE);
		frmFlightsReservation.setBounds(0, 0, 650, 440);
		frmFlightsReservation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFlightsReservation.getContentPane().setLayout(null);
		
		// define one-way button, and realize dynamic display of one-way and
		// round trip.
		JRadioButton rdbtnOneway = new JRadioButton("one-way");
		rdbtnOneway.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// repaint background
				lblNewLabel.setIcon(new ImageIcon(FlightsReservation.class.getResource("/images/aeroplane.jpg")));
				lblNewLabel.setBounds(0, 0, 644, 407);
				frmFlightsReservation.getContentPane().add(lblNewLabel);
				// remove label "click to get date".
				ReturnDatePanel.remove(ReturnDate1);
				ReturnDatePanel.validate();
				ReturnDatePanel.repaint();
				// remove label "return" and ReturnDatePanel.
				frmFlightsReservation.getContentPane().remove(lblReturning);
				frmFlightsReservation.getContentPane().remove(ReturnDatePanel);
				frmFlightsReservation.getContentPane().validate();
				frmFlightsReservation.getContentPane().repaint();
				
			}
		});
		rdbtnOneway.setSelected(true);
		rdbtnOneway.setBackground(new Color(240, 240, 240));
		rdbtnOneway.setFont(new Font("Cambria", Font.BOLD, 15));
		buttonGroup.add(rdbtnOneway);
		rdbtnOneway.setBounds(10, 18, 93, 23);
		frmFlightsReservation.getContentPane().add(rdbtnOneway);
		
		// define round trip button.
		JRadioButton rdbtnRoundTrip = new JRadioButton("round trip");
		rdbtnRoundTrip.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// add label "return".
				lblReturning.setFont(new Font("Cambria", Font.BOLD, 20));
				lblReturning.setBounds(10, 296, 99, 23);
				lblReturning.setVisible(true);
				frmFlightsReservation.getContentPane().add(lblReturning);
				// add ReturnDatePanel.
				ReturnDatePanel.setBounds(150, 296, 114, 30);
				frmFlightsReservation.getContentPane().add(ReturnDatePanel);
				ReturnDatePanel.setLayout(null);
				// add label "click to get date" and dateChooser2.
				ReturnDate1.setBounds(0, 0, 114, 30);
				dateChooser2.register(ReturnDate1);
				ReturnDatePanel.add(ReturnDate1);
				ReturnDatePanel.setVisible(true);
				ReturnDatePanel.validate();
				ReturnDatePanel.repaint();
				// add background and repaint.
				lblNewLabel.setIcon(new ImageIcon(FlightsReservation.class.getResource("/images/aeroplane.jpg")));
				lblNewLabel.setBounds(0, 0, 644, 407);
				frmFlightsReservation.getContentPane().add(lblNewLabel);
				frmFlightsReservation.getContentPane().validate();
				frmFlightsReservation.getContentPane().repaint();
				
			}
		});
		rdbtnRoundTrip.setFont(new Font("Cambria", Font.BOLD, 15));
		buttonGroup.add(rdbtnRoundTrip);
		rdbtnRoundTrip.setBounds(127, 18, 99, 23);
		frmFlightsReservation.getContentPane().add(rdbtnRoundTrip);
		
		// define seat class button
		JCheckBox rdbtnEconomy = new JCheckBox("economy");
		rdbtnEconomy.setSelected(true);
		buttonGroup_1.add(rdbtnEconomy);
		rdbtnEconomy.setFont(new Font("Cambria", Font.BOLD, 15));
		rdbtnEconomy.setBounds(10, 57, 93, 23);
		frmFlightsReservation.getContentPane().add(rdbtnEconomy);
		
		JCheckBox rdbtnFirstClass = new JCheckBox("first class");
		buttonGroup_1.add(rdbtnFirstClass);
		rdbtnFirstClass.setFont(new Font("Cambria", Font.BOLD, 15));
		rdbtnFirstClass.setBounds(127, 57, 99, 23);
		frmFlightsReservation.getContentPane().add(rdbtnFirstClass);
		
		// add airport label.
		JLabel lblFlyingFrom = new JLabel("Flying from");
		lblFlyingFrom.setFont(new Font("Cambria", Font.BOLD, 20));
		lblFlyingFrom.setBounds(10, 115, 114, 30);
		frmFlightsReservation.getContentPane().add(lblFlyingFrom);
		
		// define depart airport
		JComboBox departair = new JComboBox();
		// get current available airports.
		try
		{
			departair.setModel(new DefaultComboBoxModel(new Airports().getAirportInfo()));
		} catch (DocumentException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		departair.setBounds(127, 115, 278, 30);
		departair.setFont(new Font("Cambria", Font.BOLD, 12));
		departair.setEditable(true);
		frmFlightsReservation.getContentPane().add(departair);
		
		// add airports label
		JLabel lblFlyingTo = new JLabel("Flying to");
		lblFlyingTo.setFont(new Font("Cambria", Font.BOLD, 20));
		lblFlyingTo.setBounds(10, 172, 89, 30);
		frmFlightsReservation.getContentPane().add(lblFlyingTo);
		
		// define arrival airports
		JComboBox arriveair = new JComboBox();
		arriveair.setFont(new Font("Cambria", Font.BOLD, 12));
		arriveair.setEditable(true);
		// get current available airports.
		try
		{
			arriveair.setModel(new DefaultComboBoxModel(new Airports().getAirportInfo()));
		} catch (DocumentException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		arriveair.setBounds(127, 172, 278, 30);
		frmFlightsReservation.getContentPane().add(arriveair);
		
		// add depart date label
		JLabel lblDeparting = new JLabel("Departing");
		lblDeparting.setFont(new Font("Cambria", Font.BOLD, 20));
		lblDeparting.setBounds(10, 232, 114, 30);
		frmFlightsReservation.getContentPane().add(lblDeparting);
		
		// add a small panel.
		JPanel DepartDatePanel = new JPanel();
		DepartDatePanel.setBounds(150, 232, 117, 30);
		frmFlightsReservation.getContentPane().add(DepartDatePanel);
		
		// define depart date.
		DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
		DepartDatePanel.setLayout(null);
		JLabel DepartDate1 = new JLabel("click to get date");
		DepartDate1.setBounds(0, 0, 117, 30);
		dateChooser1.register(DepartDate1);
		DepartDatePanel.add(DepartDate1);
		DepartDatePanel.setVisible(true);
		/*
		 * JLabel lblReturning = new JLabel("Returning");
		 * lblReturning.setFont(new Font("Cambria", Font.BOLD, 20));
		 * lblReturning.setBounds(10, 296, 99, 23);
		 * frmFlightsReservation.getContentPane().add(lblReturning);
		 * 
		 * JPanel ReturnDatePanel = new JPanel(); ReturnDatePanel.setBounds(150,
		 * 296, 114, 30);
		 * frmFlightsReservation.getContentPane().add(ReturnDatePanel);
		 * ReturnDatePanel.setLayout(null);
		 * 
		 * DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");
		 * ReturnDatePanel.setLayout(null); JLabel ReturnDate1 = new JLabel(
		 * "click to get date"); ReturnDate1.setBounds(0, 0, 114, 30);
		 * dateChooser2.register(ReturnDate1); ReturnDatePanel.add(ReturnDate1);
		 * ReturnDatePanel.setVisible(true);
		 */
		
		// add a window to display user info.
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Cambria", Font.BOLD, 14));
		textArea.setBounds(392, 212, 252, 195);
		frmFlightsReservation.getContentPane().add(textArea);
		
		// define search button.
		JButton btnSearch = new JButton("search");
		btnSearch.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
		btnSearch.setBounds(74, 349, 93, 23);
		frmFlightsReservation.getContentPane().add(btnSearch);
		// when click search button, will perform actions below.
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// define strings that will get data from buttons.
				String a = (String) departair.getSelectedItem();
				String b = (String) arriveair.getSelectedItem();
				String c = dateChooser1.getStrDate();
				boolean e = rdbtnOneway.isSelected();
				boolean f = rdbtnRoundTrip.isSelected();
				boolean g = rdbtnEconomy.isSelected();
				boolean h = rdbtnFirstClass.isSelected();
				String e1 = "one-way";
				String f1 = "round trip";
				String g1 = "economy";
				String h1 = "first class";
				String userinfo = null;
				// get date from date string
				year1 = new getselectdate().mydate(c)[0];
				month1 = new getselectdate().mydate(c)[1];
				day1 = new getselectdate().mydate(c)[2];
				// cannot select same airport
				if (a.equals(b) == true)
				{
					JOptionPane.showMessageDialog(null, "cannot select same airports");
				}
				if (a.equals(b) == false)
				{
					// select seat class
					seatclass = (g == true ? g1 : h1);
					flyfrom = a;
					flyto = b;
					// change the format of date
					String month11 = getselectdate.strmydate(c)[1];
					month11 = (month11.length() == 1 ? "0" : "") + month11;
					String day11 = getselectdate.strmydate(c)[2];
					day11 = (day11.length() == 1 ? "0" : "") + day11;
					// now depart date is yyyy_mm_dd
					departdate = year1 + "_" + month11 + "_" + day11;
					// date must be correct
					if (dateChooser1.isSet && (year1 > todayyear || year1 == todayyear && month1 > todaymonth
							|| year1 == todayyear && month1 == todaymonth && day1 >= todaydate))
					{
						// if it is one-way
						if (e == true)
						{
							triptype = e1;
							returndate = null;
							userinfo = triptype + "\n" + seatclass + "\n" + flyfrom + "\n" + flyto + "\n" + departdate
									+ "\n" + returndate + "\n";
							textArea.setText(userinfo);
							TripPlanner.SearchFlights(h);
						}
						// if it is round trip
						else
						{
							triptype = f1; 
							String d = dateChooser2.getStrDate();
							year2 = new getselectdate().mydate(d)[0];
							month2 = new getselectdate().mydate(d)[1];
							day2 = new getselectdate().mydate(d)[2];
							
							String month22 = getselectdate.strmydate(d)[1];
							month22 = (month22.length() == 1 ? "0" : "") + month22;
							String day22 = getselectdate.strmydate(d)[2];
							day22 = (day22.length() == 1 ? "0" : "") + day22;
							// now return date is yyyy_mm_dd
							returndate = year2 + "_" + month22 + "_" + day22;
							
							// return date must be correct
							if (year2 > year1 || year2 == year2 && month2 > month1
									|| year2 == year1 && month2 == month1 && day2 >= day1)
							{
								userinfo = triptype + "\n" + seatclass + "\n" + flyfrom + "\n" + flyto + "\n"
										+ departdate + "\n" + returndate + "\n";
								textArea.setText(userinfo);
								TripPlanner.SearchFlights2(h);
							} else
								JOptionPane.showMessageDialog(null, "Return date is wrong!");
							
						}
					} else
						JOptionPane.showMessageDialog(null, "Depart date is wrong!");
				}
			}
			
		});
		
		// JLabel lblNewLabel = new JLabel("New label"); //background picture
		lblNewLabel.setIcon(new ImageIcon(FlightsReservation.class.getResource("/images/aeroplane.jpg")));
		lblNewLabel.setBounds(0, 0, 644, 407);
		frmFlightsReservation.getContentPane().add(lblNewLabel);
		
	}
}
