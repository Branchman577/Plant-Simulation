public class Resource extends Tile{

	public double capacity;

	private int typeflag; // 2 = water, 3 = iron, 4 = nitrogen , 5 = stone

	public Resource(int type, double cap){
		this.typeflag = type;
		this.capacity = cap; 
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
}