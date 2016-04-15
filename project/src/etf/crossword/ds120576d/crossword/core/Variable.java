package etf.crossword.ds120576d.crossword.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


// TODO: Auto-generated Javadoc
/**
 * The Class Variable.
 */
public class Variable implements Serializable {
	
	/** The value. */
	private String value = "";
	
	/** The domains. */
	Queue<String> domains=new LinkedList<String>();
	
	/** The start point. */
	public Point start;
	
	/** The end point. */
	public Point end;
	
	/** The type. */
	public char type;
	
	/** The name of Variable. */
	protected int name;

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(int name) {
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public int getName() {
		return name;
	}
	
	/** The lenght. */
	//Variable lenght
	private int lenght;
	
	
	/**
	 * Gets the lenght.
	 *
	 * @return the lenght
	 */
	public int getLenght() {
		return lenght;
	}
	
	/**
	 * Sets the lenght.
	 *
	 * @param lenght the new lenght
	 */
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	/**
	 * Instantiates a new variable.
	 */
	public Variable() {
		start = new Point();
		end = new Point();
	}
	
	/**
	 * Instantiates a new variable.
	 *
	 * @param lenght the lenght
	 */
	public Variable(int lenght) {
		this();
		this.lenght = lenght;
	}

	/**
	 * Push domain to var.
	 *
	 * @param s the s
	 */
	public void pushDomain(String s){
		domains.add(s);
	}
	
	/**
	 * Pop domain from var.
	 *
	 * @return the string
	 */
	public String popDomain(){
		return domains.remove();
	}
	
	/**
	 * Domains size.
	 *
	 * @return the int
	 */
	public int domainsSize(){
		return domains.size();
	}

	
	/**
	 * Sets the next domain as value and removes it from var domain list.
	 */
	public void setNextDomainAsValue(){
		value = popDomain();
	}

	
	/**
	 * Sets the domains for var.
	 *
	 * @param domain the new domain
	 */
	public void setDomain(List<String> domain) {
		for(Iterator<String> i = domain.iterator(); i.hasNext(); ) {
			String dom = i.next();
		   pushDomain(dom);
		}
		
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName(){
		return name+""+type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ""+getFullName()+": "+value;
	}
	
	/**
	 * Try next domain.
	 *
	 * @return true, if successful
	 */
	public boolean tryNextDomain(){
		if(domainsSize()<=0)return false;
		setNextDomainAsValue();
		return true;
	}
	
	
}
