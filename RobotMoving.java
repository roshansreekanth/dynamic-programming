import java.util.Scanner;

//Author: Roshan Sreekanth - R00170592
// I used the pseudocode provided in the lecture notes as reference
public class RobotMoving 
{
    public static void printMatrix(float[][] matrix, int dimensions)
    {
        for (int i = 0; i < dimensions; i++) // Loops through the matrix and prints it
        {
            for (int j = 0; j < dimensions; j++)
            {
                System.out.printf("%.2f ", matrix[i][j]); // Goes through the rows and columns
            } 
            System.out.println(); //Leaves a space between rows
        }
    }

    public static void printDirections(float[][] matrix, int dimensions) // Function to print the path. I check the next cheapest number
    {
        int currentRow = 0; // Keeps track of the row the pointer is on
        int currentColumn = 0; // Keeps track of the column the pointer is on

        String directions = "START -->"; // String accumulator to store the directions
        
        boolean fetchedValues = false; // I use this to avoid float addition problems (ex. 1.1 + 1.3 becomes 2.39999999)
        
        float diagonal_value = 0f; // The value of the element diagonal to the current position
        float right_value = 0f; // The value of the element right of the current position
        float down_value = 0f; // The value of the element below the current position
        
        while(!(currentColumn == dimensions - 1 && currentRow == dimensions - 1)) // Goes through the matrix 
        {
            if(currentRow == dimensions - 1) // If it is on the top or bottom edges, the only way possible is right
            {
                currentColumn++;
                directions += " Right --> ";
            }

            else if(currentColumn == dimensions - 1) // If it is on the left or right edges, the only way possible is down
            {
                currentRow++;
                directions += " Down -->";
            }

            else
            {

                if(!fetchedValues) // Runs only once to avoid float addition rounding
                {
                    //The numbers diagonal to, right to, and below the current position respectively.
                    diagonal_value  = matrix[currentRow + 1][currentColumn + 1] - matrix[currentRow][currentColumn];
                    right_value = matrix[currentRow][currentColumn + 1] -  matrix[currentRow][currentColumn];
                    down_value =  matrix[currentRow + 1][currentColumn] -  matrix[currentRow][currentColumn];
                    fetchedValues = true;
                }
                               
                if(diagonal_value < right_value + down_value) // Cheaper to travel diagonal than right and down?
                {
                    currentRow++;
                    currentColumn++;
                    directions += " Diagonal -->";
                }

                else // Cheaper to travel right or down?
                {
                    if(right_value < down_value) // Cheaper to travel right?
                    {
                        currentColumn++;
                        directions += " Right -->";
                    }
                    else // Cheaper to travel down?
                    {
                        currentRow++;
                        directions += " Down -->";
                    }

                }
               
            }
        }
        System.out.println(directions + " END");
    }


    private static float getMinCost(float costMatrix[][], int dimensions, float right_cost, float down_cost, float diagonal_cost) 
    { 
      
       costMatrix[0][0] = 0; // First element is always 0

        for (int i = 1; i <= dimensions; i++) // Fills in the edge columns.
        {
            costMatrix[i][0] = costMatrix[i - 1][0] + costMatrix[i][0] + down_cost; // Fetches the column above and adds the cost of going down 
        }
        
        for (int j = 1; j <= dimensions; j++)  // Fills in the edge rows
        {
             costMatrix[0][j] = costMatrix[0][j - 1] + costMatrix[0][j] + right_cost; // Fetches the row to the left and adds the cost of going right 
        }

        for (int i = 1; i <= dimensions; i++)
        {
            for (int j = 1; j <= dimensions; j++)
            {
                costMatrix[i][j] = Math.min(costMatrix[i-1][j-1] + costMatrix[i][j] + diagonal_cost, Math.min(costMatrix[i-1][j] +  costMatrix[i][j] + down_cost, costMatrix[i][j-1] +  costMatrix[i][j] + right_cost)); 
                // Cheaper cost for diagonal, right, or down?
            } 
        }
        System.out.println();
        printMatrix(costMatrix, dimensions); 
        return costMatrix[dimensions- 1][dimensions- 1]; // Minimum cost stored in the last element of the matrix
    }

    public static void main(String[] args)
    {
        System.out.println("Enter matrix dimensions: ");

        Scanner sc = new Scanner(System.in);
        int dimensions = sc.nextInt();
        sc.close();
        
        System.out.println("Cost Column 1");
        System.out.println("Cost Matrix: ");
        float costMatrix[][] = new float[dimensions + 1][dimensions + 1];
        System.out.printf("The minimum cost is: %.2f", getMinCost(costMatrix, dimensions, 1.1f, 1.3f, 2.5f));
        System.out.println();
        System.out.println("Directions: ");
        printDirections(costMatrix, dimensions);
        
        System.out.println("--------------------------------------------------------------------------------------");

        System.out.println("Cost Column 2");
        System.out.println("Cost Matrix: ");
        float costMatrixTwo[][] = new float[dimensions + 1][dimensions + 1];
        System.out.printf("The minimum cost is: %.2f", getMinCost(costMatrixTwo, dimensions, 1.5f, 1.2f, 2.3f));
        System.out.println();
        System.out.println("Directions");
        printDirections(costMatrixTwo, dimensions);
    } 
}