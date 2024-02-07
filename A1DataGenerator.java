import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

/** 
 * ACS-1904 Assignment 1
 * @Sveinson 
 */

public class A1DataGenerator{
    public static void main(String[] args)throws IOException {
        BufferedReader fin = null;
        PrintWriter fout = null;
        Scanner scanner = new Scanner(System.in);
        Random r = new Random();
        fout = new PrintWriter(new BufferedWriter(new FileWriter("A1Data.txt")));

        String[] intersection = {"Por-Mai", "StM-Fer", "StA-Fer", "KgE-Sar", "Nes-StJ", "Pem-Bis", "Wav-Ken"};
        int[] speedLimit = {50, 60, 60, 70, 50, 60, 80};
        int[][] speed = new int[7][10];
        int[][] trafficVolumes = new int[7][3];

        
        loadTable(speed, 7, 10, 30, 100);
        //printTable(speed, 7, 10);
        normalizeSpeed(speedLimit, speed);
        //printTable(speed, 7, 10);

        loadTable(trafficVolumes, 7, 3, 175, 550);
        //printTable(volumes, 6, 3);

        makeDataFile(intersection, speedLimit, speed, trafficVolumes, fout);

        fout.close();
        System.out.println("end of program");
    }// end main
    public static void normalizeSpeed(int[] sl, int[][] s){
        Random r = new Random();
        
        // go through the speed table to make sure that the speed is withing +- 20%
        for(int i = 0; i < sl.length; i++){
            for(int j = 0; j < s[i].length; j++){
                if((double)s[i][j] / sl[i] < (double)sl[i] * 0.6 || (double)s[i][j] / sl[i] > (double)sl[i] * 1.4){
                    // less than 80% of posted limit
                    //s[i][j] = (int)(sl[i] * 0.8);
                    //s[i][j] = r.nextInt((int)(sl[i] * 0.8) + ((int)(sl [i]* 1.2) - (int)(sl[i] * 0.8) + 1));
                    s[i][j] = r.nextInt((int)((double)sl[i] * 0.6)) + ((int)((double)sl [i]* 1.4) - (int)((double)sl[i] * 0.6) + 1);
                }// end low
                // else{
                    // if((double)s[i][j] / sl[i] > (double)sl[i] * 1.2){
                        // // more than 120% of posted limit
                        // s[i][j] = (int)(sl [i]* 1.2);
                    // }
                // }
            }// end j
        }// end for i
    }// end normalize

    public static void makeDataFile(String[] n, int[] b, int[][] times, 
    int[][]v, PrintWriter fout){

        for(int i = 0; i < n.length; i++){
            fout.print(n[i] + " ");
            fout.print(b[i] + " ");

            // times
            for(int j = 0; j < times[i].length; j++){
                System.out.print(times[i][j] + " "); 
                fout.print(times[i][j] + " ");
            }// end j times

            // volumes
            for(int j = 0; j < v[i].length; j++){
                System.out.print(v[i][j] + " ");
                fout.print(v[i][j] + " ");
            }// end j times

            System.out.println();
            fout.println();
        }//end i

    }// end make data file

    public static void loadTable(int[][] t, int rows, int columns, int lowBound, int highBound){
        Random r = new Random();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                t[i][j] = r.nextInt(lowBound) + (highBound - lowBound + 1);
            }// end j
        }// end for i
    }// end load+ 1

    public static void printTable(int[][] t, int rows, int columns){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                System.out.print(t[i][j] + " ");
            }// end j

            System.out.println();
        }// end for i
    }// end print

    
}
