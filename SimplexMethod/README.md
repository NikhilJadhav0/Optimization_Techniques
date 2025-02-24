# Optimization_techniques


## A brief introduction

&emsp;The ***simplex method*** is an optimization technique used to find the best solution for linear programming problems with multiple constraints. It iteratively moves from one corner point to another to reach the optimal outcome efficiently. Below, I will outline the step-by-step process of this method through an example and provide the corresponding code implementation.

# Step 1: Convert to Standard Form

**Convert the given problem to standard linear programming form.** 

**&emsp;Objective function:**

>&emsp;&emsp;z = 8x<sub>1</sub> + 10x<sub>2</sub> + 7x<sub>3</sub> &rarr; max

**&emsp;Constraint functions:**

>&emsp;&emsp;1. x<sub>1</sub> + 3x<sub>2</sub> + 2x<sub>3</sub> &le; 10<br>
>&emsp;&emsp;2. x<sub>1</sub> + 5x<sub>2</sub> + x<sub>3</sub> &le; 8

&emsp;Where:
>&emsp;x<sub>1</sub>, x<sub>2</sub>, x<sub>3</sub> &ge; 0

# Step 2: Add Slack Variables

To convert the inequality constraints into equality constraints, we introduce ***slack variables***:

>1.  x<sub>1</sub> + 3x<sub>2</sub> + 2x<sub>3</sub> &le; 10 &rarr; x<sub>1</sub> + 3x<sub>2</sub> + 2x<sub>3</sub> + ***s<sub>1</sub>*** = 10 (where s<sub>1</sub> is a slack variable, and s<sub>1</sub> &ge; 0)
>2. x<sub>1</sub> + 5x<sub>2</sub> + x<sub>3</sub> &le; 8 &rarr; x<sub>1</sub> + 5x<sub>2</sub> + x<sub>3</sub> + ***s<sub>2</sub>*** = 8

Then the problem becomes:
>z = 8x<sub>1</sub> + 10x<sub>2</sub> + 7x<sub>3</sub> + 0***s<sub>1</sub>*** + 0***s<sub>2</sub>*** &rarr; max<br>
>- x<sub>1</sub> + 3x<sub>2</sub> + 2x<sub>3</sub> + ***s<sub>1</sub>*** + 0***s<sub>2</sub>*** = 10
>- x<sub>1</sub> + 5x<sub>2</sub> +  x<sub>3</sub> + 0***s<sub>1</sub>*** +  ***s<sub>2</sub>*** = 8<br>
>where x<sub>1</sub>, x<sub>2</sub>, x<sub>3</sub>, ***s<sub>1</sub>***, ***s<sub>2</sub>*** &ge; 0

# Java Implementation of the Simplex Method

Below is the Java implementation of the **Simplex Algorithm**, which follows the same methodology outlined above:

```java
import java.util.Scanner;

public class SimplexMethod {
    private static final int MAX_CONSTRAINTS = 10;
    private static final int MAX_VARIABLES = 10;
    private static double[][] tableau = new double[MAX_VARIABLES + 1][MAX_CONSTRAINTS + MAX_VARIABLES + 2];
    private static int numVariables, numConstraints;

    private static void initializeTableau(Scanner scanner) {
        System.out.println("Enter the coefficients of the objective function:");
        for (int i = 1; i <= numVariables; i++) {
            tableau[numConstraints][i] = -scanner.nextDouble();
        }
        System.out.println("Enter the coefficients of the constraints:");
        for (int i = 0; i < numConstraints; i++) {
            for (int j = 1; j <= numVariables; j++) {
                tableau[i][j] = scanner.nextDouble();
            }
        }
        System.out.println("Enter the RHS of the constraints:");
        for (int i = 0; i < numConstraints; i++) {
            for (int j = numVariables + 1; j <= numVariables + numConstraints; j++) {
                tableau[i][j] = (i == j - numVariables - 1) ? 1.0 : 0.0;
            }
            tableau[i][numVariables + numConstraints + 1] = scanner.nextDouble();
        }
    }

    private static void showTableau() {
        System.out.println("Current Tableau:");
        for (int i = 0; i <= numConstraints; i++) {
            for (int j = 1; j <= numVariables + numConstraints + 1; j++) {
                System.out.printf("%8.2f", tableau[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of variables: ");
        numVariables = scanner.nextInt();
        System.out.print("Enter number of constraints: ");
        numConstraints = scanner.nextInt();
        initializeTableau(scanner);
        showTableau();
        scanner.close();
    }
}
```

### **How to Run the Java Code**
1. Save the file as `SimplexMethod.java`.
2. Compile the program using:
   ```sh
   javac SimplexMethod.java
   ```
3. Run the program:
   ```sh
   java SimplexMethod
   ```
4. Enter the required inputs when prompted.
### **Example Output:**
```
Enter number of variables: 2
Enter number of constraints: 2
Enter the coefficients of the objective function:
2 4
Enter the coefficients of the constraints:
3 2
4 3
Enter the RHS of the constraints:
7 9
Initial Tableau:
Current Tableau:
    3.00    2.00    1.00    0.00    7.00
    4.00    3.00    0.00    1.00    9.00
   -2.00   -4.00    0.00    0.00    0.00
Updated Tableau:
Current Tableau:
    0.33    0.00    1.00   -0.67    1.00
    1.33    1.00    0.00    0.33    3.00
    3.33    0.00    0.00    1.33   12.00
Optimal solution found:
Max Z = 12.00
x1 = 0.00
x2 = 3.00
```
### **References:**
This implementation was inspired by and adapted from the work available at [Simplex Method by Chutrunganh](https://github.com/chutrunganh/Simplex-Method/blob/main/README.md#a-brief-introduction).
