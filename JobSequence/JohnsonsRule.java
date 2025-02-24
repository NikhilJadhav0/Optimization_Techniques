import java.util.*;

class Job {
    int id, machine1Time, machine2Time;

    public Job(int id, int machine1Time, int machine2Time) {
        this.id = id;
        this.machine1Time = machine1Time;
        this.machine2Time = machine2Time;
    }
}

public class JohnsonsRule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of jobs: ");
        int n = scanner.nextInt();
        
        List<Job> jobs = new ArrayList<>();
        System.out.println("Enter processing times for each job (Machine 1 Time and Machine 2 Time):");
        for (int i = 0; i < n; i++) {
            System.out.print("Job " + (i + 1) + " -> ");
            int m1 = scanner.nextInt();
            int m2 = scanner.nextInt();
            jobs.add(new Job(i + 1, m1, m2));
        }
        
        scanner.close();
        
        List<Job> sequence = johnsonsRule(jobs);
        
        System.out.println("\nOptimal Job Sequence:");
        for (Job job : sequence) {
            System.out.print("J" + job.id + " -> ");
        }
        System.out.println("End\n");
        
        calculateElapsedTime(sequence);
    }
    
    public static List<Job> johnsonsRule(List<Job> jobs) {
        List<Job> firstList = new ArrayList<>();
        List<Job> lastList = new ArrayList<>();
        
        while (!jobs.isEmpty()) {
            Job minJob = Collections.min(jobs, Comparator.comparingInt(j -> Math.min(j.machine1Time, j.machine2Time)));
            
            if (minJob.machine1Time < minJob.machine2Time) {
                firstList.add(minJob);
            } else {
                lastList.add(0, minJob);
            }
            jobs.remove(minJob);
        }
        
        firstList.addAll(lastList);
        return firstList;
    }
    
    public static void calculateElapsedTime(List<Job> sequence) {
        int n = sequence.size();
        int[] inTimeM1 = new int[n];
        int[] outTimeM1 = new int[n];
        int[] inTimeM2 = new int[n];
        int[] outTimeM2 = new int[n];
        int[] idleTimeM2 = new int[n];
        
        inTimeM1[0] = 0;
        outTimeM1[0] = sequence.get(0).machine1Time;
        inTimeM2[0] = outTimeM1[0];
        outTimeM2[0] = inTimeM2[0] + sequence.get(0).machine2Time;
        idleTimeM2[0] = Math.max(0, inTimeM2[0] - outTimeM1[0]);
        
        for (int i = 1; i < n; i++) {
            inTimeM1[i] = outTimeM1[i - 1];
            outTimeM1[i] = inTimeM1[i] + sequence.get(i).machine1Time;
            inTimeM2[i] = Math.max(outTimeM1[i], outTimeM2[i - 1]);
            outTimeM2[i] = inTimeM2[i] + sequence.get(i).machine2Time;
            idleTimeM2[i] = Math.max(0, inTimeM2[i] - outTimeM2[i - 1]);
        }
        
        System.out.println("Job\tM1 In\tM1 Out\tM2 In\tM2 Out");
        for (int i = 0; i < n; i++) {
            System.out.println("J" + sequence.get(i).id + "\t" + inTimeM1[i] + "\t" + outTimeM1[i] + "\t" + inTimeM2[i] + "\t" + outTimeM2[i]);
        }
        
        System.out.println("\nIdle Time Table:");
        System.out.println("Job\tIdle Time on Machine 2");
        for (int i = 0; i < n; i++) {
            System.out.println("J" + sequence.get(i).id + "\t" + idleTimeM2[i]);
        }
        
        int totalElapsedTime = outTimeM2[n - 1];
        int totalIdleTimeM2 = Arrays.stream(idleTimeM2).sum();
        
        System.out.println("\nTotal Elapsed Time = " + totalElapsedTime);
        System.out.println("Idle Time of Machine 1 = 0");
        System.out.println("Idle Time of Machine 2 = " + totalIdleTimeM2);
    }
}
