public class Position{
	Integer y;
	Integer x;
	public Position(Integer ypoint, Integer xpoint){
		this.y = ypoint;
		this.x = xpoint;
	}

	public Integer Gety(){
		return this.y;
	}

	public Integer Getx(){
		return this.x;
	}

	public String toString(){
		return Integer.toString(y) + " " + Integer.toString(x);
	}
}
