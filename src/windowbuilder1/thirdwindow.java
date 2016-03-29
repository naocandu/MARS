package windowbuilder1;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

import Controller.Trip;
import TripPlanner.TripPlanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class thirdwindow
{
	
	public JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private MyListCellRenderer myrenderer;
	private class MyListCellRenderer extends DefaultListCellRenderer {

	    @Override
	  
	    public Component getListCellRendererComponent(
	            JList list, Object value, int index,
	            boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	        Trip label = (Trip) value;
	        
	        String GetAiportSequence = label.GetAiportSequence();
	        String GetFlightSequence = label.GetFlightSequence();
	        String toString = label.toString();
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
					thirdwindow window = new thirdwindow();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 1078, 628);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 51, 489, 467);
		frame.getContentPane().add(scrollPane);
		
		JList<Trip> list = new JList();
		DefaultListModel model = new DefaultListModel();
		int length = TripPlanner.trip.size();
		for(int i=0; i< length; i++)
		{
			model.addElement(TripPlanner.trip.get(i));
		}
		list.setModel(model);
        list.setCellRenderer(new MyListCellRenderer());
        scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(564, 51, 467, 467);
		frame.getContentPane().add(scrollPane_1);
		
		JList list2 = new JList();
		DefaultListModel model2 = new DefaultListModel();
		int length2 = TripPlanner.trip2.size();
		for(int i=0; i< length2; i++)
		{
			model.addElement(TripPlanner.trip2.get(i));
		}
		list.setModel(model2);
        list.setCellRenderer(new MyListCellRenderer());
		scrollPane_1.setViewportView(list2);
		
		JCheckBox chckbxPricecheapest = new JCheckBox("price(cheapest)");
		chckbxPricecheapest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		buttonGroup.add(chckbxPricecheapest);
		chckbxPricecheapest.setBounds(16, 6, 126, 23);
		frame.getContentPane().add(chckbxPricecheapest);
		
		JCheckBox chckbxDurationshortest = new JCheckBox("duration(shortest)");
		chckbxDurationshortest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(chckbxDurationshortest);
		chckbxDurationshortest.setBounds(163, 6, 146, 23);
		frame.getContentPane().add(chckbxDurationshortest);
		
		JCheckBox chckbxDepartureearliest = new JCheckBox("departure(earliest)");
		chckbxDepartureearliest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(chckbxDepartureearliest);
		chckbxDepartureearliest.setBounds(311, 6, 154, 23);
		frame.getContentPane().add(chckbxDepartureearliest);
		
		JCheckBox chckbxDeparturelatest = new JCheckBox("departure(latest)");
		chckbxDeparturelatest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(chckbxDeparturelatest);
		chckbxDeparturelatest.setBounds(486, 6, 154, 23);
		frame.getContentPane().add(chckbxDeparturelatest);
		
		JCheckBox chckbxArrivalearliest = new JCheckBox("arrival(earliest)");
		chckbxArrivalearliest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(chckbxArrivalearliest);
		chckbxArrivalearliest.setBounds(652, 6, 146, 23);
		frame.getContentPane().add(chckbxArrivalearliest);
		
		JCheckBox chckbxArrivallatest = new JCheckBox("arrival(latest)");
		chckbxArrivallatest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(chckbxArrivallatest);
		chckbxArrivallatest.setBounds(838, 6, 134, 23);
		frame.getContentPane().add(chckbxArrivallatest);
		
		JButton btnBook = new JButton("book");
		btnBook.setBounds(413, 538, 93, 23);
		frame.getContentPane().add(btnBook);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setBounds(564, 538, 93, 23);
		frame.getContentPane().add(btnCancel);
	}
}
