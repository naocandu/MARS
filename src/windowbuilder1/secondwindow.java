/*
 * Class provide a window showing the search result.
 * And user can book flights in this window.
 * @author Hao Liu
 * @since 2016-3-22
 */
package windowbuilder1;
import TripPlanner.TripPlanner;
import Controller.Trip;

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class secondwindow
{
	
	public JFrame frmFlightsResults; 
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public Trip reserve;
	private class MyListCellRenderer extends DefaultListCellRenderer {

	    @Override
	  
	    public Component getListCellRendererComponent(
	            JList list, Object value, int index,
	            boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	        Trip label = (Trip) value;
	        
	        String GetAiportSequence = label.GetAiportSequence();
	        String GetFlightSequence = label.GetFlightSequenceShow();
	        String toString = label.toStringShow();
	        String labelText = "<html> " + GetAiportSequence + "&nbsp;" + "&nbsp;" + "&nbsp;"+ GetFlightSequence + "<br/> " + toString;
	        setText(labelText);

	        return this;
	    }

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
					secondwindow window = new secondwindow();
					window.frmFlightsResults.setVisible(true);
					
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
	public secondwindow()
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		//Trips.LinkFlights("BOS", "AUS", "2016_05_04", true);
		frmFlightsResults = new JFrame();
		frmFlightsResults.setTitle("Flights Results");
		frmFlightsResults.setBounds(0, 0, 650, 440);
		frmFlightsResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFlightsResults.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 614, 304);
		frmFlightsResults.getContentPane().add(scrollPane);

		JList<Trip> list = new JList<Trip>();
		DefaultListModel model = new DefaultListModel();
		int length = TripPlanner.trip1.size();
		for(int i=0; i< length; i++)
		{
			model.addElement(TripPlanner.trip1.get(i));
		}
		list.setModel(model);
        list.setCellRenderer(new MyListCellRenderer());
        scrollPane.setViewportView(list);
		
		JCheckBox chckbxPrice = new JCheckBox("price(cheapest)");
		buttonGroup.add(chckbxPrice);
		chckbxPrice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				TripPlanner.tripsort1 = TripPlanner.SortBy(1,TripPlanner.trip1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for(int i=0; i< length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		chckbxPrice.setBounds(31, 18, 82, 23);
		frmFlightsResults.getContentPane().add(chckbxPrice);
		
		JCheckBox chckbxDuration = new JCheckBox("duration(shortest)");
		buttonGroup.add(chckbxDuration);
		chckbxDuration.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TripPlanner.tripsort1 = TripPlanner.SortBy(2,TripPlanner.trip1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for(int i=0; i< length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
				
			}
		});
		chckbxDuration.setBounds(118, 18, 59, 23);
		frmFlightsResults.getContentPane().add(chckbxDuration);
		
		JCheckBox chckbxDeparting = new JCheckBox("departure(earliest)");
		buttonGroup.add(chckbxDeparting);
		chckbxDeparting.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TripPlanner.trip = TripPlanner.SortBy(3,TripPlanner.trip1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for(int i=0; i< length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		chckbxDeparting.setBounds(179, 18, 50, 23);
		frmFlightsResults.getContentPane().add(chckbxDeparting);
		
		JCheckBox chckbxDeparturelatest = new JCheckBox("departure(latest)");
		chckbxDeparturelatest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TripPlanner.trip = TripPlanner.SortBy(4,TripPlanner.trip1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for(int i=0; i< length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		buttonGroup.add(chckbxDeparturelatest);
		chckbxDeparturelatest.setBounds(247, 18, 103, 23);
		frmFlightsResults.getContentPane().add(chckbxDeparturelatest);
		
		JCheckBox chckbxArrivalearliest = new JCheckBox("arrival(earliest)");
		chckbxArrivalearliest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TripPlanner.trip = TripPlanner.SortBy(5,TripPlanner.trip1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for(int i=0; i< length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		buttonGroup.add(chckbxArrivalearliest);
		chckbxArrivalearliest.setBounds(352, 18, 103, 23);
		frmFlightsResults.getContentPane().add(chckbxArrivalearliest);
		
		JCheckBox chckbxArrivallatest = new JCheckBox("arrival(latest)");
		chckbxArrivallatest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TripPlanner.trip = TripPlanner.SortBy(6,TripPlanner.trip1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for(int i=0; i< length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		buttonGroup.add(chckbxArrivallatest);
		chckbxArrivallatest.setBounds(453, 18, 103, 23);
		frmFlightsResults.getContentPane().add(chckbxArrivallatest);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				frmFlightsResults.dispose();
			}
		});
		btnCancel.setBounds(349, 368, 93, 23);
		frmFlightsResults.getContentPane().add(btnCancel);
		
		JButton btnBook = new JButton("book");
		btnBook.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				long startTime = System.currentTimeMillis(); // get start time
				Object[] options =
				{ "confirm", "cancel" };
				int response = JOptionPane.showOptionDialog(null, "Are you sure to book this flight?", "confirmation",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				
				if (response == 0) // if confirm
				{
					long endTime = System.currentTimeMillis(); // get end time
					long totalTime = (endTime - startTime) / 1000;
					if (totalTime > 5)
					{
						JOptionPane.showMessageDialog(null, "Dialog expires, please restart search.\n window will be closed in 3 seconds after click.");
						try   
						{   
						Thread.currentThread().sleep(3000);// wait 3s
						}   
						catch(Exception e){} 
						frmFlightsResults.dispose();
						
					} else
					{
						reserve = (Trip)list.getSelectedValue();
						//
						/*
						 * else { JOptionPane.showMessageDialog(null,
						 * "book fail, please try again");
						 * frmFlightsResults.dispose(); }
						 */
						JOptionPane.showMessageDialog(null,
								"book successfully" + "\n" + reserve.GetFlightSequence() + "\n" + reserve.toString()+ "\n");
						
					}
				} else if (response == 1)
				{
					JOptionPane.showMessageDialog(null, "book fail");
				}
			}
		});
		btnBook.setBounds(135, 368, 93, 23);
		frmFlightsResults.getContentPane().add(btnBook);
	}
}
