import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Root extends SimProcess {

	public Plant originplant;
	public Position pos;

	public Root(Plant orginplant,Position pos,Model owner, String name, boolean showInTrace){
		super(owner,name,showInTrace);
		this.originplant = orginplant;
		this.pos = pos;

	}

	public void lifeCycle() throws SuspendExecution{
		sendTraceNote("Root has grown into"+this.pos.Gety()+" "+this.pos.Getx());
	}

}