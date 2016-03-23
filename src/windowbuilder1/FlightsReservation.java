/*
 * Class provide a visible window for searching flights.
 * @author Hao Liu
 * @since 2016-3-22
 */

package windowbuilder1;
import TripPlanner.TripPlanner;
import XMLparser.parseAirports;
import AirFlight.Airports;
import org.dom4j.DocumentException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JToolBar;
import javax.swing.JSpinner;
import javax.swing.AbstractListModel;
import java.time.DayOfWeek;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class FlightsReservation
{
	
	private JFrame frmFlightsReservation;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public String triptype;
	public String seatclass;
	private String flyfrom;
	private String flyto;
	private String departdate;
	private String returndate;
	private int year1;
	private int month1;
	private int day1;
	private int year2;
	private int month2;
	private int day2;
	private int todayyear;
	private int todaymonth;
	private int todaydate;

	public String getDeparture() // get departure airport
	{
		return flyfrom;
	}
	
	public String getArrival() // get arrival airport
	{
		return flyto;
	}
	
	public String getDepartureDate() // get departure date
	{ // get departure date
		return departdate;
	}
	
	public String getReturnDate() // get return date
	{
		return returndate;
	}
	
	public String getTripType() // get trip type
	{
		return triptype;
	}
	
	public String getSeatClass() // get seat class
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
		
		// one way
		JRadioButton rdbtnOneway = new JRadioButton("one-way");
		rdbtnOneway.setSelected(true);
		rdbtnOneway.setBackground(new Color(240, 240, 240));
		rdbtnOneway.setFont(new Font("Cambria", Font.BOLD, 15));
		buttonGroup.add(rdbtnOneway);
		rdbtnOneway.setBounds(10, 18, 93, 23);
		frmFlightsReservation.getContentPane().add(rdbtnOneway);
		
		// round
		JRadioButton rdbtnRoundTrip = new JRadioButton("round trip");
		rdbtnRoundTrip.setFont(new Font("Cambria", Font.BOLD, 15));
		buttonGroup.add(rdbtnRoundTrip);
		rdbtnRoundTrip.setBounds(127, 18, 99, 23);
		frmFlightsReservation.getContentPane().add(rdbtnRoundTrip);
		
		// seat class
		JCheckBox rdbtnEconomy = new JCheckBox("economy");
		rdbtnEconomy.setSelected(true);
		buttonGroup_1.add(rdbtnEconomy);
		rdbtnEconomy.setFont(new Font("Cambria", Font.BOLD, 15));
		rdbtnEconomy.setBounds(10, 57, 93, 23);
		frmFlightsReservation.getContentPane().add(rdbtnEconomy);
		
		// seat class
		JCheckBox rdbtnFirstClass = new JCheckBox("first class");
		buttonGroup_1.add(rdbtnFirstClass);
		rdbtnFirstClass.setFont(new Font("Cambria", Font.BOLD, 15));
		rdbtnFirstClass.setBounds(127, 57, 99, 23);
		frmFlightsReservation.getContentPane().add(rdbtnFirstClass);
		
		JLabel lblFlyingFrom = new JLabel("Flying from");
		lblFlyingFrom.setFont(new Font("Cambria", Font.BOLD, 20));
		lblFlyingFrom.setBounds(10, 115, 114, 30);
		frmFlightsReservation.getContentPane().add(lblFlyingFrom);
		
		// depart airport
		JComboBox departair = new JComboBox();	
		departair.setBounds(127, 115, 254, 30);
		departair.setFont(new Font("Cambria", Font.BOLD, 12));
		departair.setEditable(true);
		//get airport from sever
		try
		{
			departair.setModel(new DefaultComboBoxModel(new Airports().getAirportName()));
		} catch (DocumentException e3)
		{
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} 	// sever;
		frmFlightsReservation.getContentPane().add(departair);
		
		JLabel lblFlyingTo = new JLabel("Flying to");
		lblFlyingTo.setFont(new Font("Cambria", Font.BOLD, 20));
		lblFlyingTo.setBounds(10, 172, 89, 30);
		frmFlightsReservation.getContentPane().add(lblFlyingTo);
		
		// arrival airport
		JComboBox arriveair = new JComboBox();
		arriveair.setFont(new Font("Cambria", Font.BOLD, 12));
		arriveair.setEditable(true);
		try
		{
			arriveair.setModel(new DefaultComboBoxModel(new Airports().getAirportName()));
		} catch (DocumentException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		arriveair.setBounds(127, 172, 254, 30);
		frmFlightsReservation.getContentPane().add(arriveair);
		
		JLabel lblDeparting = new JLabel("Departing");
		lblDeparting.setFont(new Font("Cambria", Font.BOLD, 20));
		lblDeparting.setBounds(10, 232, 114, 30);
		frmFlightsReservation.getContentPane().add(lblDeparting);
		
		JPanel DepartDatePanel = new JPanel();
		DepartDatePanel.setBounds(150, 243, 117, 30);
		frmFlightsReservation.getContentPane().add(DepartDatePanel);
		
		DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
		DepartDatePanel.setLayout(null);
		JLabel DepartDate1 = new JLabel("click to get date");
		DepartDate1.setBounds(0, 0, 117, 30);
		dateChooser1.register(DepartDate1);
		DepartDatePanel.add(DepartDate1);
		DepartDatePanel.setVisible(true);
		// get today date
		todayyear = dateChooser1.getDate().getYear();
		todaymonth = dateChooser1.getDate().getMonth();
		todaydate = dateChooser1.getDate().getDate();
		
		JLabel lblReturning = new JLabel("Returning");
		lblReturning.setFont(new Font("Cambria", Font.BOLD, 20));
		lblReturning.setBounds(10, 296, 99, 23);
		frmFlightsReservation.getContentPane().add(lblReturning);
		
		JPanel ReturnDatePanel = new JPanel();
		ReturnDatePanel.setBounds(150, 299, 114, 30);
		frmFlightsReservation.getContentPane().add(ReturnDatePanel);
		ReturnDatePanel.setLayout(null);
		
		DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");
		ReturnDatePanel.setLayout(null);
		JLabel ReturnDate1 = new JLabel("click to get date");
		ReturnDate1.setBounds(0, 0, 114, 30);
		dateChooser2.register(ReturnDate1);
		ReturnDatePanel.add(ReturnDate1);
		ReturnDatePanel.setVisible(true);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Cambria", Font.BOLD, 14));
		textArea.setBounds(392, 192, 252, 215);
		frmFlightsReservation.getContentPane().add(textArea);
		
		JButton btnSearch = new JButton("search");
		btnSearch.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 18));
		btnSearch.setBounds(74, 349, 93, 23);
		frmFlightsReservation.getContentPane().add(btnSearch);
		//when click search button, will perform actions below.
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				String a = (String) departair.getSelectedItem();	
				String b = (String) arriveair.getSelectedItem();
				String c = dateChooser1.getStrDate();
				String d = dateChooser2.getStrDate();
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
				year2 = new getselectdate().mydate(d)[0];
				month2 = new getselectdate().mydate(d)[1];
				day2 = new getselectdate().mydate(d)[2];
				
				if (a.equals("") || b.equals(""))	//must select airport
					JOptionPane.showMessageDialog(null, "please select airports");
				
				if (!a.equals("") && !b.equals(""))	
				{
					if (a.equals(b) == true)	//cannot select same airport
					{
						JOptionPane.showMessageDialog(null, "cannot select same airports");
					}
					if (a.equals(b) == false)
					{
						seatclass = (g == true ? g1 : h1);	//select seat class
						flyfrom = a;
						flyto = b;
						departdate = c;
						if (e == true) // one-way
						{
							triptype = e1;
							returndate = null;
							userinfo = triptype + "\n" + seatclass + "\n" + flyfrom + "\n" + flyto + "\n" + departdate
									+ "\n" + returndate + "\n";
						} else // round trip
						{
							triptype = f1;
							if (year1 >= todayyear && month1 >= todaymonth && day1 >= todaydate) //date must be correct
							{
								if (year2 > year1)
								{
									returndate = d;
									userinfo = triptype + "\n" + seatclass + "\n" + flyfrom + "\n" + flyto + "\n"
											+ departdate + "\n" + returndate + "\n";
									textArea.setText(userinfo);
								} else if (year2 == year1)
								{
									if (month2 > month1)
									{
										returndate = d;
										userinfo = triptype + "\n" + seatclass + "\n" + flyfrom + "\n" + flyto + "\n"
												+ departdate + "\n" + returndate + "\n";
										textArea.setText(userinfo);
									} else if (month2 == month1)
									{
										if (day2 >= day1)
										{
											returndate = d;
											userinfo = triptype + "\n" + seatclass + "\n" + flyfrom + "\n" + flyto
													+ "\n" + departdate + "\n" + returndate + "\n";
											textArea.setText(userinfo);
											TripPlanner.SearchFlights();
										} else
											JOptionPane.showMessageDialog(null, "Return date is wrong!");
									} else
										JOptionPane.showMessageDialog(null, "Return date is wrong!");
								} else
									JOptionPane.showMessageDialog(null, "Return date is wrong!");
							} else
								JOptionPane.showMessageDialog(null, "Depart date is wrong!");
						}
						
						// new
						// secondwindow().frmFlightsResults.setVisible(true);
					}
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("New label");		//background picture
		lblNewLabel.setIcon(new ImageIcon(FlightsReservation.class.getResource("/images/aeroplane.jpg")));
		lblNewLabel.setBounds(0, 0, 644, 407);
		frmFlightsReservation.getContentPane().add(lblNewLabel);
	}
}
