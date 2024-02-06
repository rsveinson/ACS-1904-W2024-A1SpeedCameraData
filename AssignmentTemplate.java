import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/** 
 * ACS-1904 Assignment 1
 * @Sveinson 
*/

public class AssignmentTemplate{
    public static void main(String[] args)throws FileNotFoundException {
        // ** constants **
        
        final int SCOREWEIGHTFACTOR = 35;   // the factor used to calculate the weighted socre
        
        // For declaring dimensions of arrays and tables
        final int MAXRECORDS = 6;
        final int MAXTIMES = 10;
        final int MAXVOLUMES = 3;
        
        // need scanners for file IO and for change/add operations
        Scanner scanner = new Scanner(System.in);
        Scanner fRead = new Scanner(new File("A1Data.txt"));
        
        // file IO stuff
        String strin;
        String tokens[] = null;     // input string split into separate items
        String delim = "[ ]+";      // regular expression setting delimiter to one or more spaces
        int n = 0;                  // count and index records read from the file
        
        // Paralel arrays
        String[] name = new String[MAXRECORDS];
        int bib[] = new int[MAXRECORDS];
        
        int times[][] = new int[MAXRECORDS][MAXTIMES];
        int volumes[][] = new int[MAXRECORDS][MAXVOLUMES];
        
        double[] averageTime = new double[MAXRECORDS];
        double[] averageVolume = new double[MAXRECORDS];
        int[] rank = new int[MAXRECORDS];
        
        // miscellaneous variables for performing change and add operations
        
        /*************************
         * Step one: get the data from the file into the
         * appropriate arrays and tables
         * Tempted to put all of this into a static
         * method just to tidy up the main code
         * we'll see
         ***********************************************/
         
         while(fRead.hasNext()){
             strin = fRead.nextLine();
             System.out.println(strin);
         }// end eof loop


        System.out.println("end of program");
    }
}
