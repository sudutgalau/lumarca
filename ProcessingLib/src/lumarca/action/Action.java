package lumarca.action;

public class Action{

	protected boolean init = false;
	
	public void init(){
		init = true;
	}
	
	public void execute() {
		System.out.println("DEFAULT ACTION");
	}
	
	public static void executeAction(Action action){
		if(action != null){
			if(!action.init)
				action.init();
			action.execute();
		}
	}
}
