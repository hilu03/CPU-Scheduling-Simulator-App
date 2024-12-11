package CPU_Scheduling;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.css.RGBColor;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class mainForm extends JFrame {

	private JPanel contentPane;
	private JTable table_input;
	private JTextField txtArrive;
	private JTextField txtBurst;
	public JTextField txtQuantum;
	public  JTable table_output;
	public JTextField txtAvg_turn;
	public JTextField txtAvg_wait;
	private JTextField txtID;
	public JButton btnRun;
	public JComboBox cbbAlgorithms;
	public GanttChart ganttChart;
	
	public process[] Process = new process[10000];
	public int n = 0;
	private JTextField txtColor;
	private JTextField txtPriority;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public mainForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 924, 719);
		contentPane = new JPanel();
		
		setTitle("CPU Scheduling");
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		
		
		
		contentPane.setLayout(null);

		btnRun = new JButton("RUN");
		btnRun.setBackground(new Color(223, 255, 149));
		
		btnRun.setBounds(359, 0, 95, 23);
		contentPane.add(btnRun);

		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(new Color(223, 255, 149));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtArrive.setText("");
				txtBurst.setText("");
				txtID.setText("");
				txtAvg_turn.setText("");
				txtAvg_wait.setText("");
				txtQuantum.setText("");
				
				
				for (int i=0; i<n; i++) {
					Process[i] = null;
				}
				
				n = 0;
				
				ganttChart.processes = null;
		        ganttChart.currentProcessIndex = -1;
		        ganttChart.isRunning = false;
		        ganttChart.repaint();
				
				
				
				
				
				
				DefaultTableModel model = (DefaultTableModel) table_input.getModel();
				model.setRowCount(0); // Xóa tất cả các dòng
				model = (DefaultTableModel) table_output.getModel();
				model.setRowCount(0); // Xóa tất cả các dòng
			}
		});
		btnReset.setBounds(453, 0, 89, 23);
		contentPane.add(btnReset);
		
		ChangeCellColor formatCell = new ChangeCellColor();

		JLabel lblNewLabel = new JLabel("Arrive Time");
		lblNewLabel.setBounds(27, 91, 66, 23);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Burst Time");
		lblNewLabel_1.setBounds(27, 125, 71, 23);
		contentPane.add(lblNewLabel_1);

		txtArrive = new JTextField();
		txtArrive.setBounds(96, 91, 120, 22);
		contentPane.add(txtArrive);
		txtArrive.setColumns(10);

		txtBurst = new JTextField();
		txtBurst.setBounds(96, 125, 120, 23);
		contentPane.add(txtBurst);
		txtBurst.setColumns(10);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(223, 255, 149));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtArrive.getText().isBlank() || txtBurst.getText().isBlank() || txtID.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Please Input Data!!!");
				}
				else {
					try {
						int at = Integer.parseInt(txtArrive.getText().trim());
						int bt = Integer.parseInt(txtBurst.getText().trim());
						if (at < 0 || bt < 1)
							JOptionPane.showMessageDialog(null,
									"Arrive Time and Burst Time must be a positive number!!!");
						else if (txtColor.getText().isBlank())
								JOptionPane.showMessageDialog(null,
										"Pick process color!!!");
						else if (isExsistID(txtID.getText().trim()))
								JOptionPane.showMessageDialog(null,
										"Existed Process ID!!!");
						else {
								DefaultTableModel model = (DefaultTableModel) table_input.getModel();
								Object o[] = new Object[5];
								o[0] = txtID.getText();
								o[1] = txtArrive.getText();
								o[2] = txtBurst.getText();
								int priority = txtPriority.getText().trim().isEmpty()? 1: 0;
								if (priority == 0) {
									try {
										priority = Integer.parseInt(txtPriority.getText().trim());
										if (priority < 1) {
											JOptionPane.showMessageDialog(null, "Priority must be a positive number!!!");
											return;
										}
									}
									catch(NumberFormatException ex) {
										JOptionPane.showMessageDialog(null, "Priority must be a positive number!!!");
										return;
									}
								}
								o[3] = txtPriority.getText().trim().isEmpty()? "1": txtPriority.getText().trim();
								o[4] = txtColor.getText().trim();
								model.addRow(o);
							    process p = new process(txtID.getText().trim(), at, bt, new Color(Integer.parseInt(txtColor.getText().trim())), priority);
							    Process[n++] = p;
							    txtArrive.setText("");
								txtBurst.setText("");
								txtID.setText("");
								txtPriority.setText("");
							    txtColor.setBackground(Color.white);
							    txtColor.setText("");
						}
					}
					catch (NumberFormatException u) {
						JOptionPane.showMessageDialog(null, "Arrive Time and Burst Time must be a positive number!!!");
					}
				}
			}
		});
		btnAdd.setBounds(48, 197, 66, 36);
		contentPane.add(btnAdd);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(223, 255, 149));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int selectedRow = table_input.getSelectedRow();
		        if (selectedRow >= 0) {
		        	if (txtArrive.getText().isBlank() || txtBurst.getText().isBlank() || txtID.getText().isBlank() || txtPriority.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "Please Input Data!!!");
					}
		        	else {
		        		String id = txtID.getText();
						if (!id.equals(Process[selectedRow].id) && isExsistID(id)) {
							JOptionPane.showMessageDialog(null, "Existed Process ID!!!");
						}
						else {
				        	String arriveTime = txtArrive.getText();
					        String burstTime = txtBurst.getText();
					        String priority = txtPriority.getText().trim();
					        String color = txtColor.getText();
					        
					        try {
					        	int at = Integer.parseInt(arriveTime);
						        int bt = Integer.parseInt(burstTime);
						        int pr = Integer.parseInt(priority);
						        if (at < 0 || bt < 1 || pr < 1) {
						        	JOptionPane.showMessageDialog(null, "Arrive Time, Burst Time and Priority must be a positive number!!!"); 
						        	return;
						        }
						        
						     // cap nhat table
					            table_input.setValueAt(id, selectedRow, 0);
					            table_input.setValueAt(arriveTime, selectedRow, 1);
					            table_input.setValueAt(burstTime, selectedRow, 2);
					            table_input.setValueAt(priority, selectedRow, 3);
					            table_input.setValueAt(color, selectedRow, 4);
					            
					            // cap nhat Process
					            Process[selectedRow].id = id;
					            Process[selectedRow].arriveTime = at;
					            Process[selectedRow].burstTime = Integer.parseInt(burstTime);
					            Process[selectedRow].priority = Integer.parseInt(priority);
					        	Process[selectedRow].color = new Color(Integer.parseInt(color));
					        	
					        	txtArrive.setText("");
								txtBurst.setText("");
								txtID.setText("");
								txtPriority.setText("");
							    txtColor.setBackground(Color.white);
							    txtColor.setText("");
					        }
					        catch (NumberFormatException ex) {
					        	JOptionPane.showMessageDialog(null, "Arrive Time, Burst Time and Priority must be a positive number!!!");   
					        }
					        
					        
				        	
						}
		        	}
		        	
				}
				else
			        JOptionPane.showMessageDialog(null, "Please select one row to update!!!");   
			}
		});
		btnUpdate.setBounds(182, 197, 81, 36);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(223, 255, 149));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table_input.getModel();
				int selectedRows[] = table_input.getSelectedRows();

				if (selectedRows.length > 0) {
					// xoa trong table
					for (int i = selectedRows.length - 1; i >= 0; i--) {
						model.removeRow(selectedRows[i]);
					}
					// xoa Process
					for (int i = selectedRows[0]; i < n - selectedRows.length; i++) {
						Process[i] = Process[i + selectedRows.length];
					}
				} 
				else {
					JOptionPane.showMessageDialog(null, "Please select at least one row!!!");
				}
				
			    n -= selectedRows.length;
			}
		});
		btnDelete.setBounds(336, 197, 73, 36);
		contentPane.add(btnDelete);

		JLabel lblNewLabel_2 = new JLabel("Gantt Chart");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(27, 248, 108, 23);
		contentPane.add(lblNewLabel_2);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(27, 431, 650, 238);
		contentPane.add(scrollPane1);

		table_output = new JTable();
		table_output.setShowVerticalLines(false);
		scrollPane1.getViewport().setBackground(Color.white);
		table_output.getTableHeader().setOpaque(false);
		table_output.getTableHeader().setBackground(new Color(101, 132, 237));
		table_output.getTableHeader().setForeground(Color.WHITE);
		
		table_output.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Arrive Time", "Burst Time", "Wait Time", "Turnaround Time", "Completion Time"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_output.getColumnModel().getColumn(0).setPreferredWidth(50);
		table_output.getColumnModel().getColumn(3).setPreferredWidth(89);
		table_output.getColumnModel().getColumn(4).setPreferredWidth(99);
		scrollPane1.setViewportView(table_output);

		cbbAlgorithms = new JComboBox();
		cbbAlgorithms.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbbAlgorithms.getSelectedIndex() == 3)
					txtQuantum.setEditable(true);
				else
					txtQuantum.setEditable(false);
			}
		});
		cbbAlgorithms.setModel(new DefaultComboBoxModel(new String[] {"FCFS", "SJF", "SRTF", "Round Robin", "Priority (Non-preemptive)", "Priority (Preemptive)"}));
		cbbAlgorithms.setBounds(119, 0, 175, 23);
		contentPane.add(cbbAlgorithms);

		JLabel lblProcessId = new JLabel("Process ID");
		lblProcessId.setBounds(27, 57, 66, 23);
		contentPane.add(lblProcessId);

		txtID = new JTextField();
		txtID.setColumns(10);
		txtID.setBounds(96, 57, 120, 22);
		contentPane.add(txtID);
		
		JLabel lblQuantum = new JLabel("Quantum");
		lblQuantum.setBounds(240, 57, 54, 23);
		contentPane.add(lblQuantum);
		
		txtQuantum = new JTextField();
		txtQuantum.setEditable(false);
		txtQuantum.setColumns(10);
		txtQuantum.setBounds(304, 57, 120, 22);
		contentPane.add(txtQuantum);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(26, 283, 850, 130);
		contentPane.add(scrollPane_1);
		
		
		ganttChart = new GanttChart();
		scrollPane_1.setViewportView(ganttChart);
		ganttChart.setBackground(new Color(255, 255, 255));
		
		JLabel lblcolor = new JLabel("Color");
		lblcolor.setBounds(27, 163, 71, 23);
		contentPane.add(lblcolor);
		
		txtColor = new JTextField();
		txtColor.setBackground(new Color(255, 255, 255));
		txtColor.setEditable(false);
		txtColor.setColumns(10);
		txtColor.setBounds(96, 163, 120, 23);
		contentPane.add(txtColor);
		
		JButton btnChooseColor = new JButton("Color");
		btnChooseColor.setBackground(new Color(223, 255, 149));
		btnChooseColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Choose process color", getForeground());
				if (color != null) {
					txtColor.setText(String.valueOf(color.getRGB()));
					txtColor.setBackground(color);
					txtColor.setForeground(color);
				}	
			}
		});
		btnChooseColor.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnChooseColor.setBounds(241, 156, 66, 31);
		contentPane.add(btnChooseColor);
		
		JButton btnRandom = new JButton("Random");
		btnRandom.setBackground(new Color(223, 255, 149));
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = JOptionPane.showInputDialog("Input number of processes:");
				if (s != null) {
					try {
						int numOfProcess = Integer.parseInt(s);
						DefaultTableModel model = (DefaultTableModel) table_input.getModel();
						model.setRowCount(0);
						for (int i=0; i<n; i++) {
							Process[i] = null;
						}
						n = 0;
						Random random = new Random();

						// int numOfProcess = random.nextInt(17) + 4;

						Color colorRandom;
						
						for (int i = 1; i <= numOfProcess; i++) {
						    Object o[] = new Object[5];

						    o[0] = i; // id bắt đầu từ 1

						    // Xác suất 80%
						    if (random.nextDouble() < 0.8) {
						        o[1] = random.nextInt(11);
						        o[2] = random.nextInt(11) + 1;
						    } 
						    else {
						        o[1] = random.nextInt(20);
						        o[2] = random.nextInt(20) + 1;
						    }

						    o[3] = random.nextInt(numOfProcess) + 1;
						    
						    colorRandom = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
						    int color = colorRandom.getRGB();
						    o[4] = color;

						    model.addRow(o);
						    
						    int arriveTime = (int)o[1];
						    int burstTime = (int)o[2];
						    int priority = (int)o[3];
						    process p = new process(Integer.toString(i), arriveTime, burstTime, new Color(color), priority);
						    Process[n++] = p;
						}
					}
					catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "It's not a number!!");
					}
					
					
				}
				

			}
		});
		btnRandom.setBounds(541, 0, 89, 23);
		contentPane.add(btnRandom);
		
		JLabel lblNumOfProcess = new JLabel("Priority");
		lblNumOfProcess.setBounds(240, 91, 54, 23);
		contentPane.add(lblNumOfProcess);
		
		txtPriority = new JTextField();
		txtPriority.setColumns(10);
		txtPriority.setBounds(304, 91, 120, 22);
		contentPane.add(txtPriority);
		
		JButton btnReadFile = new JButton("Read File");
		btnReadFile.setBackground(new Color(223, 255, 149));
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser();
				choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = choose.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					String path = choose.getSelectedFile().getAbsolutePath();
					try {
						DataInputStream in = new DataInputStream(new FileInputStream(path));
						String line;
						
						for (int i=0; i<n; i++) {
							Process[i] = null;
						}
						
						n = 0;
						
						DefaultTableModel model = (DefaultTableModel) table_input.getModel();
						model.setRowCount(0);
						
						//id,at,bt,priority,rgb
						while ((line = in.readLine()) != null) {
							String part[] = line.split(",");
							Process[n++] = new process(part[0], Integer.parseInt(part[1]), Integer.parseInt(part[2]), new Color(Integer.parseInt(part[4]), Integer.parseInt(part[5]), Integer.parseInt(part[6])), Integer.parseInt(part[3]));
							Object o[] = new Object[5];
							o[0] = Process[n-1].id;
							o[1] = Process[n-1].arriveTime;
							o[2] = Process[n-1].burstTime;
							o[3] = Process[n-1].priority;
							o[4] = Process[n-1].color.getRGB();
							model.addRow(o);
						}
						
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnReadFile.setBounds(628, 0, 89, 23);
		contentPane.add(btnReadFile);
		
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(434, 35, 442, 216);
				contentPane.add(scrollPane);
				
						table_input = new JTable();
						table_input.setShowVerticalLines(false);
						table_input.setOpaque(false);
						scrollPane.getViewport().setBackground(Color.white);
						table_input.getTableHeader().setOpaque(false);
						table_input.getTableHeader().setBackground(new Color(101, 132, 237));
						table_input.getTableHeader().setForeground(Color.WHITE);
						
						table_input.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								DefaultTableModel model = (DefaultTableModel) table_input.getModel();
								int selectedRow = table_input.getSelectedRow();
						        if (selectedRow >= 0) {
						            Object id         = model.getValueAt(selectedRow, 0);
						            Object arriveTime = model.getValueAt(selectedRow, 1);
						            Object burstTime  = model.getValueAt(selectedRow, 2);
						            Object priority   = model.getValueAt(selectedRow, 3);
						            Object color      = model.getValueAt(selectedRow, 4);

						            txtID.setText(id.toString());
									txtArrive.setText(arriveTime.toString());
									txtBurst.setText(burstTime.toString());
									txtPriority.setText(priority.toString());
									txtColor.setText(color.toString());
									txtColor.setForeground(new Color(Integer.parseInt(color.toString())));
									txtColor.setBackground(new Color(Integer.parseInt(color.toString())));
						        }
							}
						});
						table_input.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"ID", "Arrive Time", "Burst Time", "Priority", "Color"
							}
						) {
							boolean[] columnEditables = new boolean[] {
								false, false, false, false, false
							};
							public boolean isCellEditable(int row, int column) {
								return columnEditables[column];
							}
						});
						table_input.getColumnModel().getColumn(0).setPreferredWidth(56);
						table_input.getColumnModel().getColumn(3).setPreferredWidth(53);
						table_input.getColumnModel().getColumn(4).setPreferredWidth(88);
						scrollPane.setViewportView(table_input);
						
						JLabel lblAvgarrive = new JLabel("Avg_Turn");
						lblAvgarrive.setBounds(687, 495, 66, 23);
						contentPane.add(lblAvgarrive);
						
						JLabel lblNewLabel_1_1 = new JLabel("Avg_Wait");
						lblNewLabel_1_1.setBounds(687, 548, 71, 23);
						contentPane.add(lblNewLabel_1_1);
						
						txtAvg_turn = new JTextField();
						txtAvg_turn.setEditable(false);
						txtAvg_turn.setColumns(10);
						txtAvg_turn.setBackground(Color.WHITE);
						txtAvg_turn.setBounds(756, 495, 120, 22);
						contentPane.add(txtAvg_turn);
						
						txtAvg_wait = new JTextField();
						txtAvg_wait.setEditable(false);
						txtAvg_wait.setColumns(10);
						txtAvg_wait.setBackground(Color.WHITE);
						txtAvg_wait.setBounds(756, 548, 120, 23);
						contentPane.add(txtAvg_wait);
						table_input.setDefaultRenderer(Object.class, formatCell);
					
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEtchedBorder());
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	
	private boolean isExsistID(String id) {
		for (int i=0; i<n; i++)
			if (id.equals(Process[i].id))
				return true;
		return false;
	}
}
