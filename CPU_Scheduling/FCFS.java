package CPU_Scheduling;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class FCFS {
	
	private process Process[] = new process[10000];
	private int n;
	private mainForm Form;
	private double avgwt;
	private double avgta;
	
	
	public void solveFCFS() {
		mergeSort(Process, 0, n-1);
		
        for (int i = 0; i < n; i++) {
            if (i == 0) {
            	Process[i].completionTime = Process[i].arriveTime + Process[i].burstTime;
            } else {
                if (Process[i].arriveTime > Process[i - 1].completionTime) {
                	Process[i].completionTime = Process[i].arriveTime + Process[i].burstTime;
                } else
                	Process[i].completionTime = Process[i - 1].completionTime + Process[i].burstTime;
            }
            Process[i].turnaroundTime = Process[i].completionTime - Process[i].arriveTime;
            Process[i].waitTime = Process[i].turnaroundTime - Process[i].burstTime;
            avgwt += Process[i].waitTime;
            avgta += Process[i].turnaroundTime;
        }
        try {
        	
//	        Form.ganttChart.init(Process, n);
//            Form.ganttChart.simulate();
	        
	        DefaultTableModel model = (DefaultTableModel) Form.table_output.getModel();
			model.setRowCount(0);
			
	        for (int i=0; i<n; i++) {
	        	model = (DefaultTableModel) Form.table_output.getModel();
	        	Object o[] = new Object[6];
	        	o[0] = Process[i].id;
	        	o[1] = Process[i].arriveTime;
	        	o[2] = Process[i].burstTime;
	        	o[3] = Process[i].waitTime;
	        	o[4] = Process[i].turnaroundTime;
	        	o[5] = Process[i].completionTime;
	        	model.addRow(o);
	        }
	        
	        DecimalFormat df = new DecimalFormat("#.##");
	        df.setRoundingMode(RoundingMode.CEILING);
	        Form.txtAvg_turn.setText(df.format(avgta/n));
	        Form.txtAvg_wait.setText(df.format(avgwt/n));
	        Form.ganttChart.init(Process, n);
            Form.ganttChart.simulate();
	        
            

        }
        
        catch (Exception e) {
//        	e.printStackTrace();
        }
	}

	public FCFS(process[] Process, int n, mainForm Form) {
		this.n = n;
		this.Form = Form;
		for (int i=0; i<n; i++) {
			this.Process[i] = new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color);
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
	
	
}
