/*
 * Class provide a window showing the one-way search results.
 * And user can book flights in this window.
 * @author Hao Liu
 * @since 2016-3-22
 */
package windowbuilder1;

import TripPlanner.TripPlanner;
import Controller.Trip;
import Controller.ValidationController;

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
import javax.swing.JRadioButton;

public class secondwindow
{
	
	public JFrame frmFlightsResults;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public Trip reserve;
	
	/*
	 * This is a CellRenderer that will change the cell of Jlist. One cell can
	 * display multiple lines.
	 */
	private class MyListCellRenderer extends DefaultListCellRenderer
	{
		
		@Override
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Trip label = (Trip) value;
			
			String GetAiportSequence = label.GetAiportSequence();
			String GetFlightSequence = label.GetFlightSequenceShow();
			String toString = label.toStringShow();
			String labelText = "<html> " + GetAiportSequence + "&nbsp;" + "&nbsp;" + "&nbsp;" + GetFlightSequence
					+ "<br/> " + toString + "<br/> "
					+ "-----------------------------------------------------------------------------------------------------";
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
		frmFlightsResults = new JFrame();
		frmFlightsResults.setResizable(false);
		frmFlightsResults.setTitle("Flights Results");
		frmFlightsResults.setBounds(0, 0, 641, 476);
		frmFlightsResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFlightsResults.getContentPane().setLayout(null);
		
		// add a JScrollPane.
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 78, 595, 316);
		frmFlightsResults.getContentPane().add(scrollPane);
		
		// define the list of results.
		JList<Trip> list = new JList<Trip>();
		DefaultListModel model = new DefaultListModel();
		TripPlanner.triphop1.clear();
		for (int i = 0; i < TripPlanner.trip1.size(); i++)
			TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
		// results are sorted by price in default mode.
		TripPlanner.SortBy(1, TripPlanner.triphop1);
		int length = TripPlanner.triphop1.size();
		for (int i = 0; i < length; i++)
		{
			model.addElement(TripPlanner.triphop1.get(i));
		}
		list.setModel(model);
		list.setCellRenderer(new MyListCellRenderer());
		scrollPane.setViewportView(list);
		
