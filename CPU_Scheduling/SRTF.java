package CPU_Scheduling;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class SRTF {

	private process Process[] = new process[10000];
	private int n;
	private mainForm Form;
	double avg_wait_time = 0;
	double avg_turnaround_time = 0;

	public void SolveSRTF() {
		int completeProcess = 0, currentimeScheduling = 0, min = 99999;
		int shortestIndex = n;
		boolean hasProcessToExecute = false;
		List<process> OutputList = new ArrayList<process>();
		List<process> simuList = new ArrayList<process>();
		while (completeProcess != n) {

			// tìm tiến trình có remaining time nhỏ nhất để bắt đầu lập lịch
			for (int i = 0; i < n; i++) {
				if ((Process[i].arriveTime <= currentimeScheduling) && (Process[i].isFinish == 0)
						&& (Process[i].remainingTime < min) && (Process[i].remainingTime > 0)) {
					min = Process[i].remainingTime;
					shortestIndex = i;
					hasProcessToExecute = true;
				}
			}

			// trường hợp arrivetime nhỏ nhât khác 0
			if (hasProcessToExecute == false) {
				simuList.add(new process("", currentimeScheduling, 1, Color.white));
				simuList.get(simuList.size()-1).completionTime = currentimeScheduling + 1;
				currentimeScheduling++;
				continue;
			}
			
			Process[shortestIndex].remainingTime--;
			simuList.add(new process(Process[shortestIndex].id, currentimeScheduling, 1, Process[shortestIndex].color));
			simuList.get(simuList.size()-1).completionTime = currentimeScheduling + 1;
			min = Process[shortestIndex].remainingTime;

			if (Process[shortestIndex].remainingTime == 0) {
				min = 99999;
				completeProcess++;
				hasProcessToExecute = false;
				Process[shortestIndex].isFinish = 1;
				Process[shortestIndex].completionTime = currentimeScheduling + 1;
				Process[shortestIndex].turnaroundTime = Process[shortestIndex].completionTime
						- Process[shortestIndex].arriveTime;
				Process[shortestIndex].waitTime = Process[shortestIndex].turnaroundTime
						- Process[shortestIndex].burstTime;
				OutputList.add(Process[shortestIndex]);
				avg_turnaround_time += Process[shortestIndex].turnaroundTime;
				avg_wait_time += Process[shortestIndex].waitTime;
			}

			currentimeScheduling++;

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

	public SRTF(process[] Process, int n, mainForm Form) {
		this.n = n;
		this.Form = Form;
		for (int i = 0; i < n; i++) {
			this.Process[i] = new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color);
			this.Process[i].remainingTime = this.Process[i].burstTime;
		}
	}

}
