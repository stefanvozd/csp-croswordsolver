package etf.crossword.ds120576d.gui;


import java.io.*;
import java.awt.*;
import java.awt.event.*;


/**
 * The Class Console.
 * 
 * Redirects System.out to AWT Console
 *
 * @author Stefan
 */
public class Console extends WindowAdapter implements WindowListener, ActionListener, Runnable
{
	
	/** The frame. */
	private Frame frame;
	
	/** The text area. */
	private TextArea textArea;
	
	/** The reader. */
	private Thread reader;
	
	/** The reader2. */
	private Thread reader2;
	
	/** The quit flag. */
	private boolean quit;
					
	/** The pin. */
	private final PipedInputStream pin=new PipedInputStream(); 
	
	/** The pin2. */
	private final PipedInputStream pin2=new PipedInputStream(); 

	/** The error thrower. */
	Thread errorThrower; 
	
	/**
	 * Instantiates a new console.
	 */
	public Console()
	{
		// create all components and add them
		frame=new Frame("Crossword Console");
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
		int x=(int)(frameSize.width/2);
		int y=(int)(frameSize.height/2);
		frame.setBounds(x,y,frameSize.width,frameSize.height);
		
		textArea=new TextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		textArea.setEditable(false);
		Button button=new Button("clear");
		
		Panel panel=new Panel();
		panel.setLayout(new BorderLayout());
		panel.add(textArea,BorderLayout.CENTER);
		panel.add(button,BorderLayout.SOUTH);
		frame.add(panel);
		
		frame.setVisible(true);		
		
		frame.addWindowListener(this);		
		button.addActionListener(this);
		
		try
		{
			PipedOutputStream pout=new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout,true)); 
		} 
		catch (java.io.IOException io)
		{
			textArea.append("Couldn't redirect STDOUT to this console\n"+io.getMessage());
		}
		catch (SecurityException se)
		{
			textArea.append("Couldn't redirect STDOUT to this console\n"+se.getMessage());
	    } 
		
		try 
		{
			PipedOutputStream pout2=new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2,true));
		} 
		catch (java.io.IOException io)
		{
			textArea.append("Couldn't redirect STDERR to this console\n"+io.getMessage());
		}
		catch (SecurityException se)
		{
			textArea.append("Couldn't redirect STDERR to this console\n"+se.getMessage());
	    } 		
			
		quit=false; // signals the Threads that they should exit
				
		// Starting two seperate threads to read from the PipedInputStreams				
		//
		reader=new Thread(this);
		reader.setDaemon(true);	
		reader.start();	
		//
		reader2=new Thread(this);	
		reader2.setDaemon(true);	
		reader2.start();
				
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
	 */
	public synchronized void windowClosed(WindowEvent evt)
	{
		quit=true;
		this.notifyAll(); // stop all threads
		try { reader.join(1000);pin.close();   } catch (Exception e){}		
		try { reader2.join(1000);pin2.close(); } catch (Exception e){}
		System.exit(0);
	}		
		
	/* (non-Javadoc)
	 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
	 */
	public synchronized void windowClosing(WindowEvent evt)
	{
		frame.setVisible(false); // default behaviour of JFrame	
		frame.dispose();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public synchronized void actionPerformed(ActionEvent evt)
	{
		textArea.setText("");
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public synchronized void run()
	{
		try
		{			
			while (Thread.currentThread()==reader)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
				if (pin.available()!=0)
				{
					String input=this.readLine(pin);
					textArea.append(input);
				}
				if (quit) return;
			}
		
			while (Thread.currentThread()==reader2)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
				if (pin2.available()!=0)
				{
					String input=this.readLine(pin2);
					textArea.append(input);
				}
				if (quit) return;
			}			
		} catch (Exception e)
		{
			textArea.append("\nConsole reports an Internal error.");
			textArea.append("The error is: "+e);			
		}
		
		// just for testing (Throw a Nullpointer after 1 second)
		if (Thread.currentThread()==errorThrower)
		{
			try { this.wait(1000); }catch(InterruptedException ie){}
			throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
		}

	}
	
	/**
	 * Read line.
	 *
	 * @param in the in
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public synchronized String readLine(PipedInputStream in) throws IOException
	{
		String input="";
		do
		{
			int available=in.available();
			if (available==0) break;
			byte b[]=new byte[available];
			in.read(b);
			input=input+new String(b,0,b.length);														
		}while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
		return input;
	}	
		
	/**
	 * The main method.
	 *
	 * @param arg the arguments
	 */
	public static void main(String[] arg)
	{
		new Console(); // create console with not reference	
	}			
}