		// define all button.
		JRadioButton rdbtnAll = new JRadioButton("all");
		rdbtnAll.setSelected(true);
		rdbtnAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TripPlanner.triphop1.clear();
				// when button is selected, second line of button is unselected.
				buttonGroup.clearSelection();
				for (int i = 0; i < TripPlanner.trip1.size(); i++)
					TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				model.clear();
				int length = TripPlanner.triphop1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.triphop1.get(i));
				}
			}
		});
		buttonGroup_1.add(rdbtnAll);
		rdbtnAll.setBounds(32, 17, 121, 23);
		frmFlightsResults.getContentPane().add(rdbtnAll);
		
		// define direct button.
		JRadioButton rdbtnDirect = new JRadioButton("direct");
		buttonGroup_1.add(rdbtnDirect);
		rdbtnDirect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				for (int i = 0; i < TripPlanner.trip1.size(); i++)
					TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				TripPlanner.tripfilter1 = TripPlanner.Filter(7, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripfilter1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripfilter1.get(i));
				}
			}
		});
		rdbtnDirect.setBounds(156, 17, 121, 23);
		frmFlightsResults.getContentPane().add(rdbtnDirect);
		
		// define one hop button.
		JRadioButton rdbtnOneHop = new JRadioButton("one hop");
		buttonGroup_1.add(rdbtnOneHop);
		rdbtnOneHop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				for (int i = 0; i < TripPlanner.trip1.size(); i++)
					TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				TripPlanner.tripfilter1 = TripPlanner.Filter(8, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripfilter1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripfilter1.get(i));
				}
			}
		});
		rdbtnOneHop.setBounds(315, 17, 121, 23);
		frmFlightsResults.getContentPane().add(rdbtnOneHop);
		
		// define two hops button.
		JRadioButton rdbtnTwoHops = new JRadioButton("two hops");
		buttonGroup_1.add(rdbtnTwoHops);
		rdbtnTwoHops.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				for (int i = 0; i < TripPlanner.trip1.size(); i++)
					TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				TripPlanner.tripfilter1 = TripPlanner.Filter(9, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripfilter1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripfilter1.get(i));
				}
			}
		});
		rdbtnTwoHops.setBounds(468, 17, 121, 23);
		frmFlightsResults.getContentPane().add(rdbtnTwoHops);
		
		// define price button.
		JCheckBox chckbxPrice = new JCheckBox("price(cheapest)");
		chckbxPrice.setSelected(true);
		buttonGroup.add(chckbxPrice);
		chckbxPrice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				TripPlanner.tripsort1 = TripPlanner.SortBy(1, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		chckbxPrice.setBounds(32, 49, 122, 23);
		frmFlightsResults.getContentPane().add(chckbxPrice);
		
		// define duration button.
		JCheckBox chckbxDuration = new JCheckBox("duration(shortest)");
		buttonGroup.add(chckbxDuration);
		chckbxDuration.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TripPlanner.tripsort1 = TripPlanner.SortBy(2, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
				
			}
		});
		chckbxDuration.setBounds(156, 49, 139, 23);
		frmFlightsResults.getContentPane().add(chckbxDuration);
		
		// define departure button.
		JCheckBox chckbxDeparting = new JCheckBox("departure(earliest)");
		buttonGroup.add(chckbxDeparting);
		chckbxDeparting.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TripPlanner.tripsort1 = TripPlanner.SortBy(3, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		chckbxDeparting.setBounds(315, 49, 139, 23);
		frmFlightsResults.getContentPane().add(chckbxDeparting);
		
		// define arrival button.
		JCheckBox chckbxArrivalearliest = new JCheckBox("arrival(earliest)");
		chckbxArrivalearliest.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				TripPlanner.tripsort1 = TripPlanner.SortBy(4, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripsort1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripsort1.get(i));
				}
			}
		});
		buttonGroup.add(chckbxArrivalearliest);
		chckbxArrivalearliest.setBounds(468, 49, 127, 23);
		frmFlightsResults.getContentPane().add(chckbxArrivalearliest);
		
		// define cancel button. When clicked, the window will be closed.
		JButton btnCancel = new JButton("cancel");
		btnCancel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				frmFlightsResults.dispose();
			}
		});
		btnCancel.setBounds(349, 404, 93, 23);
		frmFlightsResults.getContentPane().add(btnCancel);
		
		// define book button.
		JButton btnBook = new JButton("book");
		btnBook.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// must select a trip
				if ((Trip) list.getSelectedValue() == null)
					JOptionPane.showMessageDialog(null, "Please select a trip");
				// realize the expiration window.
				else
				{
					// get start time
					long startTime = System.currentTimeMillis();
					Object[] options =
					{ "confirm", "cancel" };
					int response = JOptionPane.showOptionDialog(null, "Are you sure to book this flight?" + 
							((Trip) list.getSelectedValue()).confirmationString(),
							"confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					// if confirm
					if (response == 0)
					{
						// get end time
						long endTime = System.currentTimeMillis();
						long totalTime = (endTime - startTime) / 1000;
						// if wait time exceeds 5 seconds, dialog expires.
						if (totalTime > 5)
						{
							JOptionPane.showMessageDialog(null,
									"Dialog expires, please restart search.\n window will be closed in 2 seconds after click.");
							try
							{
								Thread.currentThread().sleep(2000);// wait 2s
							} catch (Exception e)
							{
							}
							ValidationController.Instance().RefreshAll();
							frmFlightsResults.dispose();
							
							
						}
						// if wait time is within 5 seconds.
						else
						{
							// get selected trip.
							reserve = (Trip) list.getSelectedValue();
							// try to book this trip
							if (ValidationController.Instance().ConfirmTrip(reserve) == true)
							{
								// if success, show message.
								JOptionPane.showMessageDialog(null, "book successfully" + "\n"
										+ reserve.GetFlightSequence() + "\n" + reserve.toString() + "\n");
								ValidationController.Instance().RefreshAll();
								frmFlightsResults.dispose();
							} else
							{
								// if fail, show message
								JOptionPane.showMessageDialog(null, "book fail \n"+ ValidationController.Instance().GetLastErrorMessage());
							}
						}
					}
					// if cancelled
					else if (response == 1)
					{
						JOptionPane.showMessageDialog(null, "cancelled");
					}
				}
			}
		});
		btnBook.setBounds(134, 404, 93, 23);
		frmFlightsResults.getContentPane().add(btnBook);
	}
}
