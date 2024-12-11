package CPU_Scheduling;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class SJF {

	private process Process[] = new process[10000];
	private int n = 0;
	private mainForm Form;
	double avg_wait_time = 0;
	double avg_turnaround_time = 0;

	public void Solve_SJF() {
		int totalFinishProcess = 0; // tổng số tiến trình đã được lập lịch
		int currentTime = 0; // thời điểm hiện tại
		List<process> newProcess = new ArrayList<process>();

		while (true) {
			int index = n; // chỉ số của tiến trình lập lịch tiếp theo
			int min = 999999;

			if (totalFinishProcess == n)
				break;

			for (int i = 0; i < n; i++) {
				if ((Process[i].arriveTime <= currentTime) && (Process[i].isFinish == 0)
						&& ((Process[i].burstTime < min) || ((Process[i].burstTime == min) && (Process[i].arriveTime < Process[index].arriveTime)))) {
//                	System.out.print(Process[i].arriveTime + " " );
					min = Process[i].burstTime;
					index = i;
				}
			}

			if (index == n)
				currentTime++;
			else {
				Process[index].completionTime = currentTime + Process[index].burstTime;
				currentTime += Process[index].burstTime;

				Process[index].turnaroundTime = Process[index].completionTime - Process[index].arriveTime;
				Process[index].waitTime = Process[index].turnaroundTime - Process[index].burstTime;
				Process[index].isFinish = 1;
				newProcess.add(Process[index]);
				newProcess.get(newProcess.size() - 1).id = Integer.toString(index + 1);
				totalFinishProcess++;
				avg_turnaround_time += Process[index].turnaroundTime;
				avg_wait_time += Process[index].waitTime;

			}
		}

		try {

			DefaultTableModel model = (DefaultTableModel) Form.table_output.getModel();
			model.setRowCount(0);

			for (int i = 0; i < newProcess.size(); i++) {
				model = (DefaultTableModel) Form.table_output.getModel();
				Object o[] = new Object[6];
				o[0] = newProcess.get(i).id;
				o[1] = newProcess.get(i).arriveTime;
				o[2] = newProcess.get(i).burstTime;
				o[3] = newProcess.get(i).waitTime;
				o[4] = newProcess.get(i).turnaroundTime;
				o[5] = newProcess.get(i).completionTime;
				model.addRow(o);
			}
			
			DecimalFormat df = new DecimalFormat("#.##");
	        df.setRoundingMode(RoundingMode.CEILING);
	        Form.txtAvg_turn.setText(df.format(avg_turnaround_time / n));
	        Form.txtAvg_wait.setText(df.format(avg_wait_time / n));
	        
	        Form.ganttChart.init(newProcess.toArray(new process[newProcess.size()]), newProcess.size());
			Form.ganttChart.simulate();
			
		} catch (Exception e) {
//        	e.printStackTrace();
		}
	}

	public SJF(process[] Process, int n, mainForm Form) {
		this.n = n;
		this.Form = Form;
		for (int i = 0; i < n; i++) {
			this.Process[i] = new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color);
		}
	}
}