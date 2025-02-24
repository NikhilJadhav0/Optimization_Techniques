import java.util.Scanner;

public class SimplexMethod {
    private static final int MAX_CONSTRAINTS = 10;
    private static final int MAX_VARIABLES = 10;
    private static double[][] tableau = new double[MAX_VARIABLES + 1][MAX_CONSTRAINTS + MAX_VARIABLES + 2];
    private static int numVariables, numConstraints;
    
    // Initialize the tableau with user input
    private static void initializeTableau(Scanner scanner) {
        System.out.println("Enter the coefficients of the objective function:");
        for (int i = 1; i <= numVariables; i++) {
            tableau[numConstraints][i] = -scanner.nextDouble(); // Objective function coefficients
        }

        System.out.println("Enter the coefficients of the constraints:");
        for (int i = 0; i < numConstraints; i++) {
            for (int j = 1; j <= numVariables; j++) {
                tableau[i][j] = scanner.nextDouble(); // Constraint coefficients
            }
        }
        
        System.out.println("Enter the RHS of the constraints:");
        for (int i = 0; i < numConstraints; i++) {
            for (int j = numVariables + 1; j <= numVariables + numConstraints; j++) {
                tableau[i][j] = (i == j - numVariables - 1) ? 1.0 : 0.0; // Slack variables
            }
            tableau[i][numVariables + numConstraints + 1] = scanner.nextDouble(); // RHS values
        }
    }
    
    // Display the current tableau
    private static void showTableau() {
        System.out.println("Current Tableau:");
        for (int i = 0; i <= numConstraints; i++) {
            for (int j = 1; j <= numVariables + numConstraints + 1; j++) {
                System.out.printf("%8.2f", tableau[i][j]);
            }
            System.out.println();
        }
    }
    
    // Check if the current solution is optimal
    private static boolean isOptimal() {
        for (int i = 1; i <= numVariables + numConstraints; i++) {
            if (tableau[numConstraints][i] < 0) {
                return false;
            }
        }
        return true;
    }
    
    // Get the pivot column (most negative value in the objective row)
    private static int getPivotColumn() {
        int index = 1;
        double min = tableau[numConstraints][1];
        for (int i = 2; i <= numVariables + numConstraints; i++) {
            if (tableau[numConstraints][i] < min) {
                min = tableau[numConstraints][i];
                index = i;
            }
        }
        return index;
    }
    
    // Get the pivot row (minimum ratio test)
    private static int getPivotRow(int pivotColumn) {
        double minRatio = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < numConstraints; i++) {
            if (tableau[i][pivotColumn] > 0) {
                double ratio = tableau[i][numVariables + numConstraints + 1] / tableau[i][pivotColumn];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    index = i;
                }
            }
        }
        return index;
    }
    
    // Perform the pivot operation
    private static void performPivot(int pivotRow, int pivotColumn) {
        double pivotValue = tableau[pivotRow][pivotColumn];
        for (int j = 1; j <= numVariables + numConstraints + 1; j++) {
            tableau[pivotRow][j] /= pivotValue;
        }
        
        for (int i = 0; i <= numConstraints; i++) {
            if (i != pivotRow) {
                double factor = tableau[i][pivotColumn];
                for (int j = 1; j <= numVariables + numConstraints + 1; j++) {
                    tableau[i][j] -= factor * tableau[pivotRow][j];
                }
            }
        }
    }
    
    // Display the optimal solution
    private static void displaySolution() {
        System.out.println("Optimal solution found:");
        System.out.printf("Max Z = %.2f\n", tableau[numConstraints][numVariables + numConstraints + 1]);
        for (int i = 1; i <= numVariables; i++) {
            boolean isBasic = true;
            int basicRow = -1;
            for (int j = 0; j < numConstraints; j++) {
                if (tableau[j][i] == 1 && basicRow == -1) {
                    basicRow = j;
                } else if (tableau[j][i] != 0) {
                    isBasic = false;
                    break;
                }
            }
            if (isBasic && basicRow != -1) {
                System.out.printf("x%d = %.2f\n", i, tableau[basicRow][numVariables + numConstraints + 1]);
            } else {
                System.out.printf("x%d = 0.00\n", i);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of variables: ");
        numVariables = scanner.nextInt();
        System.out.print("Enter number of constraints: ");
        numConstraints = scanner.nextInt();
        
        initializeTableau(scanner);
        
        System.out.println("Initial Tableau:");
        showTableau();
        
        while (!isOptimal()) {
            int pivotColumn = getPivotColumn();
            int pivotRow = getPivotRow(pivotColumn);
            
            if (pivotRow == -1) {
                System.out.println("Unbounded Solution");
                return;
            }
            
            performPivot(pivotRow, pivotColumn);
            System.out.println("Updated Tableau:");
            showTableau();
        }
        
        displaySolution();
        scanner.close();
    }
}