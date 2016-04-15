package etf.crossword.ds120576d.crossword;

import etf.crossword.ds120576d.crossword.core.Assignment;
import etf.crossword.ds120576d.crossword.core.Player;
import etf.crossword.ds120576d.gui.CrosswordGUI;

// TODO: Auto-generated Javadoc
/**
 * The Class StepByStepPlayer.
 */
public class StepByStepPlayer extends Player {

	/** The no threads. */
	protected int noThreads = 1;
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#nextStep()
	 */
	@Override
	public synchronized void nextStep() {
		try {
			wait(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#forceNextStep()
	 */
	@Override
	public synchronized void forceNextStep(){
		notify();
	}
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#print(java.lang.String)
	 */
	@Override
	public void print(String s) {
		System.out.print(s);
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#println(java.lang.String)
	 */
	@Override
	public void println(String s) {
		System.out.println(s);
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#solution(java.lang.String)
	 */
	@Override
	public void solution(String s) {
		System.out.println(s);
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#finish()
	 */
	@Override
	public  void finish() {
		System.out.println("Finished!");
	}
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#drawTable(etf.crossword.ds120576d.crossword.core.Assignment)
	 */
	@Override
	public void drawTable(Assignment asm) {
		CrosswordGUI.drawAssignment(asm);
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#playFaster()
	 */
	
	@Override
	public void playFaster() {
		delay = (delay > 5) ? (delay-50) : (delay=5);
	}
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#playSlower()
	 */
	@Override
	public void playSlower() {
		delay = (delay < 2500) ? (delay+50) : (delay=2500);
	}
	

}
