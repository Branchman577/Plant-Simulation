import desmoj.core.simulator.*;
import java.util.*;
public class Board{

	public Integer sizex;
	public Integer sizey;
	public Integer noPlants;
	public Integer noWater;
	public Integer noIron;
	public Integer noNitro;
	public SimProcess[][] boardboi;

	public Board( Integer sizey, Integer sizex, Integer noPlants, LaunchSimulation name){
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
		for (int i =2; i < returnarray.length; i++ ) {
			for (int j =0; j < returnarray[i].length; j++ ) {
				plantprob = probplacer(2.0,(double)this.sizex,plantcount,0.3,j,i);

				// System.out.println(plantprob);
				if ((plantprob >=1 || Math.random()< plantprob) && plantcount != 0){
					double mat = Math.random() * 20 + 1;
					int matt = (int)(mat) + 10;
					Position pos = new Position(i,j);
					returnarray[i][j] =  new Plant(Math.random(),Math.random(),Math.random(),Math.random(),Math.random(),Math.random(),matt, Math.random(),pos,name, "Plant", true );
					plantcount -= 1;
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
	
}
