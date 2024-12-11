package CPU_Scheduling;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Priority_NonPreemptive {
	private process Process[] = new process[10000];
	private int n;
	private mainForm Form;
	private double avgwt;
	private double avgta;
	
	public Priority_NonPreemptive(process[] Process, int n, mainForm Form) {
		this.n = n;
		this.Form = Form;
		for (int i=0; i<n; i++) {
			this.Process[i] = new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color, Process[i].priority);
		}
	}
	
	public void solvePriority_NonPreemptive() {
		List<process> outputList = new ArrayList<>();
		
		//sap xep
		for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (Process[i].arriveTime > Process[j].arriveTime) {
                    int temp = Process[i].arriveTime;
                    Process[i].arriveTime = Process[j].arriveTime;
                    Process[j].arriveTime = temp;
                    
                    temp = Process[i].burstTime;
                    Process[i].burstTime = Process[j].burstTime;
                    Process[j].burstTime = temp;
                    
                    temp = Process[i].priority;
                    Process[i].priority = Process[j].priority;
                    Process[j].priority = temp;
                    
                    String tmp = Process[i].id;
                    Process[i].id = Process[j].id;
                    Process[j].id = tmp;
                    
                    Color c = Process[i].color;
                    Process[i].color = Process[j].color;
                    Process[j].color = c;
                }
                else if (Process[i].arriveTime == Process[j].arriveTime && Process[i].priority > Process[j].priority) {
                	int temp = Process[i].arriveTime;
                    Process[i].arriveTime = Process[j].arriveTime;
                    Process[j].arriveTime = temp;
                    
                    temp = Process[i].burstTime;
                    Process[i].burstTime = Process[j].burstTime;
                    Process[j].burstTime = temp;
                    
                    temp = Process[i].priority;
                    Process[i].priority = Process[j].priority;
                    Process[j].priority = temp;
                    
                    String tmp = Process[i].id;
                    Process[i].id = Process[j].id;
                    Process[j].id = tmp;
                    
                    Color c = Process[i].color;
                    Process[i].color = Process[j].color;
                    Process[j].color = c;
                }
            }
        }
		
		int currentTime = 0, completeProcess = 0;
		while(true) {
			if (completeProcess == n)
				break;
			int index = -1, min = 99999;
			for(int i=0;i<n;i++) {
	    		if(Process[i].remainingTime > 0 
	    			&& Process[i].arriveTime <= currentTime 
	    			&& Process[i].priority < min) 
	    		{
                    min = Process[i].priority;
                    index = i;
	    		}
	    	}
			if (index == -1) {
				currentTime++;
				continue;
			}
			Process[index].remainingTime = 0;
	        currentTime += Process[index].burstTime;
	        Process[index].completionTime = currentTime;
	        Process[index].turnaroundTime = Process[index].completionTime - Process[index].arriveTime;
	        Process[index].waitTime = Process[index].turnaroundTime - Process[index].burstTime;
	        avgwt += Process[index].waitTime;
	        avgta += Process[index].turnaroundTime;
	        outputList.add(Process[index]);
	        completeProcess++;
		}
        try {
        	
	        
	        DefaultTableModel model = (DefaultTableModel) Form.table_output.getModel();
			model.setRowCount(0);
			
	        for (int i=0; i<n; i++) {
	        	model = (DefaultTableModel) Form.table_output.getModel();
	        	Object o[] = new Object[6];
	        	o[0] = outputList.get(i).id;
	        	o[1] = outputList.get(i).arriveTime;
	        	o[2] = outputList.get(i).burstTime;
	        	o[3] = outputList.get(i).waitTime;
	        	o[4] = outputList.get(i).turnaroundTime;
	        	o[5] = outputList.get(i).completionTime;
	        	model.addRow(o);
	        }
	        
	        DecimalFormat df = new DecimalFormat("#.##");
	        df.setRoundingMode(RoundingMode.CEILING);
	        Form.txtAvg_turn.setText(df.format(avgta/n));
	        Form.txtAvg_wait.setText(df.format(avgwt/n));
	        Form.ganttChart.init(outputList.toArray(new process[outputList.size()]), n);
            Form.ganttChart.simulate();
	        
            

        }
        
        catch (Exception e) {
//        	e.printStackTrace();
        }
	}
}