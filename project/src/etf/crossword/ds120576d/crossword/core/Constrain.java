package etf.crossword.ds120576d.crossword.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map.Entry;

import etf.crossword.ds120576d.core.exceptions.OutOfLineRangeException;
import etf.crossword.ds120576d.core.exceptions.ParallelLineException;

// TODO: Auto-generated Javadoc
/**
 * The Class Constrain.
 * 
 */
public class Constrain{
		
	/**
	 * Validates if new Variable can be added to Assignment  .
	 *
	 * @param cw the cw
	 * @param asm the asm
	 * @return true, if valid
	 */
	public static boolean validate(Variable cw, Assignment asm) {

		for (Variable tcw : asm.complete_vars) {
			try {
				if (violates(cw, tcw))
					return false;
			} catch (Exception e) {

			}
		}

		return true;
}


	
	
	
	/**
	 * Check if variable violates other variable.
	 *
	 * @param cw1 the cw1
	 * @param cw2 the cw2
	 * @return true, if violates
	 */
	private static boolean violates(Variable cw1,Variable cw2){
		
		Point inct = null;
		try{	
		inct = Utility.lineIntersect(cw1,cw2);
		
		} catch (ParallelLineException e){
			//if its a dot
			boolean inter = Utility.intersectsDot(cw1.start, cw2.start, cw2.end);
			if(cw1.getLenght()==1 && !inter) return false;
			
			if(cw1.getLenght()==1 && inter)inct = cw1.start;
			
			//if(cw1.getLenght()!=1) return true;
		} catch (OutOfLineRangeException e) {
			return false;
		}
	
	
		
		int indexCW1 = Utility.indexOfIntersect(cw1.start, inct);
		
		int indexCW2 = Utility.indexOfIntersect(cw2.start, inct);
		
		if(cw1.getLenght()==1 &&  (indexCW1!=0 || indexCW1!=0))return false;//out of range
		
		if(cw1.getValue().charAt(indexCW1) == cw2.getValue().charAt(indexCW2)) return false;
		
		return true;
		
		
		
	}

	
	
}
