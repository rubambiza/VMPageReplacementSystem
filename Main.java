import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import java.util.Queue;
import java.util.LinkedList;
/******************************************************
* Main entry into the program.
* Reads an input file, creates a table and GUI.
* Passes the table, input, and file to the controller.
* @author Gloire Rubambiza
* @since 11/22/2017
******************************************************/
public class Main {

  public static void main (String[] args) {

    String filename = "";
    FileChoice chooser = new FileChoice();
    filename = chooser.chooseFile();
    Tables tbl = new Tables();
    SystemGUI gui = new SystemGUI();
    Queue<Integer[]> myQ = new LinkedList<Integer[]>();

    try  {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line  = null;

      while ( (line = reader.readLine() ) != null) {
        sanitizeInput(line, myQ);
      }
    } catch ( IOException e) {
      System.err.println("Could not find input file.\n");
      e.printStackTrace();
      System.exit(1);
    }

    // Send the controller to handle the input.
    Controller ctrl = new Controller(gui, tbl, myQ);
  }

  /*************************************************
  * Sanitizes the input from the file i.e. trims it.
  * Accounts for the possibility of 10 processes.
  * Adds the input to the queue.
  * @param line is the line that was read from file.
  * @param queue is the queue of inputs
  **************************************************/
  private static void sanitizeInput ( String line, Queue<Integer[]> queue ) {

    // The string that will produce the integers to be used
    String newLine  = (line.replaceAll(":\\s", "")).substring(1);

    // The array that will store the pid and page requested.
    Integer [] mapping = new Integer[2];

    // Check if we are dealing with process #10.
    if ( newLine.length() == 7 ) {
      mapping[0] = Integer.parseInt(newLine.substring(0,1));
      mapping[1] = Integer.parseInt(newLine.substring(1),2);
    } else {
      mapping[0] = Integer.parseInt(newLine.substring(0,2));
      mapping[1] = Integer.parseInt(newLine.substring(2),2);
    }
    queue.add(mapping);
  }
}
