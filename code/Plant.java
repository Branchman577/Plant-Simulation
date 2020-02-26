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
	public Position origin;
	public Position growingTo;
	public LaunchSimulation simulation;
	private int typeflag = 1;
	private boolean growthflag = false;
	private boolean searchflag = true;
	private Position growto;
	private Position growthpoint;
	private ArrayList<Resource> connectedresources;
	private Model owner;


	public Plant(double fitness_func,double agressiveness,double growth_rate,double resource_conswater,double resource_consiron,double resource_consnitro, int maturity, double mutation,Position pos, Model owner, String name, boolean showInTrace){
		super(owner,name,showInTrace);
		this.owner = owner;
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
		this.origin=pos;
		this.growthpoint = this.origin;

	}

	public Position Searchfor(int type){
		SimProcess[][] bb = this.simulation.board.boardboi;

		int numedges = positions.size();
		int edgechoice = (int)(Math.random() * ((numedges))) + 0;
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
		//add the first 4 immediate positions around the edgepoint
		
		int maxchecks = this.simulation.board.Getx()*this.simulation.board.Gety();
		int numchecks = 0;
		while(needtocheck.size()>0 && numchecks < maxchecks){
			Position checkpos = needtocheck.get(0);
			needtocheck.remove(0);
			int checkx = checkpos.Getx();
			int checky = checkpos.Gety();

		 	if( (checky > 1 && checky < this.simulation.board.Gety()) && (checkx > 0 && checkx < this.simulation.board.Getx()) ){

		 		if((bb[checky][checkx] instanceof Resource)){
		 			if (!(connectedresources.contains(bb[checky][checkx])) ){
	
			 			if (Resourcechecker(type, (Resource)bb[checky][checkx])){
			 				this.growthpoint = edgepoint;
			 				sendTraceNote("The new growthpoint is "+ this.growthpoint);
							return checkpos; // Returning found resource
						}						
		 			}
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

		SimProcess[][] bb = this.simulation.board.boardboi;
		int x = posg.Getx();
		int y = posg.Gety();
		//if ((!(bb[y][x] instanceof Resource) && !(bb[y][x] instanceof Plant)) && (!(bb[y][x] instanceof Root)))  {
			this.positions.add(posg);
			bb[y][x] = new Root(this,posg,this.owner, "Root", false);
			this.growthpoint = posg;
			sendTraceNote("Made root at position" + " " + this.growthpoint);
			//return true;
		//}
		//return false;
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
		connectedresources = new ArrayList<Resource>();
		Position resourceboi = Searchfor(lowestresource());

		if(positions.contains(resourceboi)){
			sendTraceNote("Resource not found");
		}
		else{
			sendTraceNote("Resource is at "+resourceboi.Gety()+ " "+resourceboi.Getx());
			growthflag = true;
			searchflag = false;
			this.growto = resourceboi;
		}
	
		while( (this.water> this.resourceconw * this.positions.size()&& this.iron>this.resourceconi * this.positions.size()) && this.nitro>this.resourceconn * this.positions.size()){
			this.water =  this.water - (this.resourceconw * this.positions.size());
			this.iron = this.iron - (this.resourceconi * this.positions.size());
			this.nitro = this.nitro -(this.resourceconn * this.positions.size());
				if(searchflag == true){

					resourceboi = Searchfor(lowestresource());
					if(positions.contains(resourceboi)){
						sendTraceNote("Resource not found");
					}
					else{
						sendTraceNote("Resource is at "+resourceboi.Gety()+ " "+resourceboi.Getx());
						growthflag = true;
						searchflag = false;
						this.growto = resourceboi;
					}

				}
				else{
					if(simulation.board.distance(this.growthpoint,growto) >1){
						Position growthchoice= growthdirection(this.growthpoint,growto);
						if(growthchoice != growthpoint)
							Grow(growthchoice);
					}
					else{
						connectedresources.add((Resource)this.simulation.board.boardboi[growto.Gety()][growto.Getx()]);
						growthflag = false;
						searchflag = true;
					}

				}
				consumeresources();		
				System.out.println(presentTime());
				sendTraceNote("Trace Note");
				hold(new TimeSpan(1, TimeUnit.MINUTES));
		}
			// need to add maturity and growth up, add death and removing of postions on board, add repoducing and next generation

		
	}
	public void consumeresources(){
		if (connectedresources.size() > 1) {
			for (Resource reboi : connectedresources ) {
				reboi.consume(10.0);
				if (reboi.gettype() == 2)
					this.water+=10.0;
				else if (reboi.gettype() == 3)
					this.iron +=10.0;
				else
					this.nitro += 10.0;	
			}
			
		}


	}
	public int lowestresource(){

		if(this.water <= this.iron && this.water <= this.nitro)
			return 2;
		else if (this.iron <= this.water && this.iron <= this.nitro) 
			return 3;
		else
			return 4;
	}
	public Position growthdirection(Position growthpoint, Position growto){//returns the next point to grow to
		int growthpointx = growthpoint.Getx();
		int growthpointy = growthpoint.Gety();

		int distancetogrow = simulation.board.distance(growthpoint,growto);
		ArrayList<Position> possiblegrowth = new ArrayList<Position>();
		ArrayList<Position> possiblegrowthcheckedlonger = new ArrayList<Position>();

		possiblegrowth.add(new Position(growthpointy+1,growthpointx));
		possiblegrowth.add(new Position(growthpointy-1,growthpointx));
		possiblegrowth.add(new Position(growthpointy,growthpointx+1));
		possiblegrowth.add(new Position(growthpointy,growthpointx-1));

		while (possiblegrowth.size() != 0){
			int num = (int)(Math.random() * ((possiblegrowth.size()-1)));//number between 0-3 inclusive
			Position check = possiblegrowth.get(num);

			if( simulation.board.validpos(check) && (!(simulation.board.boardboi[check.Gety()][check.Getx()] instanceof Resource) && !(simulation.board.boardboi[check.Gety()][check.Getx()] instanceof Plant)) && (!(simulation.board.boardboi[check.Gety()][check.Getx()] instanceof Root))){
				if (simulation.board.distance(check,growto) < distancetogrow){
					return check;
				}
				else{
					possiblegrowthcheckedlonger.add(check);
					possiblegrowth.remove(check);
				}
			}
			else{
				possiblegrowth.remove(check);
			}

		}
		//no choice is shorter but are still valid points not taken up
		if(possiblegrowthcheckedlonger.size()>0){
			return possiblegrowthcheckedlonger.get((int)(Math.random() * ((possiblegrowthcheckedlonger.size()-1))));
		}
			
		else{
			return growthpoint;
		}
			// it found no possible growthpoints
		}
	}
