/******************************************************
 * ACS-1904-518 - PROGRAMMING TWO - Assignment One

 *
 *******************************************************/

// import statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class A1DataAndInputIncompatible
{
    public static void main(String[] args) throws FileNotFoundException
    {
        // creating constants that will be used by the array
        final int MAX_IDS = 7;
        final int MAX_SPEED = 7;
        final int MAX_READINGS = 10;
        final int MAX_VOLUMES = 3;

        // creating arrays that will be used throughout the system
        String[] intersectionCodes = new String[MAX_IDS];
        int[] speedLimit = new int[MAX_SPEED];
        int[][] sensorReadings = new int[MAX_IDS][MAX_READINGS];
        int[][] trafficVolumes = new int[MAX_IDS][MAX_VOLUMES];

        // using a static method that populates the array with the data provided in the file
        populateArraysFromFile(intersectionCodes, speedLimit, sensorReadings, trafficVolumes);

        // using the static method that generates and returns the average of the speed readings
        double[] averageSpeed = calculateAverageSpeed(sensorReadings);
        //System.out.println(Arrays.toString(averageSpeed));

        // using a static method that generates and returns the average of the speed volumes
        double[] averageVolume = calculateAverageVolume(trafficVolumes);
        //System.out.println(Arrays.toString(averageVolume));

        // using a static that generates and returns the speedsScore
        double[] speedScore = calculateSpeedScore(speedLimit, averageSpeed, averageVolume);
        //System.out.println(Arrays.toString(speedScore));

        // using a static method to calculate the rank using the speed score
        int[] scoreRank = calculateRank(speedScore);

        // printing general message
        System.out.println("Speed Camera Statistics");
        System.out.println("**************************************************************************");
        System.out.println();

        // using static method to display summary of everything
        displaySummary(intersectionCodes, speedLimit, averageSpeed,
                trafficVolumes, averageVolume, speedScore, scoreRank);
        System.out.println();

        // HERE STARTS THE DATA MANIPULATION SECTION

        // creating a new scanner to take keyboard input
        Scanner kb = new Scanner(System.in);
        String userInput;

        // opening messages
        System.out.println("******* Edit Records *******");
        System.out.println("\nChoose an option:");
        System.out.println("A - Add a stat");
        System.out.println("B - Quit");
        System.out.println();

        userInput = kb.next();

        // creating a while loop that iterates based on the userInput, it is possible that the user may quit
        // without editing any record, therefore, a while loop is the best possible control structure to be used
        while(userInput.equalsIgnoreCase("A"))
        {
            // using a static method that asks for intersection code and checks if it is in the list
            int codeIndex = getIndexToEdit(intersectionCodes);

            // now creating a while loop that if codeIndex is -1, then we call the function again
            // while codeIndex is -1
            while(codeIndex == -1)
            {
                codeIndex = getIndexToEdit(intersectionCodes);
            } // end of while loop
            //System.out.println(codeIndex);

            // asking what stat to edit
            System.out.println();
            System.out.println("Select");
            System.out.println("S: add a speed");
            System.out.println("V: add a volume");

            // setting the userInput to be what the user wants to change
            userInput = kb.next();

            // now starting an if-else chain that will decide if the speed or the volume array
            // is passed to a newer method
            boolean validValue = false;
            // this while loop makes sure that a valid value is entered out of both the options
            while(!validValue)
            {
                if (userInput.equalsIgnoreCase("S"))
                {
                    addStat(sensorReadings[codeIndex]);
                    validValue = true;
                } // end if user wants to edit speed
                else if (userInput.equalsIgnoreCase("V"))
                {
                    addStat(trafficVolumes[codeIndex]);
                    validValue = true;
                } // end of if user wants to edit volume
                else
                {
                    System.out.println("Please enter a valid value:");
                    System.out.println("S: add a speed");
                    System.out.println("V: add a volume");
                    userInput = kb.next();
                } // end of else if user did not enter a valid value
            }

            // now that the arrays have updated values, re-working on the summary so that the changes
            // may be reflected - this is done through various static methods
            averageSpeed = calculateAverageSpeed(sensorReadings);
            averageVolume = calculateAverageVolume(trafficVolumes);
            speedScore = calculateSpeedScore(speedLimit, averageSpeed, averageVolume);
            scoreRank = calculateRank(speedScore);

            // now displaying the final reworked array by calling the displaySummary once again
            displaySummary(intersectionCodes, speedLimit, averageSpeed,
                    trafficVolumes, averageVolume, speedScore, scoreRank);


            System.out.println("\nChoose an option:");
            System.out.println("A - Add a stat");
            System.out.println("B - Quit");
            userInput = kb.next();

        } // end of while loop

        System.out.println();
        System.out.println("end of program");

    } // end of main

    /*******************************
     * The following static method populates the array with the data retrieved from the file
     * @param intersectionCodes array of intersection codes from the file
     * @param speedLimit array of speed limit from the file
     * @param sensorReadings 2D-array of sensor readings from the file
     * @param trafficVolumes 2D-array of traffic volume from the file
     ******************************/
    public static void populateArraysFromFile(String[] intersectionCodes, int[] speedLimit,
                                              int[][] sensorReadings, int[][] trafficVolumes)
            throws FileNotFoundException
    {
        // creating a scanner that reads the value from the file
        Scanner file = new Scanner(new File("A1Data.txt"));

        // using variables that will be used in the while loop to add values to the required arrays
        int rowCount = 0;
        final int SENSOR_READING_NUMBER = 10;
        final int TRAFFIC_VOLUME_NUMBER = 3;

        // starting an EOF while loop
        while(file.hasNextLine())
        {
            // filling up the arrays using the scanner class

            // filling up the ID as well as the speed limit array
            intersectionCodes[rowCount] = file.next();
            speedLimit[rowCount] = file.nextInt();

            // now using a for loop to populate the 2D array of speed
            for(int i = 0; i < SENSOR_READING_NUMBER; i++)
            {
                sensorReadings[rowCount][i] = file.nextInt();
            } // end of for

            // now using the for loop to populate the 2D array of traffic volume readings
            for(int i = 0; i < TRAFFIC_VOLUME_NUMBER; i++)
            {
                trafficVolumes[rowCount][i] = file.nextInt();
            } // end of for loop

            // increasing the rowCount by one to move onto the next array
            rowCount++;

        } // end of EOF while loop

    } // end of populateArraysFromFile

    /***************************
     * The following static method calculates the average of the sensor reading by dropping highest and lowest
     * @param sensorReadings 2D-array of sensor readings
     * @return average array
     *************************/
    public static double[] calculateAverageSpeed(int[][] sensorReadings)
    {
        // making a constant for the double array
        final int MAX_AVERAGES = 7;

        // initializing a double array that will be hold the averages and min/max
        double[] average = new double[MAX_AVERAGES];

        // starting a for loop that goes through the outer array and calculates the average from the inner
        // array and then populates the average array with the same
        for (int i = 0; i < sensorReadings.length; i++)
        {

            // making an array that will hold the values from this 2D-array and then sort this array
            int[] auxiliaryArray = new int[10];

            // setting up two variables that will contain the min and max values that will later be removed from
            // the array
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            // this for loop sets the value of max and min which can later be used to remove the same
            for (int j = 0; j < sensorReadings[i].length; j++)
            {
                max = Math.max(max, sensorReadings[i][j]);
                min = Math.min(min, sensorReadings[i][j]);

            } // end of inner for loop

            for(int j = 0; j < auxiliaryArray.length; j++)         // for loop to populate the new array
            {
                auxiliaryArray[j] = sensorReadings[i][j];
            } // end of inner for loop
            //System.out.println(Arrays.toString(auxiliaryArray));

            // using a static method to find the average and then add it to the average array
            for (int j = 0; j < MAX_AVERAGES; j++)
            {
                average[i] = averageArrayPopulator(auxiliaryArray);
            }
            // end of for loop

        } // end of outer for loop

        return average;
    } //end of calculateAverage

    /***************************
     * The following static method acts as mini calculateAverageSpeed calculates the average and then returns to the main method
     * @param auxiliaryArray 2D-array of sensor readings
     * @return double of average of speed to be used in the main method
     *************************/
    public static double averageArrayPopulator(int[] auxiliaryArray)
    {
        // sorting the auxiliary array because there is no future, therefore array manipulation is possible
        Arrays.sort(auxiliaryArray);

        // creating the necessary variables
        int sum = 0;               // holds a sum of the array integer (casted later)
        int count = 0;             // counts number of elements in the array for later usage

        // starting a for loop to move from index position one to length - 1 so that the lowest and
        // the highest values are left out while sum and count variables are used as required
        for (int i = 1; i < auxiliaryArray.length - 1; i++)
        {
            sum += auxiliaryArray[i];
            count++;
        } // end of enhanced-for

        // now calculating the average by the required formula
        //System.out.println(String.format("%.2f", averageDouble));
        //System.out.println(averageDouble);

        // now returning the double value
        return (double) sum / count;

    } // end of averageArrayPopulator

    /***************************
     * The following static calculates the average of the traffic
     * @param trafficVolumes 2D-array of sensor readings
     * @return double of average of volume to be used in the main method
     *************************/
    public static double[] calculateAverageVolume(int[][] trafficVolumes)
    {
        // making a constant for maximum volumes
        final int MAX_VOLUMES = 7;

        // making an array that will hold the values of arrays
        double[] average = new double[MAX_VOLUMES];

        // starting for loop that goes through each row of the 2D-array
        for (int i = 0; i < trafficVolumes.length; i++)
        {
            // creating sum as well as count variables that will count and sum
            int sum = 0;
            int count = 0;

            // making a for loop that goes through the columns in the main array
            for (int j = 0; j < trafficVolumes[i].length; j++)
            {
                sum += trafficVolumes[i][j];
                count++;
            } // end of inner for loop

            // now calculating the average
            double doubleAverage = (double) sum / count;
            //System.out.println(doubleAverage);

            // now adding the average to the main array
            average[i] = doubleAverage;

        } // end of outer for loop

        return average;
    } // end of traffic volumes

    /***************************
     * The following static calculates the speed score from all the values
     * @param speedLimit is the array of speed limits
     * @param averageSpeed is the array of average speed that is previously calculated
     * @param averageVolume is the array of average volume that is previously calculated
     * @return double type array of the speed score
     *************************/
    public static double[] calculateSpeedScore(int[] speedLimit, double[] averageSpeed, double[] averageVolume)
    {
        // creating a final int to be used a constant
        final int MAX_SPEED = 7;

        // making an array variable
        double[] speedScore = new double[MAX_SPEED];

        // making a double variable to hold the value of speed score
        double speedScoreVariable;

        // using a for loop that calculates the speedScore and then adds it to the array
        for (int i = 0; i < MAX_SPEED; i++)
        {
            // calculating using the formula
            speedScoreVariable = averageSpeed[i] * averageVolume[i];
            speedScoreVariable /= speedLimit[i];

            // adding the final value to the array after parsing it to have two decimal places
            speedScore[i] = speedScoreVariable;

        } // end of for loop

        return speedScore;

    } // end of calculateSpeedScore

    /***************************
     * Calculates the rank of speedScores
     * @param speedScore speedScore array from the main method
     * @return int type array of score ranks
     *************************/
    public static int[] calculateRank(double[] speedScore)
    {
        // creating a final int to be used as a constant
        final int MAX_SCORE_AMOUNT = 7;

        // creating an array that will hold the value of rank
        int[] scoreRank = new int[MAX_SCORE_AMOUNT];

        // creating an auxiliary array that will hold the same values as the speed score array
        double[] auxiliaryArray = new double[MAX_SCORE_AMOUNT];
        // creating an auxiliary rak to hold the numbers that are in descending order
        int[] auxiliaryRank = new int[MAX_SCORE_AMOUNT];

        // populating the said array
        for (int i = 0; i < MAX_SCORE_AMOUNT; i++)
        {
            auxiliaryArray[i] = speedScore[i];
        } // end of for loop

        // now sorting the newly created array
        Arrays.sort(auxiliaryArray);

        // now making auxiliary rank to the sorted array which must be done in descending order as the
        // array is in ascending order, also making count to control the loop
        int count = 0;
        for (int i = MAX_SCORE_AMOUNT; i > 0; i--)
        {
            auxiliaryRank[count++] = i;
        } // end of for loop

        // creating the final for loop to set the ranks
        for (int i = 0; i < MAX_SCORE_AMOUNT; i++)
        {
            // creating another for statement to loop through each number in both the arrays
            for (int j = 0; j < MAX_SCORE_AMOUNT; j++)
            {
                // creating an if conditional which will set the rank
                if (speedScore[i] == auxiliaryArray[j])
                {
                    scoreRank[i] = auxiliaryRank[j];
                } // end of if

            } // end of inner for loop

        } // end of inner for loop
        //System.out.println(Arrays.toString(scoreRank));

        // returning the scoreRank array
        return scoreRank;

    } // end of calculateRank

    /*******************************
     * The following static method generates and displays the summary of all given values
     * @param intersectionCodes string type array of intersection codes from the file
     * @param speedLimit int type array of speed limit from the file
     * @param averageSpeed double type array of average speeds
     * @param trafficVolumes 2D-array of traffic volume from the file
     * @param averageVolume double array type array of average volume
     * @param speedScore double type array of speedScore
     * @param speedRank int type array of speedRank
     ******************************/
    public static void displaySummary(String[] intersectionCodes, int[] speedLimit, double[] averageSpeed,
                                      int[][]trafficVolumes, double[] averageVolume, double[] speedScore, int[] speedRank)
    {
        // final int constant to be used in the loops
        final int ROW_LENGTH = 7;

        // printing the foremost message
        System.out.println("Intersection \t Limit \t Avg Speed " +
                "\t V1 \t V2 \t V3 \t Avg Vol   Score \t Rank");

        // now using a for loop to printing values from the arrays column-wise
        for (int i = 0; i < ROW_LENGTH; i++)
        {
            System.out.println(intersectionCodes[i] + "\t\t    " + speedLimit[i] + "\t     "
                    + String.format("%.2f", averageSpeed[i]) + "\t " + trafficVolumes[i][0] + "\t "
                    + trafficVolumes[i][1] + "\t " + trafficVolumes[i][2] +
                    "\t " + String.format("%.2f", averageVolume[i]) + "    " +
                    String.format("%.2f", speedScore[i]) + "\t " + speedRank[i]);
        } // end of for loop

    } // end of displaySummary

    /***************************
     * Checks if the inout that the user has entered is in the array and produces the result for the same
     * @param intersectionCodes array of intersection codes
     * @return the index of input intersection code
     *************************/
    public static int getIndexToEdit(String[] intersectionCodes)
    {
        // creating a scanner to take userInput
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the intersection code for the record to edit.");
        String userInput = kb.next();

        // creating a count variable that will be used for getting the index of the array
        // it is set to -1 to assume that the code is not present
        int counter = 0;

        // starting a for loop that goes through the array to check if the code is in the array
        for (String intersectionCode : intersectionCodes) {
            if (userInput.equalsIgnoreCase(intersectionCode)) {
                return counter;
            } // end of if

            counter++;
        } // end of for loop

        // if the counter from above did not get returned, then the value is not present, thus we return -1
        return -1;

    } // end of intersectionInputChecker

    /***************************
     * This static methods adds the new stat that the user provided as an input
     * @param addedArray part of an array that is to be edited
     *************************/
    public static void addStat(int[] addedArray)
    {
        // creating a new scanner that will take in value from the user to be added in the array
        Scanner kb = new Scanner(System.in);
        int userInput;

        // asking user to enter a value
        System.out.println("Enter the new stat value.");
        userInput = kb.nextInt();

        // starting a for loop,
        // the work of this for loop is go through each value in the required array.
        // as it goes through, an if statement works by pushing each value one index behind
        // and then the last value (userInput) is also added to the same using another if statement.
        for (int i = 0; i < addedArray.length; i++)
        {
            // starting an if loop, creating two separate scenarios based on the index that we are working on
            if (i > 0)
            {
                addedArray[i - 1] = addedArray[i];
            } // end of if that makes sure that the index does not get out of bounds as it runs when i > 0

            if (i == addedArray.length - 1)
            {
                addedArray[i] = userInput;
            } // end of else when the index is the last index (new value iss to be added)

        } // end of for loop
        //System.out.println(Arrays.toString(addedArray));

    } // end of addedArray

} // end of class
