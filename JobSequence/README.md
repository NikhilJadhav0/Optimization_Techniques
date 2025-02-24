# Johnson's Rule for Job Sequencing

## Introduction
The sequencing problem involves determining the optimal order of performing a set of jobs on multiple machines to minimize total elapsed time. **Johnson’s Rule** is an efficient algorithm used for sequencing **n jobs on 2 machines** to achieve an optimal order while minimizing idle time and total processing time.

## Johnson’s Algorithm
Johnson’s rule follows these steps:
1. Identify the smallest processing time among all jobs on **Machine 1 and Machine 2**.
2. If the smallest time is on **Machine 1**, schedule that job **first**.
3. If the smallest time is on **Machine 2**, schedule that job **last**.
4. If there is a tie, apply further rules:
   - If the minimum time is the same for both machines, schedule the job from **Machine 1 first**.
   - If there’s a tie in **Machine 1**, select the job with the minimum **Machine 2** time and process it first.
   - If there’s a tie in **Machine 2**, select the job with the minimum **Machine 1** time and process it last.
5. Repeat until all jobs are scheduled.
6. Calculate the **total elapsed time** and **idle time** for each machine.

---
## Example
### Given Jobs and Processing Times:
| Job | Machine 1 Time | Machine 2 Time |
|----|-------------|-------------|
| J1 | 9 | 7 |
| J2 | 5 | 4 |
| J3 | 10 | 9 |
| J4 | 1 | 5 |
| J5 | 3 | 2 |

### Optimal Job Sequence:
```
J4 -> J3 -> J1 -> J2 -> J5
```

### Elapsed Time Table:
| Job | M1 In | M1 Out | M2 In | M2 Out |
|----|------|------|------|------|
| J4 | 0  | 1  | 1  | 6  |
| J3 | 1  | 11 | 6  | 15 |
| J1 | 11 | 20 | 15 | 22 |
| J2 | 20 | 25 | 22 | 26 |
| J5 | 25 | 28 | 26 | 28 |

### Idle Time Table:
| Job | Idle Time on Machine 2 |
|----|----------------------|
| J4 | 1 |
| J3 | 0 |
| J1 | 0 |
| J2 | 0 |
| J5 | 0 |

### Total Elapsed Time: `28`
### Idle Time of Machine 1: `0`
### Idle Time of Machine 2: `1`

---
## How to Run the Java Program
### **Prerequisites:**
- Ensure you have **Java** installed.

### **Steps to Run:**
1. **Save the Java file** as `JohnsonsRule.java`.
2. **Compile the program** using:
   ```sh
   javac JohnsonsRule.java
   ```
3. **Run the program**:
   ```sh
   java JohnsonsRule
   ```
4. **Enter job processing times** when prompted.
5. **View the optimal sequence, elapsed time, and idle time.**

---
## Code
The Java implementation of Johnson’s Rule is available in `JohnsonsRule.java`. The program takes input for job processing times, determines the optimal sequence, and calculates elapsed time and idle time.

For any modifications or improvements, feel free to contribute!

