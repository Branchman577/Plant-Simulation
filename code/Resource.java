import desmoj.core.simulator.*;
public class Resource extends SimProcess{

	public double capacity;
	public LaunchSimulation simulation;
	private int typeflag; // 2 = water, 3 = iron, 4 = nitrogen , 5 = stone
	public int y;
	public int x;
	public Resource(int type, double cap, Model owner, String name, boolean showInTrace,int y, int x){
		super(owner,name,showInTrace);
		this.simulation=(LaunchSimulation)owner;
		this.typeflag = type;
		this.capacity = cap; 
		this.y=y;
		this.x=x;
	}

	public String toString(){
		if(typeflag == 2)
			return "Water";
		if(typeflag == 3)
			return "Iron";
		if(typeflag == 4)
			return "Nitro";
		else
			return "Stone";
		}
	public double getcap(){
		return this.capacity;
	}
	public int gettype(){
		return this.typeflag;
		}
	public void lifeCycle(){
		sendTraceNote(x+" "+y);
	}
}
