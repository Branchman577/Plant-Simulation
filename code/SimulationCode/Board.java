import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Board extends SimProcess{

	public Integer sizex;
	public Integer sizey;
	public Integer noPlants;
	public Integer noWater;
	public Integer noIron;
	public Integer noNitro;
	public SimProcess[][] boardboi;

	public Board( Integer sizey, Integer sizex, Integer noPlants, LaunchSimulation name, String names, boolean showInTrace){
		super(name,names,showInTrace);
		this.sizex = sizex;
		this.sizey = sizey;
		this.noPlants = noPlants;
		int resources = ((sizex * sizey)/16);
		this.noWater = resources;
		this.noIron = resources;
		this.noNitro = resources;
		this.boardboi = makeboard(name);
	}

	public SimProcess[][] makeboard(LaunchSimulation name){
		double plantcount = (double)this.noPlants;
		double plantprob;
		double resourcecount = (plantcount + 2) * 2; // plus 2 for stones
		double resourceprob;

		SimProcess[][] returnarray = new SimProcess[this.sizey +2 ][this.sizex]; // plus 2 for sky

		//placing plants
		int x=1;
		for (int i =2; i < returnarray.length; i++ ) {
			for (int j =0; j < returnarray[i].length; j++ ) {
				plantprob = probplacer(2.0,(double)this.sizex,plantcount,0.3,j,i);

				// System.out.println(plantprob);
				if ((plantprob >=1 || Math.random()< plantprob) && plantcount != 0){
					double mat = Math.random() * 20 + 1;
					int matt = (int)(mat) + 10;
					Position pos = new Position(i,j);
					returnarray[i][j] =  new Plant(Math.random(),Math.random(),Math.random(),Math.random(),Math.random(),Math.random(),matt, Math.random(),pos,name, "Plant", true,x );
					plantcount -= 1;
					x++;
//					returnarray[i][j].activate();
					//System.out.println(returnarray[i][j]);
				}
				else{
					returnarray[i][j] = null;
				}
						
			}
		}
		//Placing resources
		for (int l =4; l < returnarray.length; l++ ) {
			for (int k =0; k < returnarray[l].length; k++ ) {
				resourceprob = probplacer((double)this.sizey,(double)this.sizex,resourcecount,0.3,k,l);

				if (returnarray[l][k]== null) {
					if ((resourceprob >=1 || Math.random()< resourceprob) && resourcecount != 0) {

						int numtype = (int)(Math.random() * ((5 - 2) + 1)) + 2; // random number between 2 and 5 inclusive
						if(numtype == 5)
							returnarray[l][k] = new Resource(5,-1.0,name,"Rock",true,l,k);
						else
							returnarray[l][k] = new Resource(numtype,1000.0,name,"Resource",true,l,k);
//						returnarray[l][k].activate();
						resourcecount -=1;
					}
				}
			}
		}
		return returnarray;
	}
	public double probplacer(double rows, double columns,double numtoplace, double base, int scanx,int scany){
		//System.out.println(rows+" "+columns+" "+numtoplace+" "+base+" "+scanx+" "+scany);
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
		return ( ( (checkpos.Getx() >=0) && (checkpos.Getx() <= this.sizex) ) && ( (checkpos.Gety()>=2) && (checkpos.Gety() <= this.sizey) ) );
	}
	public void lifeCycle() throws SuspendExecution{

		while(true){
			sendTraceNote(generateBoardState());
			hold(new TimeSpan(1, TimeUnit.MINUTES));
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
