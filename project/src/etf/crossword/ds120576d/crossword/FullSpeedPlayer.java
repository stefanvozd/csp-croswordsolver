package etf.crossword.ds120576d.crossword;

import java.util.LinkedList;
import java.util.List;

import etf.crossword.ds120576d.crossword.core.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class FullSpeedPlayer.
 */
public class FullSpeedPlayer extends Player {
	
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#started()
	 */
	@Override
	public void started() {
		// TODO Auto-generated method stub
		super.started();
	}
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#nextStep()
	 */
	@Override
	public void nextStep() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#print(java.lang.String)
	 */
	@Override
	public void print(String s) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#println(java.lang.String)
	 */
	@Override
	public void println(String s) {

	}
	
	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#solution(java.lang.String)
	 */
	@Override
	public  void solution(String s) {

		//System.out.println(s);
		
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#finish()
	 */
	@Override
	public void finish() {
		System.out.println("Finish ");

	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#getNoThreads()
	 */
	@Override
	public int getNoThreads() {
		// -1 because we have starter Thread that will join process to
		return  (Runtime.getRuntime().availableProcessors() - 1);
	}

	/* (non-Javadoc)
	 * @see etf.crossword.ds120576d.crossword.core.Player#forceNextStep()
	 */
	@Override
	public void forceNextStep() {}


}
