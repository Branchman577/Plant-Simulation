import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Plant extends SimProcess {

	public double fitness;
	public double agress;
	public double growth;
	public double resourceconw;
	public double resourceconi;
	public double resourceconn;
	public int age;
	public int size;
	public int maturity;
	public double mutation;
	public double water;
	public double iron;
	public double nitro;
	public ArrayList<Position> positions;
	public ArrayList<Position> edges;
	public Position origin;
	public Position growingTo;
	public LaunchSimulation simulation;
	private Integer typeflag = 1;


	public Plant(double fitness_func,double agressiveness,double growth_rate,double resource_conswater,double resource_consiron,double resource_consnitro, int maturity, double mutation,Position pos, Model owner, String name, boolean showInTrace){
		super(owner,name,showInTrace);
		this.simulation=(LaunchSimulation)owner;
		this.fitness = fitness_func;
		this.agress = agressiveness;
		this.growth = growth_rate;
		this.resourceconw = resource_conswater;
		this.resourceconi = resource_consiron;
		this.resourceconn = resource_consnitro;
		this.age = 0;
		this.size = 1;
		this.maturity = maturity;
		this.mutation = mutation;
		this.water = 100.0;
		this.iron = 100.0;
		this.nitro = 100.0;
		this.positions = new ArrayList<Position>();
		this.positions.add(pos);
		this.edges = new ArrayList<Position>();
		this.edges.add(pos);
		this.origin=pos;

	}

	public Position Searchfor(int type)
	{
		SimProcess[][] bb = this.simulation.board.boardboi;

		int numedges = edges.size();
		int edgechoice = (int)(Math.random() * ((edges.size()))) + 0;
		Position edgepoint = this.positions.get(edgechoice);

		int x = edgepoint.Getx();
		int y = edgepoint.Gety();
		ArrayList<Position> alreadychecked = new ArrayList<Position>();
		alreadychecked.add(edgepoint);

		ArrayList<Position> needtocheck = new ArrayList<Position>();
		needtocheck.add(new Position(y-1,x));
		needtocheck.add(new Position(y+1,x));
		needtocheck.add(new Position(y,x-1));
		needtocheck.add(new Position(y,x+1));
		//add the fiest 4 immediate positions around the edgepoint
		
		int maxchecks = this.simulation.board.Getx()*this.simulation.board.Gety();
		int numchecks = 0;
		while(needtocheck.size()>0 && numchecks < maxchecks){
			Position checkpos = needtocheck.get(0);
			needtocheck.remove(0);
			int checkx = checkpos.Getx();
			int checky = checkpos.Gety();

		 	if( (checky > 1 && checky < this.simulation.board.Gety()) && (checkx > 0 && checkx < this.simulation.board.Getx()) ){

		 		if(bb[checky][checkx] instanceof Resource){
		 			if (Resourcechecker(type, (Resource)bb[checky][checkx]))
						return checkpos; 						
		 		}
		 		alreadychecked.add(checkpos);
		 		Position pos1 = new Position(checky-1,checkx);
		 		Position pos2 = new Position(checky+1,checkx);
		 		Position pos3 = new Position(checky,checkx-1);
		 		Position pos4 = new Position(checky,checkx+1);
		 		if ( !alreadychecked.contains(pos1) && !needtocheck.contains(pos1))
		 			needtocheck.add(pos1);
		 		if ( !alreadychecked.contains(pos2) && !needtocheck.contains(pos2))
		 			needtocheck.add(pos2);
		 		if ( !alreadychecked.contains(pos3) && !needtocheck.contains(pos3))
		 			needtocheck.add(pos3);
		 		if ( !alreadychecked.contains(pos4) && !needtocheck.contains(pos4))
		 			needtocheck.add(pos4);
		 	numchecks += 1;

		 	} 
		}
		return edgepoint; // returns chosen edgepoint if it fails
	}

	public boolean Resourcechecker(int t, Resource re){
		if(t == re.gettype())
			return true;
		else
			return false;
	}



	public void Grow(Position posg){
		this.positions.add(posg);
		// needs to be edited to grow
	}

	public int gettype(){
		return this.typeflag;
		}

	public String toString(){
		return "Plant";
	}
	public void lifeCycle() throws SuspendExecution{
		sendTraceNote("Plant is planted "+this.origin.Gety()+" "+this.origin.Getx());
		Board board = simulation.board;
		Position waterboi = Searchfor(2);
		sendTraceNote("Water is at "+waterboi.Getx()+ " "+waterboi.Gety());
		while(true){
			System.out.println(presentTime());
			sendTraceNote("Sent a trace note");
			hold(new TimeSpan(1, TimeUnit.MINUTES));
		}
//		while(this.water!=0&&this.iron!=0&&this.nitro!=0&&this.age<this.maturity+10){
//			System.out.println("works?");
//		}
//		if (Math.random()<=0.5){
	//		Board.boardboi[this.origin.Gety()][this.origin.Getx()]=new Resource(5,-1.0);
		//}
		
	}
}
