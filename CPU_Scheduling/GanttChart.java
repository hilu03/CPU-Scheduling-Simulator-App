package CPU_Scheduling;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class GanttChart extends JPanel{

	public List<process> processes; // Danh sách các tiến trình cần mô phỏng
    public int currentProcessIndex = -1; // Chỉ số của tiến trình hiện tại
    public int n = 0;
    public boolean isRunning;
     
    public GanttChart() {
    	
    }

    public void init(process[] Process, int n) {
    	this.n = n;
    	processes = new ArrayList<process>();
        for (int i=0; i<n; i++) {
        	if (i==0 && Process[i].arriveTime != 0)
        		processes.add(new process("", 0, Process[i].arriveTime, Color.white));
        	else if (i != 0 && Process[i-1].completionTime < Process[i].arriveTime)
        		processes.add(new process("", Process[i-1].completionTime, Process[i].arriveTime - Process[i-1].completionTime, Color.white));
        	processes.add(new process(Process[i].id, Process[i].arriveTime, Process[i].burstTime, Process[i].color));
        	processes.get(processes.size()-1).completionTime = Process[i].completionTime;
        }
        this.currentProcessIndex = 0;
        isRunning = true;
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (processes != null) {
        	return new Dimension(processes.get(processes.size()-1).completionTime * 20 + 30, 130);
        }
        return new Dimension(850, 130);
    }
    


    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isRunning) {
        	int x = 0;
    		for (int i=0; i<=currentProcessIndex && i < processes.size(); i++) {
    			
    			g.setColor(processes.get(i).color);
            	g.fillRect(x, 0, processes.get(i).burstTime * 20, 100);
            	
            	g.setColor(Color.BLACK);
            	g.drawString(processes.get(i).id, x, 50);
            	g.drawLine(x, 100, x + processes.get(i).burstTime * 20, 100);
            	if (i != 0 && !processes.get(i).id.equals("") && processes.get(i-1).id.equals(""))
            		g.drawString(Integer.toString(processes.get(i).arriveTime), x-3, 110);
            	
            	x += processes.get(i).burstTime * 20;
            	if (!processes.get(i).id.equals(""))
            		g.drawString(Integer.toString(processes.get(i).completionTime), x-3, 110);
    		}
    		currentProcessIndex++;
        }
        else if (processes != null) {
        	int x = 0;
        	for (int i=0; i < processes.size(); i++) {
    			
    			g.setColor(processes.get(i).color);
            	g.fillRect(x, 0, processes.get(i).burstTime * 20, 100);
            	
            	g.setColor(Color.BLACK);
            	g.drawString(processes.get(i).id, x, 50);
            	g.drawLine(x, 100, x + processes.get(i).burstTime * 20, 100);
            	if (i != 0 && !processes.get(i).id.equals("") && processes.get(i-1).id.equals(""))
            		g.drawString(Integer.toString(processes.get(i).arriveTime), x-3, 110);
            	
            	x += processes.get(i).burstTime * 20;
            	if (!processes.get(i).id.equals(""))
            		g.drawString(Integer.toString(processes.get(i).completionTime), x-3, 110);
    		}
        }
    }
    
    public void simulate() {
    	for (process p: processes) {
    		try {
//    			repaint();
    			SwingUtilities.invokeLater(() -> repaint());
				Thread.sleep(p.burstTime * 300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	isRunning = false;
    	
    }
}
