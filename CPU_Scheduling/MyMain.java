package CPU_Scheduling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class MyMain {

	private mainForm Form;
	public static void main(String[] args) {
		MyMain myMain = new MyMain();
		mainForm mainForm_ = new mainForm();
		mainForm_.setForeground(Color.BLACK);
		myMain.Form = mainForm_;
		myMain.Form.setVisible(true);
		myMain.Form.btnRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						if (myMain.Form.cbbAlgorithms.getSelectedItem() == "FCFS")
							new FCFS(myMain.Form.Process, myMain.Form.n, myMain.Form ).solveFCFS();	
						else if (myMain.Form.cbbAlgorithms.getSelectedItem() == "SJF")
							new SJF(myMain.Form.Process, myMain.Form.n, myMain.Form ).Solve_SJF();	
						else if (myMain.Form.cbbAlgorithms.getSelectedItem() == "SRTF")
							new SRTF(myMain.Form.Process, myMain.Form.n, myMain.Form ).SolveSRTF();
						else if (myMain.Form.cbbAlgorithms.getSelectedItem() == "Round Robin") {
							if (!myMain.Form.txtQuantum.getText().isBlank()) {
								try {
									int quantum = Integer.parseInt(myMain.Form.txtQuantum.getText());
									if (quantum < 1)
										JOptionPane.showMessageDialog(null, "It's not a positive number!!!");
									else
										new RoundRobin(myMain.Form.Process, myMain.Form.n, quantum, myMain.Form).Solve_RoundRobin();	
								}
								catch(NumberFormatException e) {
									JOptionPane.showMessageDialog(null, "It's not a positive number!!!");
								}
							}	
							else
								JOptionPane.showMessageDialog(null, "Please input quantum!!!");
						}
						else if (myMain.Form.cbbAlgorithms.getSelectedItem() == "Priority (Non-preemptive)")
							new Priority_NonPreemptive(myMain.Form.Process, myMain.Form.n, myMain.Form ).solvePriority_NonPreemptive();
						else if (myMain.Form.cbbAlgorithms.getSelectedItem() == "Priority (Preemptive)")
							new Priority_Preemptive(myMain.Form.Process, myMain.Form.n, myMain.Form ).solvePriority_Preemptive();
					}
					
				}).start();
				
			}
		});
	}

}
