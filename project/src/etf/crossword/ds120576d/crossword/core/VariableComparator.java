package etf.crossword.ds120576d.crossword.core;

import java.io.Serializable;
import java.util.Comparator;


/**
	 * The Class VariableComparator.
	 * Compares two Variable by lenght
	 */
	public class VariableComparator implements Serializable,Comparator<Variable>
	{
	    
    	/* (non-Javadoc)
    	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
    	 */
    	public int compare(Variable x, Variable y)
	    {
	     
	        if (x.getLenght() > y.getLenght())
	        {
	            return -1;
	        }
	        if (x.getLenght() < y.getLenght())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}


	

