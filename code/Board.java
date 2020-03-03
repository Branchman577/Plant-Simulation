import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Board extends SimProcess{

	public Integer sizex;
	public Integer sizey;
	public Integer noPlants;
	public SimProcess[][] boardboi;
	public Integer resources;
	public Integer plantID;
	public ArrayList<Plant> newSeedlings = new ArrayList<Plant>();
	public LaunchSimulation names;

	public Board( Integer sizey, Integer sizex, Integer noPlants, LaunchSimulation name, String desc, boolean showInTrace){
		super(name,desc,showInTrace);
		this.sizex = sizex;
		this.sizey = sizey;
		this.noPlants = noPlants;
		this.resources = ((this.sizex * this.sizey)/16);
		this.plantID=1;
		this.names=name;		
		this.boardboi = makeboard(name);
	}

	public SimProcess[][] makeboard(LaunchSimulation name){
		double plantcount = (double)this.noPlants;
		double plantprob;
		double resourcecount = this.resources;
		double numrocks = this.resources/4;
		double resourceprob;

		SimProcess[][] returnarray = new SimProcess[this.sizey +2 ][this.sizex]; // plus 2 for sky
		//placing plants
		while(plantcount>=1){
			Position posPlant = placeObject(1,3,0,sizex,returnarray);
			double mat = Math.random() * 30 + 1;
			int matt = (int)(mat) + 10;
//			Position pos = new Position(i,j);
			returnarray[posPlant.Gety()][posPlant.Getx()] =  new Plant(Math.random(),Math.random(),Math.random(),Math.random(),Math.random(),matt, Math.random(),posPlant,name, "Plant", true,plantID);
					plantcount -= 1;
					plantID++;
		}
		//Placing resources
		int minwater =1;
		int miniron = 1;
		int minnitro= 1;
		while(resourcecount>0){
			Position posResource= placeObject(2,sizey,0,sizex,returnarray);
			int numtype=(int)(Math.random()*((4-2)+1))+2;
			if(minwater == 1){
				returnarray[posResource.Gety()][posResource.Getx()] = new Resource(2,1000.0,name,"Resource",true,posResource.Gety(),posResource.Getx());//min 1 water tile
				minwater =0;
			}
			else if(miniron == 1){
				returnarray[posResource.Gety()][posResource.Getx()] = new Resource(3,1000.0,name,"Resource",true,posResource.Gety(),posResource.Getx());//min 1 iron tile
				miniron =0;
			}
			else if (minnitro == 1){
				returnarray[posResource.Gety()][posResource.Getx()] = new Resource(4,1000.0,name,"Resource",true,posResource.Gety(),posResource.Getx());//min 1 iron tile
				minnitro =0;
			}
			else if ((minwater ==0 && miniron == 0) && minnitro== 0) {
				returnarray[posResource.Gety()][posResource.Getx()] = new Resource(numtype,1000.0,name,"Resource",true,posResource.Gety(),posResource.Getx());//random choice the rest of resources
			}
			resourcecount--;
		}
		//placing rocks
		while(numrocks>0){
			Position posRock = placeObject(2,sizey,0,sizex,returnarray);
			returnarray[posRock.Gety()][posRock.Getx()]=new Resource(5,1000.0,name,"Rock",true,posRock.Gety(),posRock.Getx());
			numrocks--;
		}
		return returnarray;
	}
	
	public Position placeObject(int min_y, int max_y, int min_x,int max_x, SimProcess[][] array){
		int newX=-1;
		int newY=-1;
		int counter=0;
		while((newX<0||newX>sizex-1)||(newY<=min_y||newY>max_y)||array[newY][newX]!=null&&counter!=50){
			newX = (int)((Math.random()*max_x+1));//+();
			newY = (int)((Math.random()*(max_y-min_y)+1)+min_y);
			counter++;

		}
		if(counter==50){return null;}
		return new Position(newY,newX);
	}
	public double probplacer(double rows, double columns,double numtoplace, double base, int scanx,int scany){
		double num_of_tiles = (columns - scanx) + (columns *((rows-1.0)-(scany-2)));
		return (1)-(((num_of_tiles-numtoplace)/(rows*columns)));

	}
	public Integer Gety(){
		return this.sizey;
	}

	public Integer Getx(){
		return this.sizex;
	}

	public int distance(Position growthpoint, Position growto){
		return((Math.abs(growthpoint.Getx()-growto.Getx()))+Math.abs((growthpoint.Gety()-growto.Gety())));
	}
	public boolean validpos(Position checkpos){
		return ( ( (checkpos.Getx() >=0) && (checkpos.Getx() < this.sizex) ) && ( (checkpos.Gety()>=2) && (checkpos.Gety() < this.sizey) ) );
	}
	public boolean validposgrowup(Position checkpos){
		return ( ( (checkpos.Getx() >=0) && (checkpos.Getx() < this.sizex) ) && ( (checkpos.Gety()>=0) && (checkpos.Gety() < this.sizey) ) );
	}
	public void addToList(Plant plant){
		newSeedlings.add(plant);
	}

	public void lifeCycle() throws SuspendExecution{

		while(true){
			sendTraceNote(generateBoardState());
			hold(new TimeSpan(1, TimeUnit.MINUTES));
			for(Plant seedling: newSeedlings){
				if(boardboi[seedling.origin.Gety()][seedling.origin.Getx()]==seedling){
					seedling.activate();
				}
			}
			newSeedlings = new ArrayList<Plant>();

		}
	}
	public String generateBoardState(){
		String output="";
		int i=1;
		for (SimProcess[] layer: boardboi){

			for (SimProcess item:layer){
				if (item instanceof Resource){
					output=output+"||"+item.toString();
				}
				else if (item instanceof Plant){
					Plant temp = (Plant)item;
					output=output+"||P"+temp.plant_no;
				}
				else if(item instanceof Root){
					Root temp = (Root)item;
					output=output+"||R"+temp.originplant.plant_no;
				}		
				else{output=output+"||"+null;}
			}
			output=output+"##";
			i++;
		}
		return output;
	}
	
}
