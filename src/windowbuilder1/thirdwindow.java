package windowbuilder1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;

public class thirdwindow
{
	
	public JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
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
		frame.setBounds(100, 100, 724, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JCheckBox chckbxPrice = new JCheckBox("price");
		chckbxPrice.setSelected(true);
		buttonGroup.add(chckbxPrice);
		chckbxPrice.setBounds(31, 16, 55, 23);
		frame.getContentPane().add(chckbxPrice);
		
		JCheckBox chckbxDuration = new JCheckBox("duration");
		buttonGroup.add(chckbxDuration);
		chckbxDuration.setBounds(164, 16, 86, 23);
		frame.getContentPane().add(chckbxDuration);
		
		JCheckBox chckbxTotalTime = new JCheckBox("total time");
		buttonGroup.add(chckbxTotalTime);
		chckbxTotalTime.setBounds(324, 16, 103, 23);
		frame.getContentPane().add(chckbxTotalTime);
		
		JButton btnBook = new JButton("book");
		btnBook.setBounds(228, 385, 93, 23);
		frame.getContentPane().add(btnBook);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.setBounds(367, 385, 93, 23);
		frame.getContentPane().add(btnCancel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 688, 333);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(0, 0, 325, 333);
		panel.add(list);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setBounds(335, 0, 353, 333);
		panel.add(list_1);
	}
}
