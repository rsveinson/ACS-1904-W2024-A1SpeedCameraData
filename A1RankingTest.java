import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/** 
 * ACS-1904 Assignment 1
 * @Sveinson 
 */

public class A1RankingTest{
    public static void main(String[] args)throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        Random r = new Random();
        int[] list = new int[10];
        int[] ranking = new int[10];
        int[] util = new int[10];

        int largest = Integer.MIN_VALUE;
        int bigIndex = -1;

        // load the list with 10 random numbers
        for(int i = 0; i < list.length; i++){
            list[i] = r.nextInt(15) + 1;
        }// end for

        // rank the ints
        // 1 copy list to util
        for(int i = 0; i < list.length; i++)
            util[i] = list[i];

        // make sure the lists are different
        System.out.println(list);
        System.out.println(util);

        printList(list, list.length);

        // find the largest and ran it 1
        for(int i = 0; i < util.length; i++){
            
            largest = Integer.MIN_VALUE;
            for(int j = 0; j < list.length; j++){
                if(util[j] > largest){
                    largest = util[j];
                    bigIndex = j;
                }// end if
            }//end for

            System.out.println("biggest is " + list[bigIndex]);
            util[bigIndex] = -1;
            ranking[bigIndex] = i + 1;
            //printList(list, list.length);
        }// end for
        
        printParallelLists(list, ranking, list.length);

        System.out.println("end of program");
    }

    public static void printList(int[] list, int size){
        for(int i = 0; i < size; i++){
            System.out.println(list[i]);
        }// end for
    }
    
    public static void printParallelLists(int[] list, int[] other, int size){
        for(int i = 0; i < size; i++){
            System.out.println(list[i] + ": " + other[i]);
        }// end for
    }
}
