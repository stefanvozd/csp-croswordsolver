package etf.crossword.ds120576d.crossword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JTextArea;


import etf.crossword.ds120576d.crossword.core.Assignment;
import etf.crossword.ds120576d.crossword.core.Constrain;
import etf.crossword.ds120576d.crossword.core.Player;
import etf.crossword.ds120576d.crossword.core.Utility;
import etf.crossword.ds120576d.crossword.core.Variable;
import etf.crossword.ds120576d.crossword.core.VariableComparator;
import etf.crossword.ds120576d.gui.Console;
import etf.crossword.ds120576d.gui.CrosswordGUI;


/**
 * The Class CrosswordGame.
 * Game is created here. 
 */
public class CrosswordGame {

	/** Game one and only one Constrain. */
	private Constrain cwconst = new Constrain();
	
	/** The init Assignment. */
	private static Assignment asm;
	
	/** The max. */
	private static int max = 0;
	
	/** The player. */
	static Player player;
	
	/** The temp vars. */
	List<Variable>[] vars = (ArrayList<Variable>[])new ArrayList[100];
	
	/** ALl vars in Priority Queue . */
	public PriorityQueue<Variable> vqueue =  new PriorityQueue<Variable>(100, new VariableComparator());
	

	/** The no threads in with we execute game. */
	volatile static int noThreads= 8;
	
	/** The linked blocking deque of threads. */
	static BlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<Runnable>(
			noThreads);
	
	/** The executor service. */
	static ExecutorService executorService = new ThreadPoolExecutor(noThreads, noThreads, 300,  TimeUnit.SECONDS, linkedBlockingDeque);
	
	
	/**
	 * Instantiates a new crossword game.
	 *
	 * @param _vars the _vars
	 * @param _domains the _domains
	 * @param ply the ply
	 */
	public CrosswordGame( List<Variable> _vars,List<String> _domains,Player ply) {
		new Console();
		player = ply;
		noThreads = player.getNoThreads();
		initMaps(_vars,_domains);
	}
	
	/**
	 * Sets the player.
	 *
	 * @param player the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Inits the maps.
	 * Adds domains to vars
	 *
	 * @param _vars the _vars
	 * @param _domains the _domains
	 */
	private void initMaps(List<Variable> _vars, List<String> _domains) {
		
		//Sort vars
		for(Variable cw : _vars){
			try{
				max = cw.getLenght()>max? cw.getLenght():max;
				vars[cw.getLenght()].add(cw);
			}catch(Exception e){
				vars[cw.getLenght()]=new ArrayList<Variable>();
				vars[cw.getLenght()].add(cw); 
				
			}
			
			if(cw.getLenght()>max)max=cw.getLenght();
		}
	
		System.out.println("Domains: ");
		//Add domains to vars
		for(String dm : _domains){
			try{
			//System.out.print(dm+" ");
			Collection<Variable> tempVars = vars[dm.length()];
			for(Variable cw1 : tempVars){
				cw1.pushDomain(dm);
			}
			}catch(Exception e){
				//no key in map
			}
		}
	
		System.out.println("Vars: ");
		for(int i=0;i<=max;i++){
			try{
				List<Variable> vt = vars[i];
				for(Variable tmp : vt){
					System.out.println(tmp +" with " + tmp.domainsSize()+" domains");
					vqueue.add(tmp);
				}
			}catch(Exception e){
				
			}
		
		
		}
		
		
	}

	
	/**
	 * Start game.
	 */
	public void start(){
		asm = new Assignment(vqueue);
		backtrack_search(asm);	
	}
	
	/** The game starttime. */
	static long starttime;
	
	/** The level writer helper. */
	static String snivo="------------------------------------------------------------------------------------------------------------------------------------------------------------------";
	
	/** The current level flag. */
	static volatile int nivo=0;
	
	/** The number of game solutions. */
	static AtomicInteger numsol = new AtomicInteger(0);
	
	/** The time when first solution is found */
	static volatile long solStart = 0;	
	
	/**
	 * Backtrack search initiator.
	 *
	 * @param asm the asm
	 */
	public void backtrack_search(Assignment asm){
		player.started("Threads started: "+(noThreads+1));
		starttime = System.currentTimeMillis();
		backtrack(asm,0);	
		if(numsol.get()==0)System.out.println("No solutions found.");
		player.finish();
	}
	
	
	
	/**
	 * Backtrack algorithm its self
	 *
	 * @param asm the asm
	 * @param level the level
	 */
	public void backtrack(Assignment asm,int level){
		Variable cw;
	
			if(asm.vqueue.isEmpty())return;
			cw = asm.popVar();
			while(cw.tryNextDomain()){
				player.nextStep();
				player.print(""+Thread.currentThread().getId());
				player.print(snivo.substring(0,level)+"> "+"Check "+cw+" in "+asm.complete_vars);
			
				if(cwconst.validate(cw, asm)){
					player.nextStep();
					player.println(" --> satisfies!");
					player.print(Thread.currentThread().getId()+snivo.substring(0,level)+"> "+"Add "+ cw + " to " +asm.complete_vars );

					asm.complete_vars.add(cw);
					player.println(" -> "+asm.complete_vars );
					player.drawTable(asm);
					
					if(asm.complete_vars.size()==asm.maxvars){
						player.nextStep();
						long endTime=System.currentTimeMillis();
						if(solStart==0)solStart=System.currentTimeMillis();
						
						
						if(numsol.incrementAndGet()%1000==0){
							System.out.println(Thread.currentThread().getId()+snivo.substring(0,level)+"> "+"SOLUTION!"+ asm.complete_vars +" @ "+numsol+"-"+(endTime-starttime)+" ms"); 
						}
						
				
						player.println(Thread.currentThread().getId()+snivo.substring(0,level)+"> "+"SOLUTION!"+ asm.complete_vars +" @ "+numsol+"-"+(endTime-solStart)); 
						player.solution(Thread.currentThread().getId()+snivo.substring(0,level)+"> "+"SOLUTION!"+ asm.complete_vars +" @ "+numsol+"-"+(endTime-solStart));
					}
				
					
					
					try{
					if(linkedBlockingDeque.size()<noThreads)executorService.execute(new BacktrackWorker((Assignment) Utility.copy(asm),++level));
					
					else backtrack((Assignment) Utility.copy(asm),++level);
					} catch (Exception e) {
					 
					}
					player.println(Thread.currentThread().getId()+snivo.substring(0,level)+"> "+"Remove "+cw+" from "+asm.complete_vars);
					
					player.nextStep();
					asm.complete_vars.remove(cw);
					player.drawTable(asm);
					player.nextStep();
					--level;
				} else {
					player.nextStep();
					player.println(" --> violates!");
				}
				
			}
			
	
		}
		

	
/**
 * The Class BacktrackWorker thread.
 */
private class BacktrackWorker extends Thread{
	
	/** The Assignment. */
	Assignment asm;
	
	/** The level. */
	int level;
	
	/**
	 * Instantiates a new backtrack worker.
	 *
	 * @param asm the asm
	 * @param level the level
	 */
	public BacktrackWorker( Assignment asm,int level) {
		this.level=level;
		this.asm=asm;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		 backtrack(asm,level);
	}
	

	
}

	


}
