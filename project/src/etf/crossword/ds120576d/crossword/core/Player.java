package etf.crossword.ds120576d.crossword.core;

import java.util.LinkedList;

import com.sun.accessibility.internal.resources.accessibility;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public abstract class Player {

	/** The no threads that game can use with this player. */
	protected  int noThreads;
	
	/** The delay between steps. */
	protected int delay = 0;
	
	/**
	 * Started action.
	 */
	public void started() {
		System.out.println("Started!");
	}
	
	/**
	 * Started action with custom string.
	 *
	 * @param s the s
	 */
	public void started(String s) {
		System.out.println(s);
	}
	
	/**
	 * Sets the delay.
	 *
	 * @param delay the new delay
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	/**
	 * Gets the delay.
	 *
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * Plays faster.
	 */
	public void playFaster() {}
	
	/**
	 * Plays slower.
	 */
	public void playSlower() {}
	
	
	/**
	 * Finish action.
	 */
	public void finish(){}
	
	/**
	 * Solution.
	 *
	 * @param s the s
	 */
	public void solution(String s){}
	
	
	/**
	 * Playes Next step.
	 */
	public void nextStep(){}
	
	/**
	 * Prints the string.
	 *
	 * @param s the s
	 */
	public void print(String s){}
	
	/**
	 * Println the string.
	 *
	 * @param s the s
	 */
	public void println(String s){}
	
	
	/**
	 * Forces next step.
	 */
	public void forceNextStep(){}
	
	
	/**
	 * Gets the no threads.
	 *
	 * @return the no threads
	 */
	public  int getNoThreads() {
		return noThreads;
	}
	
	
	/**
	 * Draw Assignment table in GUI.
	 *
	 * @param asm the asm
	 */
	public void drawTable(Assignment asm){
		
	}

	
}
