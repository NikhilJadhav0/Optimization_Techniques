package Transportation_problem;

import java.util.Scanner;

public class NorthWestCornerMethod {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of sources: ");
        int m = scanner.nextInt();
        System.out.print("Enter number of destinations: ");
        int n = scanner.nextInt();

        int[][] cost = new int[m + 1][n + 1];
        int[] supply = new int[m + 1];
        int[] demand = new int[n + 1];
        int[][] allocation = new int[m + 1][n + 1];

        System.out.println("Enter the cost matrix: ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter supply for each source: ");
        int totalSupply = 0;
        for (int i = 0; i < m; i++) {
            supply[i] = scanner.nextInt();
            totalSupply += supply[i];
        }

        System.out.println("Enter demand for each destination: ");
        int totalDemand = 0;
        for (int j = 0; j < n; j++) {
            demand[j] = scanner.nextInt();
            totalDemand += demand[j];
        }

        boolean dummyRowAdded = false, dummyColumnAdded = false;
        if (totalSupply > totalDemand) {
            System.out.println("Adding a dummy destination to balance the problem at column " + n);
            demand[n] = totalSupply - totalDemand;
            n++;
            dummyColumnAdded = true;
        } else if (totalDemand > totalSupply) {
            System.out.println("Adding a dummy source to balance the problem at row " + m);
            supply[m] = totalDemand - totalSupply;
            m++;
            dummyRowAdded = true;
        }
        
        System.out.println("\nUpdated Cost Matrix (with dummy if needed):");
        printMatrixWithLabels(cost, m, n, dummyRowAdded, dummyColumnAdded);

        int i = 0, j = 0;
        while (i < m && j < n) {
            int allocationAmount = Math.min(supply[i], demand[j]);
            allocation[i][j] = allocationAmount;
            supply[i] -= allocationAmount;
            demand[j] -= allocationAmount;

            System.out.println("\nIntermediate Allocation Matrix:");
            printMatrixWithLabels(allocation, m, n, dummyRowAdded, dummyColumnAdded);

            if (supply[i] == 0) i++;
            if (demand[j] == 0) j++;
        }

        System.out.println("\nFinal Allocation Matrix:");
        printMatrixWithLabels(allocation, m, n, dummyRowAdded, dummyColumnAdded);

        int totalCost = 0;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                totalCost += allocation[i][j] * cost[i][j];
            }
        }
        System.out.println("\nTotal Transportation Cost: " + totalCost);
        scanner.close();
    }

    public static void printMatrixWithLabels(int[][] matrix, int rows, int cols, boolean dummyRow, boolean dummyCol) {
        System.out.print("\t");
        for (int j = 0; j < cols; j++) {
            if (dummyCol && j == cols - 1) {
                System.out.print("DummyCol\t");
            } else {
                System.out.print("D" + (j + 1) + "\t");
            }
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            if (dummyRow && i == rows - 1) {
                System.out.print("DummyRow\t");
            } else {
                System.out.print("S" + (i + 1) + "\t");
            }

            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
