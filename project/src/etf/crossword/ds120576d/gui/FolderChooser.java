package etf.crossword.ds120576d.gui;


import javax.swing.*;
import java.awt.event.*;
import java.awt.*;



// TODO: Auto-generated Javadoc
/**
 * The Class FolderChooser.
 */
public class FolderChooser extends JPanel
   implements ActionListener {
   
   /** The go. */
   JButton go;
   
   /** The chooser. */
   JFileChooser chooser;
   
   /** The choosertitle. */
   String choosertitle;
   
  /**
   * Instantiates a new folder chooser.
   */
  public FolderChooser () {
    go = new JButton("Do it");
    go.addActionListener(this);
    add(go);
   }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
  
        
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      }
    else {
      System.out.println("No Selection ");
      }
     }
   
  /* (non-Javadoc)
   * @see javax.swing.JComponent#getPreferredSize()
   */
  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }
    
  /**
   * The main method.
   *
   * @param s the arguments
   */
  public static void main(String s[]) {
    JFrame frame = new JFrame("");
    FolderChooser  panel = new FolderChooser ();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }
}