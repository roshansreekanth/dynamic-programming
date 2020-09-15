import java.util.HashMap;
import java.util.Scanner;

//Author: Roshan Sreekanth - R00170592
// I used a HashMap as I found it easier to work with, and the access time is O(1)
// I used the pseudocode and example code RodCuttingTopDown.java provided as references
public class PaperRollCuttingBottomUp 
{

   public static void printCuts(int size, HashMap<Integer, Integer> pieceCount)
   {
        while(pieceCount.get(size) > 0) // Subtracts the first cut from the size until the size reaches 0 to get the number of pieces used
        {
            System.out.print(pieceCount.get(size) + " ");
            size -= pieceCount.get(size); // Subtracts the size by the first cut value and runs the loop again 
        }
   }
   
   public static void processProfitAndCuts(int size, HashMap<Integer, Float> cutValues) 
   {
       HashMap<Integer, Float> profit = new HashMap<Integer, Float>();
       HashMap<Integer, Integer> pieceCount = new HashMap<Integer, Integer>();
       for(int i = 0; i <= size; i++) // initializes profit and pieceCount
       {
            profit.put(i, (float)0);
            pieceCount.put(i, 0);
       }

       for(int i = 1; i <= size; i++)
       {
           float optimumPrice = Float.MIN_VALUE; 
           for(int j = 1; j <= i; j++) // Goes through the smaller cuts
           {
               if(optimumPrice <  cutValues.get(j) + profit.get(i - j)) // If a better price is found it is the new optimum price
               {
                   optimumPrice = cutValues.get(j) + profit.get(i - j);
                   pieceCount.put(i, j); // Puts in the first cut used when cutting for the optimum price
               }
           }
           profit.put(i, optimumPrice); // Puts the optimum price for piece of length n
       }

       System.out.printf("Maximum profit is: %.2f", profit.get(size));
       System.out.println();
       System.out.println("Cuts for rod of size " + size + " is: ");   
       
       printCuts(size, pieceCount);
   }

   public static void main(String[] args)
   {
       HashMap<Integer, Float> cutValues = new HashMap<Integer, Float>();
       
       // Add keys and values (size, value)
       cutValues.put(1, 1.2f);
       cutValues.put(2, 3f);
       cutValues.put(3, 5.8f);
       cutValues.put(4, 0f);
       cutValues.put(5, 10.1f);

       Scanner sc = new Scanner(System.in);
       System.out.println("Enter the size of the roll: ");
       int size = sc.nextInt();
       sc.close();
       
       if(size > cutValues.size()) // If the size of the cut is greater than the number of cuts possible, all the prices greater than 5 are set to €0
       {
           for(int i = cutValues.size() + 1; i <= size; i++)
           {
               cutValues.put(i, 0f);
           }
       }
       
       processProfitAndCuts(size, cutValues);
   }

}