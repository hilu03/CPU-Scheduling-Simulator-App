package CPU_Scheduling;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class RoundRobin {
	private process Process[] = new process[10000];
	private int n;
	private int quantum;
	private mainForm Form;
	double avg_wait_time = 0;
	double avg_turnaround_time = 0;

	public void Solve_RoundRobin() {
		for (int i = 0; i < n; i++) {
			Process[i].remainingTime = Process[i].burstTime;
		}
		mergeSort(Process, 0, n - 1); // sap xep theo arrive time
		int totalFinishProcess = 0; // tổng số tiến trình đã được lập lịch
		int currentTime = Process[0].arriveTime; // arriveTime nhỏ nhất

		List<process> OutputList = new ArrayList<process>();
		List<process> simuList = new ArrayList<process>();
		Queue<process> processQueue = new LinkedList<process>();
		
		processQueue.offer(Process[0]); // thêm tiến trình đầu tiên vào hàng đợi


		int index = 0; // chi so cua tien trinh
		while (true) {
			// tất cả tiến trình đã được lập lịch
			if (totalFinishProcess == n)
				break;
			
			process currentProcess = null;
			// hang doi != null
				currentProcess = processQueue.poll();
				
				// remaining time của tiến trình vừa lấy ra khỏi hàng đợi > quantum
				if (currentProcess.remainingTime > quantum) {
					currentProcess.remainingTime -= quantum;
					simuList.add(new process(currentProcess.id, currentTime, quantum, currentProcess.color));
					simuList.get(simuList.size()-1).completionTime = currentTime + quantum;
					currentTime += quantum;
			
					/* tu process tiep theo, process nao da den thi add vao hang doi, khi khong con nua
					   thi add process truoc do (vua la ra khoi hang doi) */
					if (index != n-1) { // nếu ko phải là tiến trình cuối cùng
						for(int i = index+1; i <= n; i++) {
							// tất cả các tiến trình từ 0 -> n-1 đều đã được thêm vào hàng đợi
							if (i == n) {
								processQueue.offer(currentProcess);
								break;
							}
							// tiến trình tiếp theo đã tới khi tiến trình trước đó đã làm việc
							if(Process[i].arriveTime <= currentTime) {
								processQueue.offer(Process[i]);
								index = i;
							}
							// tiến trình tiếp theo chưa tới, thì tiến trình trước đó tiếp tục làm
							else {
								processQueue.offer(currentProcess);
								break;
							}
						}
					}
					// nếu đã là tiến trình cuối cùng, thì tiến trình trước đó tiếp tục add vào hàng đợi cho đến khi làm xong
					else {
						processQueue.offer(currentProcess);
					}
					
				} 
				// remaining time của tiến trình vừa lấy ra khỏi hàng đợi <= quantum  (hay là đã làm xong) 
				else {
					simuList.add(new process(currentProcess.id, currentTime, currentProcess.remainingTime, currentProcess.color));
					simuList.get(simuList.size()-1).completionTime = currentTime + currentProcess.remainingTime;
					currentTime += currentProcess.remainingTime;
					currentProcess.remainingTime = 0;
					currentProcess.isFinish = 1;
					
					currentProcess.completionTime = currentTime;
					currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arriveTime;
					currentProcess.waitTime = currentProcess.turnaroundTime - currentProcess.burstTime;
					
					avg_turnaround_time += currentProcess.turnaroundTime;
					avg_wait_time += currentProcess.waitTime;
					
					OutputList.add(currentProcess);
					
					totalFinishProcess++;
					
					if (index != n-1) {
						for(int i = index+1; i < n; i++) {
							if(Process[i].arriveTime <= currentTime) {
								processQueue.offer(Process[i]);
								index = i;
							}
							/* Trường hợp các tiến trình trước đã làm xong nhưng tiến trình tiếp theo chưa tới
							 lúc đó hàng đợi là rỗng */
							else {
								if (processQueue.isEmpty()) {
									currentTime = Process[i].arriveTime;
									processQueue.offer(Process[i]);
									index = i;
								}
							}
						}
					}
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

	public static void mergeSort(process[] Process, int left, int right) {
		if (left < right) {
			int middle = (left + right) / 2;

			mergeSort(Process, left, middle);
			mergeSort(Process, middle + 1, right);

			merge(Process, left, middle, right);
		}
	}

	public static void merge(process[] Process, int left, int middle, int right) {
		int n1 = middle - left + 1;
		int n2 = right - middle;

		process[] leftArray = new process[n1];
		process[] rightArray = new process[n2];

		for (int i = 0; i < n1; ++i) {
			leftArray[i] = Process[left + i];
		}
		for (int j = 0; j < n2; ++j) {
			rightArray[j] = Process[middle + 1 + j];
		}

		int i = 0, j = 0;
		int k = left;
		while (i < n1 && j < n2) {
			if (leftArray[i].arriveTime <= rightArray[j].arriveTime) {
				Process[k] = leftArray[i];
				i++;
			} else {
				Process[k] = rightArray[j];
				j++;
			}
			k++;
		}

		while (i < n1) {
			Process[k] = leftArray[i];
			i++;
			k++;
		}

		while (j < n2) {
			Process[k] = rightArray[j];
			j++;
			k++;
		}
	}
	
	public RoundRobin(process[] Process, int n, int quantum, mainForm Form) {
		this.n = n;
		this.quantum = quantum;
		this.Form = Form;
		for (int i=0; i<n; i++) {
			this.Process[i] = new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color);
		}
	}
}
