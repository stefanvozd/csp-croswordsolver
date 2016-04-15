package etf.crossword.ds120576d.crossword.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The Class Assignment.
 */
public class Assignment implements Serializable {

	/** The Vars of Assignment in Priority Queue. */
	public PriorityQueue<Variable> vqueue = new PriorityQueue<Variable>(25,
			new VariableComparator());

	/** The completed vars. */
	public LinkedList<Variable> complete_vars = new LinkedList<Variable>();

	/** Num. of vars found in game */
	public int maxvars;

	/**
	 * Instantiates a new assignment.
	 */
	public Assignment() {

	}

	/**
	 * Instantiates a new assignment.
	 *
	 * @param vqueue2 the vqueue2
	 */
	public Assignment(PriorityQueue<Variable> vqueue2) {
		vqueue = (PriorityQueue<Variable>) Utility.copy(vqueue2);
		maxvars = vqueue.size();
	}


	/**
	 * Pop var from var queue.
	 *
	 * @return the variable
	 */
	public Variable popVar() {
		if (vqueue.isEmpty())
			return null;
		return vqueue.remove();
	}

	/**
	 * Push var to var queue.
	 *
	 * @param cw the cw
	 */
	public void pushVar(Variable cw) {
		vqueue.add(cw);
	}



}
