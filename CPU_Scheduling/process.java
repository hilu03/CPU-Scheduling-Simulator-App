package CPU_Scheduling;

import java.awt.Color;

public class process {
	public String id;
	public int arriveTime;
	public int burstTime;
	public int startTime;
	public int waitTime;
	public int turnaroundTime;
	public int remainingTime;
	public int priority;
	public int completionTime;
	public int isFinish;
	public Color color;
	
	public process(String id, int arriveTime, int burstTime, Color color, int priority) {
		this.id = id;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
		this.priority = priority;
		this.isFinish = 0;
		this.color = color;
		this.remainingTime = this.burstTime;
	}
	
	public process(String id, int arriveTime, int burstTime, Color color) {
		this.id = id;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
		this.isFinish = 0;
		this.color = color;
	}
	
	
}
