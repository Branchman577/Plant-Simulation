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
	private boolean repoflag = false;
	private boolean finrepo =false;
	private Position growto;
	private Position growthpoint;
	private ArrayList<Resource> connectedresources;
	private Model owner;
	public int plant_no;


	public Plant(double fitness_func,double agressiveness,double growth_rate,double resource_conswater,double resource_consiron,double resource_consnitro, int maturity, double mutation,Position pos, Model owner, String name, boolean showInTrace, int num){
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
		this.water = 200.0;
		this.iron = 200.0;
		this.nitro = 200.0;
		this.positions = new ArrayList<Position>();
		this.positions.add(pos);
		this.origin=pos;
		this.growthpoint = this.origin;
		this.plant_no=num;
	}

	public Position Searchfor(int type){
		SimProcess[][] bb = this.simulation.board.boardboi;

		int numedges = positions.size();
		int edgechoice = (int)(Math.random() * ((numedges))) + 0;

		Position edgepoint = this.positions.get(edgechoice);

		int x = edgepoint.Getx();
		int y = edgepoint.Gety();
		while(y <2){
			edgechoice = (int)(Math.random() * ((numedges))) + 0;
			edgepoint = this.positions.get(edgechoice);

			x = edgepoint.Getx();
			y = edgepoint.Gety();

		}

		ArrayList<Position> alreadychecked = new ArrayList<Position>();
		alreadychecked.add(edgepoint);

		ArrayList<Position> needtocheck = new ArrayList<Position>();
		needtocheck.add(new Position(y-1,x));
		needtocheck.add(new Position(y+1,x));
		needtocheck.add(new Position(y,x-1));
		needtocheck.add(new Position(y,x+1));
		//add the first 4 immediate positions around the edgepoint
		
		//int maxchecks = this.simulation.board.Getx()*this.simulation.board.Gety();
		//int numchecks = 0;
		while(needtocheck.size()>0 ){//&& numchecks < maxchecks
			Position checkpos = needtocheck.get(0);
			needtocheck.remove(0);

			int checkx = checkpos.Getx();
			int checky = checkpos.Gety();

		 	if(this.simulation.board.validpos(checkpos)){
		 		boolean notin = false;
		 		if((bb[checky][checkx] instanceof Resource)){
		 			for (Resource conn : connectedresources ) {
		 				if(checkx == conn.x && checky ==conn.y)
		 					notin = true;	
		 			}
		 			if (notin == false){
			 			if (Resourcechecker(type, (Resource)bb[checky][checkx])){
			 				this.growthpoint = edgepoint;
			 				sendTraceNote("The new growthpoint is "+ this.growthpoint);
							return checkpos; // Returning found resource
						}						
		 			}
		 		}
		 	}
		 	alreadychecked.add(checkpos);
	 		Position pos1 = new Position(checky-1,checkx);
	 		Position pos2 = new Position(checky+1,checkx);
	 		Position pos3 = new Position(checky,checkx-1);
	 		Position pos4 = new Position(checky,checkx+1);

	 		boolean foundflag = false;
	 		for ( Position posboi : needtocheck ) {
	 			if(posboi.Gety() == pos1.Gety() && posboi.Getx() == pos1.Getx()){
	 				foundflag = true;
	 				break;
	 			}
 			for (Position posboi2 : alreadychecked ) {
 				if (foundflag == true) {
 					break;	
 				}
 					if(posboi.Gety() == pos1.Gety() && posboi.Getx() == pos1.Getx()){
 						foundflag = true;
 						break;
 					}
 						
 				}
	 		}
	 		if (foundflag == false) {
	 			needtocheck.add(pos1);
	 		}
	 		foundflag = false;

	 		for ( Position posboi : needtocheck ) {
	 			if(posboi.Gety() == pos2.Gety() && posboi.Getx() == pos2.Getx()){
	 				foundflag = true;
	 				break;
	 			}
 			for (Position posboi2 : alreadychecked ) {
 				if (foundflag == true) {
 					break;	
 				}
 					if(posboi.Gety() == pos2.Gety() && posboi.Getx() == pos2.Getx()){
 						foundflag = true;
 						break;
 					}
 						
 				}
	 		}
	 		if (foundflag == false) {
	 			needtocheck.add(pos2);
	 		}
	 		foundflag = false;

	 		for ( Position posboi : needtocheck ) {
	 			if(posboi.Gety() == pos3.Gety() && posboi.Getx() == pos3.Getx()){
	 				foundflag = true;
	 				break;
	 			}
 			for (Position posboi2 : alreadychecked ) {
 				if (foundflag == true) {
 					break;	
 				}
 					if(posboi.Gety() == pos3.Gety() && posboi.Getx() == pos3.Getx()){
 						foundflag = true;
 						break;
 					}
 						
 				}
	 		}
	 		if (foundflag == false) {
	 			needtocheck.add(pos3);
	 		}
	 		foundflag = false;

	 		for ( Position posboi : needtocheck ) {
	 			if(posboi.Gety() == pos4.Gety() && posboi.Getx() == pos4.Getx()){
	 				foundflag = true;
	 				break;
	 			}
 			for (Position posboi2 : alreadychecked ) {
 				if (foundflag == true) {
 					break;	
 				}
 					if(posboi.Gety() == pos4.Gety() && posboi.Getx() == pos4.Getx()){
 						foundflag = true;
 						break;
 					}
 						
 				}
	 		}
	 		if (foundflag == false) {
	 			needtocheck.add(pos4);
	 		}
	 		foundflag = false;
	 		//if (!needtocheck.contains(pos1)!alreadychecked.contains(pos1) && 	
	 		//if (!needtocheck.contains(pos2))needtocheck.add(pos2);
	 		//if (!needtocheck.contains(pos3))needtocheck.add(pos3);
	 		//if (!needtocheck.contains(pos4))needtocheck.add(pos4);
		 	//numchecks += 1;
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

			Position pos1 = new Position(y-1,x);
	 		Position pos2 = new Position(y+1,x);
	 		Position pos3 = new Position(y,x-1);
	 		Position pos4 = new Position(y,x+1);

	 		if (this.simulation.board.validpos(pos1)) {
	 			if (bb[pos1.Gety()][pos1.Getx()] instanceof Resource ) {
	 				if(((Resource)bb[pos1.Gety()][pos1.Getx()]).gettype()!= 5)
	 					connectedresources.add((Resource)bb[pos1.Gety()][pos1.Getx()]);
	 			}
	 			
	 		}
	 		if (this.simulation.board.validpos(pos2)){
	 			if (bb[pos2.Gety()][pos2.Getx()] instanceof Resource && this.simulation.board.validpos(pos2) ) {
		 			if(((Resource)bb[pos2.Gety()][pos2.Getx()]).gettype()!= 5)
		 				connectedresources.add((Resource)bb[pos2.Gety()][pos2.Getx()]);
	 			}
	 		}

	 		if (this.simulation.board.validpos(pos3)){
	 			if (bb[pos3.Gety()][pos3.Getx()] instanceof Resource && this.simulation.board.validpos(pos3)) {
	 				if(((Resource)bb[pos3.Gety()][pos3.Getx()]).gettype()!= 5)
	 					connectedresources.add((Resource)bb[pos3.Gety()][pos3.Getx()]);
	 			}
	 		}
	 		if (this.simulation.board.validpos(pos4)){
	 			if (bb[pos4.Gety()][pos4.Getx()] instanceof Resource && this.simulation.board.validpos(pos4)) {
	 				if(((Resource)bb[pos4.Gety()][pos4.Getx()]).gettype()!= 5)
	 					connectedresources.add((Resource)bb[pos4.Gety()][pos4.Getx()]);
	 			}
	 		}

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
		Position skyPoint = new Position(0,origin.Getx());

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

			if((age>=maturity&&searchflag==true)&& finrepo == false){
				//int maxY=this.simulation.board.sizey;
				Position minY = this.positions.get(0);
				if(repoflag==false){
					for(Position growthPlace:this.positions){
						//System.out.println(growthPlace.Gety() + " " +minY.Gety());
						if(growthPlace.Gety()< minY.Gety()){
							minY=growthPlace;
						}
					}

					repoflag=true;

					Position growthChoice = growthdirection(minY, skyPoint);

					if(growthChoice!= minY){
						Grow(growthChoice);
						if(growthChoice.Gety() == skyPoint.Gety() && growthChoice.Getx()== skyPoint.Getx()){
							sendTraceNote("Ready to repoduce");
							finrepo = true;
						}
					}
					else{
						repoflag = false;

					}

				}
				else{
					Position growthChoice = growthdirection(minY, skyPoint);
					if(growthChoice!=minY){
						Grow(growthChoice);
						if(growthChoice.Gety() == skyPoint.Gety() && growthChoice.Getx()== skyPoint.Getx()){
							sendTraceNote("Ready to repoduce");
							finrepo = true;
					}
					}
					else{
						repoflag = false;

					}
				}

			}

			else if(searchflag == true){

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
					if(growthchoice != growthpoint){
						Grow(growthchoice);
					}
					else{
						growthflag = false;
						searchflag =true;							
					}
				}
				else{
					//connectedresources.add((Resource)this.simulation.board.boardboi[growto.Gety()][growto.Getx()]);
					growthflag = false;
					searchflag = true;
				}

			}
				consumeresources();
				age++;
				System.out.println(presentTime());
				sendTraceNote("Trace Note");
				hold(new TimeSpan(1, TimeUnit.MINUTES));
		}
		sendTraceNote("Plant has died");
		for (Position posdel : this.positions) {
			this.simulation.board.boardboi[posdel.Gety()][posdel.Getx()] = null;
			
			// need to add maturity and growth up, add death and removing of postions on board, add repoducing and next generation
		}
	}
	public void consumeresources(){
		if (connectedresources.size() > 1) {
			for (Resource reboi : connectedresources ) {
				boolean kek = reboi.consume(10.0);
				if (reboi.gettype() == 2 && kek)
					this.water+=10.0;
				else if (reboi.gettype() == 3 && kek)
					this.iron +=10.0;
				else if(kek)
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
			int num = (int)(Math.random() * ((possiblegrowth.size())));//number between 0-3 inclusive
			Position check = possiblegrowth.get(num);

			if (repoflag == true){
				if( simulation.board.validposgrowup(check) && (!(simulation.board.boardboi[check.Gety()][check.Getx()] instanceof Resource) && !(simulation.board.boardboi[check.Gety()][check.Getx()] instanceof Plant)) && (!(simulation.board.boardboi[check.Gety()][check.Getx()] instanceof Root))){
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
			else{
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

		}
		//no choice is shorter but are still valid points not taken up
		if(possiblegrowthcheckedlonger.size()>0){
			return possiblegrowthcheckedlonger.get((int)(Math.random() * ((possiblegrowthcheckedlonger.size()))));
		}
			
		else{
			return growthpoint;
		}
			// it found no possible growthpoints
		}
	}
