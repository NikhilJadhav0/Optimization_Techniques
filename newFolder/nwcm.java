package newFolder;

public class nwcm {


    public static void main(String[] args) {
        int[] supply = {20, 30, 25};  // supply from each source
        int[] demand = {10, 25, 15, 25}; // demand at each destination

        int[][] cost = {
            {8, 6, 10, 9},
            {9, 7, 4, 2},
            {3, 4, 2, 5}
        };

        int[][] allocation = new int[supply.length][demand.length];

        int i = 0, j = 0;

        while (i < supply.length && j < demand.length) {
            int min = Math.min(supply[i], demand[j]);
            allocation[i][j] = min;

            supply[i] -= min;
            demand[j] -= min;

            if (supply[i] == 0) {
                i++;
            } else {
                j++;
            }
        }

        // Display result
        System.out.println("Allocation Matrix:");
        int totalCost = 0;
        for (i = 0; i < allocation.length; i++) {
            for (j = 0; j < allocation[0].length; j++) {
                System.out.printf("%5d", allocation[i][j]);
                totalCost += allocation[i][j] * cost[i][j];
            }
            System.out.println();
        }

        System.out.println("\nTotal Transportation Cost (Initial Solution): " + totalCost);
    }
}
