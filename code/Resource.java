import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;
import java.util.concurrent.TimeUnit;
import java.util.*;
public class Resource extends SimProcess{

	public double capacity;
	public LaunchSimulation simulation;
	private int typeflag; // 2 = water, 3 = iron, 4 = nitrogen , 5 = stone
	public int y;
	public int x;
	public Model owner;
	public Resource(int type, double cap, Model owner, String name, boolean showInTrace,int y, int x){
		super(owner,name,showInTrace);
		this.owner=owner;
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
	public void lifeCycle() throws SuspendExecution{
		sendTraceNote(x+" "+y);
		if(capacity==-1){
			while(true){hold(new TimeSpan(1, TimeUnit.MINUTES));}
		}
		while(capacity>0){

			hold(new TimeSpan(1, TimeUnit.MINUTES));
		}
		if (Math.random()<0.5){
			simulation.board.boardboi[y][x]= new Resource(typeflag, 1000.0, owner, "Resource", true, y,x);
			simulation.board.boardboi[y][x].activate();
				
		}
		else{simulation.board.boardboi[y][x]=null;}
	}
}
