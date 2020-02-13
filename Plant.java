import java.util.*;
public class Plant extends Tile{

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

	private Integer typeflag = 1;


	public Plant(double fitness_func,double agressiveness,double growth_rate,double resource_conswater,double resource_consiron,double resource_consnitro, int maturity, double mutation,Position pos){
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


	}

	public void Searchfor(int type, Board board, Position positiontosearchfrom)
	{
		// will be implemented soon
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
}