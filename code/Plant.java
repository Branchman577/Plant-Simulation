import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Plant extends SimProcess {

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
	public int plant_no;


	public Plant(double agressiveness,double growth_rate,double resource_conswater,double resource_consiron,double resource_consnitro, int maturity, double mutation,Position pos, Model owner, String name, boolean showInTrace, int num){
		super(owner,name,showInTrace);
		this.owner = owner;
		this.simulation=(LaunchSimulation)owner;
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
		this.plant_no=num;
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
		
		//int maxchecks = this.simulation.board.Getx()*this.simulation.board.Gety();
		//int numchecks = 0;
		while(needtocheck.size()>0 ){//&& numchecks < maxchecks
			Position checkpos = needtocheck.get(0);
			needtocheck.remove(0);

			int checkx = checkpos.Getx();
			int checky = checkpos.Gety();

		 	if(this.simulation.board.validpos(checkpos)){

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
		ArrayList<Plant> newPlants= new ArrayList<Plant>();
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
						if(growthchoice != growthpoint){
							Grow(growthchoice);
						}
						else{
							growthflag = false;
							searchflag =true;							
						}
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
		sendTraceNote("Plant has died");

		for (Position posdel : this.positions) {
			this.simulation.board.boardboi[posdel.Gety()][posdel.Getx()] = null;
			
			// need to add maturity and growth up, add death and removing of postions on board, add repoducing and next generation
		}
		System.out.println(plant_no+" "+fitness()+" "+agress+" "+(((fitness()+agress)/2))+" "+newSeedlingPlace(this.origin));
		
		for(Plant seedling: newPlants){
			seedling.activate();
		}
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
			int num = (int)(Math.random() * ((possiblegrowth.size())));//number between 0-3 inclusive
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
			return possiblegrowthcheckedlonger.get((int)(Math.random() * ((possiblegrowthcheckedlonger.size()))));
		}
			
		else{
			return growthpoint;
		}
			// it found no possible growthpoints
		}
	
	public ArrayList<Plant> generateSeedlings(Plant plant2){
		double newAgress;
		double newGrowthRate;
		double newWCons;
		double newICons;
		double newNCons;
		int newMat;
		double newMut;
		ArrayList<Plant> seedlings = new ArrayList<Plant>();
		double numberOfSeeds= Math.floor(2*fitness());
		double carryOverFactP1= ((fitness()+agress)/2);
		double carryOverFactP2= ((plant2.fitness()+plant2.agress)/2);
		double p1Portion = 0.6*carryOverFactP1;
		double p2Portion = 0.4*carryOverFactP2;
		double toBeFilled = 1-(p1Portion+p2Portion);
		if(agress>plant2.agress){p1Portion+=toBeFilled;}
		else{p2Portion+=toBeFilled;}
		int i=0;
		while(i<numberOfSeeds){//Stuff to modify Agressiveness, Growth_Rate, water consump, iron consump, nit consump, maturity, 
			if(Math.random()<mutation){ newAgress= Math.random();}
			else{ newAgress= newGeneValue(agress, agress, plant2.agress,plant2.agress);}
			if(Math.random()<mutation){ newGrowthRate = Math.random();}
			else{ newGrowthRate = newGeneValue(growth, agress,plant2.growth,plant2.agress);}
			if(Math.random()<mutation){newWCons=Math.random();}	
			else{newWCons= newGeneValue(resourceconw, agress, plant2.resourceconw,plant2.resourceconw);}
			if(Math.random()<mutation){newICons= Math.random();}
			else{newICons=newGeneValue(resourceconi, agress,plant2.resourceconw,plant2.agress);}
			if(Math.random()<mutation){newNCons = Math.random();}
			else{newNCons= newGeneValue(resourceconn, agress,plant2.resourceconn,plant2.agress);}
			if(Math.random()<mutation){newMat = (int)(Math.random()*20+1)+10;}
			else{newMat = (int)(Math.floor(newGeneValue(maturity, agress,plant2.maturity,plant2.agress)));}
			if(Math.random()<mutation){newMut = Math.random();}
			else{ newMut= newGeneValue(mutation,agress,plant2.mutation,plant2.agress);}
			Position newOrigin= newSeedlingPlace(this.origin);
			Plant seedling = new Plant(newAgress, newGrowthRate,newWCons,newICons,newNCons,newMat,newMut, newOrigin, simulation,"Plant", true,simulation.board.plantID);
			simulation.board.boardboi[newOrigin.Gety()][newOrigin.Getx()]= seedling;
			seedlings.add(seedling);
			//	public Plant(double agressiveness,double growth_rate,double resource_conswater,double resource_consiron,double resource_consnitro, int maturity, double mutation,Position pos, Model owner, String name, boolean showInTrace, int num){
			simulation.board.plantID++;
			i++;
		}
		
		return seedlings;
	}
	
	public double fitness(){
		return (this.connectedresources.size()*10)/(this.positions.size()*(this.resourceconw+this.resourceconi+resourceconn));
	}
	public double newGeneValue(double v1,double ag1,double v2,double ag2){
		if(v1>v2){
			return v2*(1+ag1);
		}
		return 	v1*(1+ag2);		
	}	
	public Position newSeedlingPlace(Position origin){
		int newXPos=-1;
		int newYPos=-1;
		while((newXPos<0||newXPos>simulation.board.sizex-1)||(newYPos<2||newYPos>4)||simulation.board.boardboi[newYPos][newXPos]!=null){
			newXPos = (int)((Math.random()*5+1)+Math.ceil(origin.Getx()/2));
			newYPos = (int)((Math.random()*2+1)+1);
		}
		return new Position(newXPos,newYPos);
	}
	public Position findMate(skyPoint){
		int increment=1;
		while((skyPoint.Getx()-increment>=0)||(skyPoint.Getx()+increment<simulation.board.sizex)){
			if(skyPoint.Getx()+increment<simulation.board.sizex&&(simulation.board.boardboi[0][skyPoint.Getx()+increment]!=null)){return new Position(skyPoint.Getx()+increment,0);}
			else if(skyPoint.Getx()-increment>=0&&simulation.board.boardboi[0][skyPoint.Getx()-increment]!=null){return new Position(skyPoint.Getx()-increment,0);}
			increment++;
		}	
		
	}
}
