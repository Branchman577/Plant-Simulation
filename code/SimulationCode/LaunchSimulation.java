import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.concurrent.TimeUnit;


public class LaunchSimulation extends Model{
	public Board board;
	public int size_x=10;
	public int size_y=9;
	public int noplant=3;
	public LaunchSimulation(Model owner, String Name, boolean showInReport, boolean showInTrace){
		super(owner, Name, showInReport, showInTrace);
	}
	public static void main(String [] args){
		//setting up the simulation
		LaunchSimulation base = new LaunchSimulation(null, "Plant_Simulation", true,true);
		Experiment exp = new Experiment("Results");
		base.connectToExperiment(exp);
		//enable a progress bar to be shown
		exp.setShowProgressBar(true);
		//set how long the simulation will run for
		exp.stop(new TimeInstant(1500, TimeUnit.MINUTES));
		//set how long the debug and trace files will be recorded for
		exp.tracePeriod(new TimeInstant(0), new TimeInstant(100, TimeUnit.MINUTES));
		exp.debugPeriod(new TimeInstant(0), new TimeInstant(50, TimeUnit.MINUTES));
		exp.start();
		exp.report();
		exp.finish();
		
	}

	public String description(){return "This is a third year project Plant Simulation by Nevan and Gergely";}
	public void doInitialSchedules(){
		board.activate();
		sendTraceNote("Board size: "+size_x+" "+size_y+" "+noplant);
		for(SimProcess[] row:board.boardboi){
			for(SimProcess object:row){
				if (object!=null){object.activate();}
			}
		}
	}
	//Initialise all static objects
	public void init(){
		
		board = new Board(size_x, size_y, noplant, this, "Board", true);
		
	}
}
