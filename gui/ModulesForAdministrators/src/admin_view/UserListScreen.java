package admin_view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import db.Connect_DB;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.Dimension;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserListScreen extends JFrame {
	private static UserListScreen frame;

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private static JTable table;
	private DefaultTableModel tableModel;
	
	
	private JTextField searchTextField;
	private JButton btnSearch;
	private JButton btnRefresh;
	
	Vector<Vector<Object>> data;
	String filter = "CreateTime";
	String order = "asc";
	String criteria = "Username";
	String keyword = "";

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new UserListScreen();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserListScreen() {
		Connect_DB db = Connect_DB.getInstance();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 964, 450);
		contentPane.add(scrollPane);
		
		// Data to be displayed in the JTable
        try {
			data = db.getAllUser(filter, order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table = new JTable();
		scrollPane.setViewportView(table);
        String[] columnNames = { "Username", "Fullname", "Address", "Date of Birth", "Sex", "Email", "Date created" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        for (int i = 0; i < data.size(); i++) {
        	tableModel.addRow(data.get(i));
        }
        table.setModel(tableModel);
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//			},
//			new String[] {
//				"Username", "Fullname", "Address", "Date of Birth", "Sex", "Email", "Date created"
//			}
//		) {
//			boolean[] columnEditables = new boolean[] {
//				false, false, false, false, false, false, false
//			};
//			public boolean isCellEditable(int row, int column) {
//				return columnEditables[column];
//			}
//		});
		table.getColumnModel().getColumn(3).setPreferredWidth(15);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setPopupSize(new Dimension(120, 200));
		addPopup(table, popupMenu);
		
		JMenuItem updateMenuItem = new JMenuItem("C???p nh???t");
		updateMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String user = table.getValueAt(row, 0).toString();
				UpdateUserScreen.main(user);
			}
		});
		popupMenu.add(updateMenuItem);
		
		JMenuItem deleteMenuItem = new JMenuItem("X??a");
		deleteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(frame,
						"B???n c?? ch???c mu???n x??a ng?????i d??ng n??y",
						"C???nh b??o",
						JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE);
				if (choice == JOptionPane.OK_OPTION) {
					int row = table.getSelectedRow();
					String user = table.getValueAt(row, 0).toString();
					
					try {
						db.deleteUser(user);
						JOptionPane.showMessageDialog(frame,
				                "X??a th??nh c??ng",
				                "Message",
				                JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenu.add(deleteMenuItem);
		
		JMenuItem banMenuItem = new JMenuItem("Kh??a");
		banMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(frame,
						"B???n c?? ch???c mu???n kh??a ng?????i d??ng n??y",
						"C???nh b??o",
						JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE);
				if (choice == JOptionPane.OK_OPTION) {
					int row = table.getSelectedRow();
					String user = table.getValueAt(row, 0).toString();
					
					try {
						db.banUser(user);
						JOptionPane.showMessageDialog(frame,
				                "Kh??a th??nh c??ng",
				                "Message",
				                JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenu.add(banMenuItem);
		
		JMenuItem loginHistoryMenuItem = new JMenuItem("L???ch s??? ????ng nh???p");
		loginHistoryMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("L???ch s??? ????ng nh???p");
				frame.dispose();
				LoginHistoryScreen.main();
			}
		});
		popupMenu.add(loginHistoryMenuItem);
		
		JMenuItem friendListMenuItem = new JMenuItem("Danh s??ch b???n b??");
		friendListMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Danh s??ch b???n b??");
				frame.dispose();
				FriendListScreen.main();
			}
		});
		popupMenu.add(friendListMenuItem);
		
		JLabel headerLabel = new JLabel("DANH S??CH NG?????I D??NG");
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		headerLabel.setBounds(26, 11, 948, 25);
		contentPane.add(headerLabel);
		
		JButton btnAdd = new JButton("Th??m");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// T???o c???a s??? m???i ????? th??m
				AddUserScreen.main();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBounds(375, 50, 90, 30);
		contentPane.add(btnAdd);
		
		JComboBox<String> filterComboBox = new JComboBox<String>();
		filterComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"S???p x???p theo Date created", "S???p x???p theo Username", "S???p x???p theo Fullname"}));
		filterComboBox.setBounds(10, 50, 175, 30);
		filterComboBox.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		        	// C???p nh???t l???i modelTable
		        	String filterSelect = filterComboBox.getSelectedItem().toString();
		        	data.clear();
		        	tableModel.setRowCount(0);
		        	
		        	if (filterSelect.equals("S???p x???p theo Date created")) {
		        		filter = "CreateTime";
		        	} else if (filterSelect.equals("S???p x???p theo Username")) {
		        		filter = "UserName";
		        	} else if (filterSelect.equals("S???p x???p theo Fullname")) {
		        		filter = "FullName";
		        	}
		        	
		        	try {
						data = db.searchUser(keyword, criteria, filter, order);
			            for (int i = 0; i < data.size(); i++) {
			            	tableModel.addRow(data.get(i));
			            }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		    }
		});
		contentPane.add(filterComboBox);
		
		JComboBox<String> orderComboBox = new JComboBox<String>();
		orderComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"S???p x???p t??ng d???n", "S???p x???p gi???m d???n"}));
		orderComboBox.setBounds(215, 50, 130, 30);
		orderComboBox.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		        	// C???p nh???t l???i modelTable
		        	// C???p nh???t l???i modelTable
		        	String orderSelect = orderComboBox.getSelectedItem().toString();
		        	data.clear();
		        	tableModel.setRowCount(0);
		        	
		        	if (orderSelect.equals("S???p x???p t??ng d???n")) {
		        		order = "asc";
		        	} else if (orderSelect.equals("S???p x???p gi???m d???n")) {
		        		order = "desc";
		        	}
		        	
		        	try {
		        		data = db.searchUser(keyword, criteria, filter, order);
			            for (int i = 0; i < data.size(); i++) {
			            	tableModel.addRow(data.get(i));
			            }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		    }
		});
		contentPane.add(orderComboBox);
		
		btnSearch = new JButton("T??m");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	data.clear();
	        	tableModel.setRowCount(0);
	        	keyword = searchTextField.getText();
	        	
	        	try {
					data = db.searchUser(keyword, criteria, filter, order);
		            for (int i = 0; i < data.size(); i++) {
		            	tableModel.addRow(data.get(i));
		            }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearch.setBounds(884, 50, 90, 30);
		contentPane.add(btnSearch);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(615, 50, 240, 30);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		btnRefresh = new JButton("T???i l???i");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	data.clear();
	        	tableModel.setRowCount(0);
	        	
		        try {
		        	data = db.searchUser(keyword, criteria, filter, order);
			        for (int i = 0; i < data.size(); i++) {
			        	tableModel.addRow(data.get(i));
			        }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRefresh.setBounds(884, 10, 90, 30);
		contentPane.add(btnRefresh);
		
		JButton btnBack = new JButton("Quay l???i");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MenuScreen.main();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBounds(10, 10, 90, 30);
		contentPane.add(btnBack);
		
		JComboBox<String> searchComboBox = new JComboBox<String>();
		searchComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Username", "Fullname"}));
		searchComboBox.setBounds(490, 50, 100, 30);
		searchComboBox.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		            criteria = searchComboBox.getSelectedItem().toString();
		        }
		    }
		});
		contentPane.add(searchComboBox);
		
		// Close db when close window by X
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	db.close();
		    }
		});
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedRow();
				if (e.isPopupTrigger() && row != -1) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
}
