package lumarca.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

//TODO: METHOD NAME IN OBJECT MUST BE UNIQUE

public class MethodAction extends Action{
	
	private Object object;
	private Method method;
	private Object[] args;
	
	private String methodName;
	
	public void init() {
		
		for(Method method: object.getClass().getMethods()){
			if(methodName.equals(method.getName())){
				this.method = method;
				break;
			}
		}
		
		if(method == null){
			System.out.println("Invalid Method Name: "+ methodName);
		}
	}
	
	private void executeMethod(){
		if(method != null){
			try{
				method.invoke(object, args);
			} catch(Exception e){
				System.err.println("MethodName: " + methodName);
				e.printStackTrace();
			}
		}
	}
	
	public Object executeMethodReturn(){
		Object result = null;
		
		if(method != null){
			try{
				result = method.invoke(object, args);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void execute() {
		executeMethod();
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
