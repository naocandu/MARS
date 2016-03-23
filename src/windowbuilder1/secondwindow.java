/*
 * Class provide a window showing the search result.
 * And user can book flights in this window.
 * @author Hao Liu
 * @since 2016-3-22
 */
package windowbuilder1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.JSlider;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Scrollbar;
import java.awt.ScrollPane;
import java.awt.List;
import java.awt.Panel;
import javax.swing.JScrollPane;

public class secondwindow
{
	
	public JFrame frmFlightsResults; 
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public String reserve;
	
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
		frmFlightsResults.setTitle("Flights Results");
		frmFlightsResults.setBounds(0, 0, 650, 440);
		frmFlightsResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFlightsResults.getContentPane().setLayout(null);
		
		JCheckBox chckbxPrice = new JCheckBox("price(cheapest)");
		chckbxPrice.setSelected(true);
		chckbxPrice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// void sort()
			}
		});
		buttonGroup.add(chckbxPrice);
		chckbxPrice.setBounds(31, 18, 135, 23);
		frmFlightsResults.getContentPane().add(chckbxPrice);
		
		JCheckBox chckbxDuration = new JCheckBox("duration(shortest)");
		chckbxDuration.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// void sort1()
			}
		});
		buttonGroup.add(chckbxDuration);
		chckbxDuration.setBounds(189, 18, 135, 23);
		frmFlightsResults.getContentPane().add(chckbxDuration);
		
		JCheckBox chckbxDeparting = new JCheckBox("departure(earliest)");
		chckbxDeparting.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// void sort2()
			}
		});
		buttonGroup.add(chckbxDeparting);
		chckbxDeparting.setBounds(375, 18, 155, 23);
		frmFlightsResults.getContentPane().add(chckbxDeparting);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 614, 304);
		frmFlightsResults.getContentPane().add(scrollPane);
		
		// getflights()
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(new AbstractListModel()
		{
			/*String[] values = new tripplanner().getFlight();
			
			public int getSize()
			{
				return values.length;
			}
			
			public Object getElementAt(int index)
			{
				return values[index];
			}*/
		});
		
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
						Thread.currentThread().sleep(3000);// ms
						}   
						catch(Exception e){} 
						frmFlightsResults.dispose();
						
					} else
					{
						reserve = (String) list.getSelectedValue();
						// if(reserve()==0) JOptionPane.showMessageDialog(null,
						// "book successfully"+"\n"+reserve);
						/*
						 * else { JOptionPane.showMessageDialog(null,
						 * "book fail, please try again");
						 * frmFlightsResults.dispose(); }
						 */
						JOptionPane.showMessageDialog(null,
								"book successfully" + "\n" + reserve + "\n" + startTime + "\n" + endTime);
					}
				} else if (response == 1)
				{
					JOptionPane.showMessageDialog(null, "book fail");
				}
			}
		});
		btnBook.setBounds(135, 368, 93, 23);
		frmFlightsResults.getContentPane().add(btnBook);
		
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
	}
}
