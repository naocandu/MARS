package windowbuilder1;

/**
 * Class provide a window showing the round trip search results.
 * And user can book flights in this window.
 * 
 * @author Hao Liu
 * @since 2016-3-22
 */
import Controller.Trip;
import Controller.Trips;
import Controller.ValidationController;
import TripPlanner.TripPlanner;

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class thirdwindow
{
	
	public JFrame frmRoundtrip;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	/**
	 * define the trip that will be used as booked trip.
	 */
	public static Trip reserve1;
	public static Trip reserve2;
	public static Trip reserve3;
	
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
					thirdwindow window = new thirdwindow();
					window.frmRoundtrip.setVisible(true);
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
	public thirdwindow()
	{
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmRoundtrip = new JFrame();
		frmRoundtrip.setResizable(false);
		frmRoundtrip.setTitle("roundtrip");
		frmRoundtrip.setBounds(100, 100, 1078, 628);
		frmRoundtrip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRoundtrip.getContentPane().setLayout(null);
		
		// add SCrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 83, 489, 435);
		frmRoundtrip.getContentPane().add(scrollPane);
		
		/**
		 * define depart trip list, sorted by price.
		 */
		JList<Trip> list = new JList<Trip>();
		DefaultListModel model = new DefaultListModel();
		TripPlanner.triphop1.clear();
		for (int i = 0; i < TripPlanner.trip1.size(); i++)
			TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
		TripPlanner.SortBy(1, TripPlanner.triphop1);
		int length = TripPlanner.triphop1.size();
		for (int i = 0; i < length; i++)
		{
			model.addElement(TripPlanner.triphop1.get(i));
		}
		list.setModel(model);
		list.setCellRenderer(new MyListCellRenderer());
		scrollPane.setViewportView(list);
		
		// add SCrollPane
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(564, 83, 467, 435);
		frmRoundtrip.getContentPane().add(scrollPane2);
		
		/**
		 * define return list, sorted by price
		 */
		JList<Trip> list2 = new JList<Trip>();
		DefaultListModel model2 = new DefaultListModel();
		TripPlanner.triphop2.clear();
		for (int i = 0; i < TripPlanner.trip2.size(); i++)
			TripPlanner.triphop2.add(TripPlanner.trip2.get(i));
		TripPlanner.SortBy(1, TripPlanner.triphop2);
		int length2 = TripPlanner.triphop2.size();
		for (int m = 0; m < length2; m++)
		{
			model2.addElement(TripPlanner.triphop2.get(m));
		}
		list2.setModel(model2);
		list2.setCellRenderer(new MyListCellRenderer());
		scrollPane2.setViewportView(list2);
		
		/**
		 * define non mix-seating button.
		 */
		JRadioButton rdbtnMixseating = new JRadioButton("non mix-seating");
		/**
		 * define all button
		 */
		JRadioButton rdbtnAll = new JRadioButton("all");
		rdbtnAll.setSelected(true);
		rdbtnAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.tripmix1.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				}
				model.clear();
				int length = TripPlanner.triphop1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.triphop1.get(i));
				}
				
				TripPlanner.triphop2.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.tripmix2.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.trip2.get(i));
				}
				model2.clear();
				int length2 = TripPlanner.triphop2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.triphop2.get(i));
				}
			}
		});
		buttonGroup_1.add(rdbtnAll);
		rdbtnAll.setBounds(16, 19, 121, 23);
		frmRoundtrip.getContentPane().add(rdbtnAll);
		
		/**
		 * define direct button.
		 */
		JRadioButton rdbtnDirect = new JRadioButton("direct");
		rdbtnDirect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.tripmix1.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				}
				TripPlanner.tripfilter1 = TripPlanner.Filter(7, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripfilter1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripfilter1.get(i));
				}
				
				TripPlanner.triphop2.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.tripmix2.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.trip2.get(i));
				}
				TripPlanner.tripfilter2 = TripPlanner.Filter(7, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripfilter2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripfilter2.get(i));
				}
			}
		});
		buttonGroup_1.add(rdbtnDirect);
		rdbtnDirect.setBounds(199, 19, 121, 23);
		frmRoundtrip.getContentPane().add(rdbtnDirect);
		
		/**
		 * define one hop button.
		 */
		JRadioButton rdbtnOneHop = new JRadioButton("one hop");
		rdbtnOneHop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.tripmix1.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				}
				TripPlanner.tripfilter1 = TripPlanner.Filter(8, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripfilter1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripfilter1.get(i));
				}
				
				TripPlanner.triphop2.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.tripmix2.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.trip2.get(i));
				}
				TripPlanner.tripfilter2 = TripPlanner.Filter(8, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripfilter2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripfilter2.get(i));
				}
			}
		});
		buttonGroup_1.add(rdbtnOneHop);
		rdbtnOneHop.setBounds(396, 19, 121, 23);
		frmRoundtrip.getContentPane().add(rdbtnOneHop);
		
		/**
		 * define two hops button.
		 */
		JRadioButton rdbtnTwoHops = new JRadioButton("two hops");
		rdbtnTwoHops.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.tripmix1.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				}
				TripPlanner.tripfilter1 = TripPlanner.Filter(9, TripPlanner.triphop1);
				model.clear();
				int length = TripPlanner.tripfilter1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.tripfilter1.get(i));
				}
				
				TripPlanner.triphop2.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.tripmix2.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.trip2.get(i));
				}
				TripPlanner.tripfilter2 = TripPlanner.Filter(9, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripfilter2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripfilter2.get(i));
				}
			}
		});
		buttonGroup_1.add(rdbtnTwoHops);
		rdbtnTwoHops.setBounds(596, 19, 121, 23);
		frmRoundtrip.getContentPane().add(rdbtnTwoHops);
		
		rdbtnMixseating.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonGroup.clearSelection();
				TripPlanner.triphop1.clear();
				rdbtnAll.setSelected(true);
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.tripmix1.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip1.size(); i++)
						TripPlanner.triphop1.add(TripPlanner.trip1.get(i));
				}
				model.clear();
				int length = TripPlanner.triphop1.size();
				for (int i = 0; i < length; i++)
				{
					model.addElement(TripPlanner.triphop1.get(i));
				}
				
				TripPlanner.triphop2.clear();
				if (rdbtnMixseating.isSelected() == true)
				{
					for (int i = 0; i < TripPlanner.tripmix2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.tripmix2.get(i));
				} else
				{
					for (int i = 0; i < TripPlanner.trip2.size(); i++)
						TripPlanner.triphop2.add(TripPlanner.trip2.get(i));
				}
				model2.clear();
				int length2 = TripPlanner.triphop2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.triphop2.get(i));
				}
			}
		});
		
		rdbtnMixseating.setBounds(754, 19, 121, 23);
		frmRoundtrip.getContentPane().add(rdbtnMixseating);
		
		/**
		 * define price button.
		 */
		JCheckBox chckbxPricecheapest = new JCheckBox("price(cheapest)");
		chckbxPricecheapest.setSelected(true);
		chckbxPricecheapest.addActionListener(new ActionListener()
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
				
				TripPlanner.tripsort2 = TripPlanner.SortBy(1, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripsort2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripsort2.get(i));
				}
			}
		});
		buttonGroup.add(chckbxPricecheapest);
		chckbxPricecheapest.setBounds(16, 56, 126, 23);
		frmRoundtrip.getContentPane().add(chckbxPricecheapest);
		
		/**
		 * define duration button.
		 */
		JCheckBox chckbxDurationshortest = new JCheckBox("duration(shortest)");
		chckbxDurationshortest.addActionListener(new ActionListener()
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
				
				TripPlanner.tripsort2 = TripPlanner.SortBy(2, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripsort2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripsort2.get(i));
				}
			}
		});
		buttonGroup.add(chckbxDurationshortest);
		chckbxDurationshortest.setBounds(199, 54, 146, 23);
		frmRoundtrip.getContentPane().add(chckbxDurationshortest);
		
		/**
		 * define departure button.
		 */
		JCheckBox chckbxDepartureearliest = new JCheckBox("departure(earliest)");
		chckbxDepartureearliest.addActionListener(new ActionListener()
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
				
				TripPlanner.tripsort2 = TripPlanner.SortBy(3, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripsort2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripsort2.get(i));
				}
			}
		});
		buttonGroup.add(chckbxDepartureearliest);
		chckbxDepartureearliest.setBounds(396, 54, 154, 23);
		frmRoundtrip.getContentPane().add(chckbxDepartureearliest);
		
		/**
		 * define arrival button
		 */
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
				
				TripPlanner.tripsort2 = TripPlanner.SortBy(4, TripPlanner.triphop2);
				model2.clear();
				int length2 = TripPlanner.tripsort2.size();
				for (int i = 0; i < length2; i++)
				{
					model2.addElement(TripPlanner.tripsort2.get(i));
				}
			}
		});
		buttonGroup.add(chckbxArrivalearliest);
		chckbxArrivalearliest.setBounds(596, 54, 146, 23);
		frmRoundtrip.getContentPane().add(chckbxArrivalearliest);
		
		/**
		 * define cancel button.
		 */
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				frmRoundtrip.dispose();
			}
		});
		btnCancel.setBounds(564, 538, 93, 23);
		frmRoundtrip.getContentPane().add(btnCancel);
		
		/**
		 * define book button.
		 */
		JButton btnBook = new JButton("book");
		btnBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// must select trips
				if ((Trip) list.getSelectedValue() == null || (Trip) list2.getSelectedValue() == null)
					JOptionPane.showMessageDialog(null, "Please select trips.");
				// realize expiration part.
				else
				{
					// get start time
					long startTime = System.currentTimeMillis();
					Object[] options =
					{ "confirm", "cancel" };
					int response = JOptionPane.showOptionDialog(null,
							"Are you sure to book this trip?" + "\nOutbound:"
									+ ((Trip) list.getSelectedValue()).confirmationString() + "Returning:"
									+ ((Trip) list2.getSelectedValue()).confirmationString(),
							"confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					// if confirm
					if (response == 0)
					{
						// get end time
						long endTime = System.currentTimeMillis();
						long totalTime = (endTime - startTime) / 1000;
						// if wait time exceeds 5 seconds.
						if (totalTime > 5)
						{
							JOptionPane.showMessageDialog(null,
									"Dialog expires, please restart search.\n window will be closed in 3 seconds after click.");
							try
							{
								Thread.currentThread().sleep(3000);// wait 3s
							} catch (Exception e)
							{
							}
							frmRoundtrip.dispose();
						}
						// if wait time is within 5 seconds.
						else
						{
							// get selected trips.
							reserve1 = (Trip) list.getSelectedValue();
							reserve2 = (Trip) list2.getSelectedValue();
							// merge trips.
							reserve3 = Trips.MergeTrips(reserve1, reserve2);
							if (ValidationController.Instance().ConfirmTrip(reserve3) == true)
							{
								// if success, show message.
								JOptionPane.showMessageDialog(null,
										"book successfully" + "\n" + reserve1.GetFlightSequence() + "\n"
												+ reserve1.toString() + "\n" + reserve2.GetFlightSequence() + "\n"
												+ reserve2.toString() + "\n");
								try
								{
									Thread.currentThread().sleep(2000);
								} catch (InterruptedException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								frmRoundtrip.dispose();
							} else
							{
								JOptionPane.showMessageDialog(null,
										"book fail \n" + ValidationController.Instance().GetLastErrorMessage());
							}
						}
					} else if (response == 1)
					{
						JOptionPane.showMessageDialog(null, "cancelled");
					}
				}
			}
		});
		btnBook.setBounds(413, 538, 93, 23);
		frmRoundtrip.getContentPane().add(btnBook);
	}
}
