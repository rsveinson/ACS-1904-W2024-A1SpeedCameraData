import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/** 
 * ACS-1904 Assignment 1
 * @Sveinson 
*/

public class A1ShiftTest{
    public static void main(String[] args)throws FileNotFoundException {
        
        Scanner scanner = new Scanner(System.in);
        Random r = new Random();

        int[] list = new int[10];
        int newN = 5;
        
        for(int i = 0; i < 10; i++){
            list[i] = i + 20;
        }//end i
        

        printList(list, 10);
        System.out.println("end of list");
        
        addNewN(list, 10, newN);

        printList(list, 10);
        System.out.println("end of list");
        System.out.println("end of program");
    }
    public static void shift(int[] l, int length){
        //System.out.println("shifting");
        for(int i = 0; i < length - 1; i++){
            l[i] = l[i + 1];
        }// end shift loop
    }// end shift
    
    public static void addNewN(int[] l, int length, int n){
        // System.out.println(l.length);
        // System.out.println(n);
        if(length == l.length){
            System.out.println("full");
            shift(l, length);
            l[length -1] = n;
        }//end full
        else{
            System.out.println("not full");
            l[length - 1] = n;
        }
    }
    public static void printList(int[] list, int size){
        for(int i = 0; i < size; i++){
            System.out.println(list[i]);
        }// end for
    }
}
