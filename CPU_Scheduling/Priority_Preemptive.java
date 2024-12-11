package CPU_Scheduling;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class Priority_Preemptive {
	private process Process[] = new process[10000];
	private int n;
	private mainForm Form;
	double avg_wait_time = 0;
	double avg_turnaround_time = 0;

	public void solvePriority_Preemptive() {
		for (int i = 0; i < n; i++) {
			Process[i].remainingTime = Process[i].burstTime;
		}
		int totalFinishProcess = 0; // tổng số tiến trình đã được lập lịch 
		int currentTime = 0;
		int index = n;  // chỉ số của tiến trình lập lịch tiếp theo
		int min = 99999;
		List<process> OutputList = new ArrayList<process>();
		List<process> simuList = new ArrayList<process>();
		while (true) {
			// tất cả tiến trình đã được lập lịch
			if (totalFinishProcess == n)
				break;
			
			for (int i = 0; i < n; i++) {
				if ((Process[i].arriveTime <= currentTime) && (Process[i].isFinish == 0) 
						&& ((Process[i].priority < min) || ((Process[i].priority == min) && (Process[i].arriveTime < Process[index].arriveTime))))  {
					min = Process[i].priority;
					index = i;
					System.out.println("index = " + index);
					System.out.println("min = " + min);
				}
			}
			// không tìm thấy tiến trình có arriveTime hợp lệ
			if (index == n) {
				currentTime++;
			}
			// tìm thấy, tiến trình làm 1s
			else {
				Process[index].remainingTime--;
				
				simuList.add(new process(Process[index].id, currentTime, 1, Process[index].color));
				simuList.get(simuList.size()-1).completionTime = currentTime + 1;
				// tiến trình làm xong
				System.out.println("remaining = " + Process[index].remainingTime);
				if (Process[index].remainingTime == 0) {
					System.out.println(index);
					Process[index].isFinish = 1;
					
					Process[index].completionTime = currentTime + 1;
					Process[index].turnaroundTime = Process[index].completionTime - Process[index].arriveTime;
					Process[index].waitTime = Process[index].turnaroundTime - Process[index].burstTime;
					avg_turnaround_time += Process[index].turnaroundTime;
					avg_wait_time += Process[index].waitTime;
					
					OutputList.add(Process[index]);
					
					totalFinishProcess++;
					index = n;  
					min = 99999;
				}
				currentTime++;
			}
		}
		
		try {

			DefaultTableModel model = (DefaultTableModel) Form.table_output.getModel();
			model.setRowCount(0);

			for (int i = 0; i < OutputList.size(); i++) {
				model = (DefaultTableModel) Form.table_output.getModel();
				Object o[] = new Object[6];
				o[0] = OutputList.get(i).id;
				o[1] = OutputList.get(i).arriveTime;
				o[2] = OutputList.get(i).burstTime;
				o[3] = OutputList.get(i).waitTime;
				o[4] = OutputList.get(i).turnaroundTime;
				o[5] = OutputList.get(i).completionTime;
				model.addRow(o);
			}
			
			DecimalFormat df = new DecimalFormat("#.##");
	        df.setRoundingMode(RoundingMode.CEILING);
			Form.txtAvg_turn.setText(df.format(avg_turnaround_time / n));
			Form.txtAvg_wait.setText(df.format(avg_wait_time / n));
			
			Form.ganttChart.init(simuList.toArray(new process[simuList.size()]), simuList.size());
			Form.ganttChart.simulate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Priority_Preemptive(process[] Process, int n, mainForm Form) {
		this.n = n;
		this.Form = Form;
		for (int i = 0; i < n; i++) {
			this.Process[i] = new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color, Process[i].priority);
		}
	}

}
