import java.util.Scanner;
import java.util.InputMismatchException;

public class Student_Grading_System {
    public static void main(String[] args) {
        System.out.println("Student Grading System");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter number of students: ");
            int totalStudents = scanner.nextInt();

            String valedictorian = "None";
            double highestAverage = -1;
            double totalClassSum = 0;
            int atRiskCount = 0; 

            for (int i = 0; i < totalStudents; i++) {
                System.out.println("\nStudent " + (i + 1) + " of " + totalStudents);

                System.out.print("Enter name: ");
                String name = scanner.next();

                System.out.print("Enter Math score [0-100]: ");
                double math = scanner.nextDouble();

                System.out.print("Enter Science score [0-100]: ");
                double science = scanner.nextDouble();

                System.out.print("Enter Coding score [0-100]: ");
                double coding = scanner.nextDouble();

                if (!isValid(math) || !isValid(science) || !isValid(coding)) {
                    System.out.println("Error: Scores must be 0-100. Skipping entry.");
                    continue; 
                }

                double studentAverage = (math + science + coding) / 3;
                totalClassSum += studentAverage;

                int failedSubjects = 0;
                if (math < 60) failedSubjects++;
                if (science < 60) failedSubjects++;
                if (coding < 60) failedSubjects++;

                String statusAlert = "STABLE";
                if (failedSubjects >= 2) {
                    statusAlert = "CRITICAL RISK (Failing " + failedSubjects + " subjects)";
                    atRiskCount++;
                } else if (failedSubjects == 1) {
                    statusAlert = "WARNING (Failing 1 subject)";
                }

                if (studentAverage > highestAverage) {
                    highestAverage = studentAverage;
                    valedictorian = name;
                }

                System.out.println(name + " | Avg: " + String.format("%.2f", studentAverage) + "% | Grade: " + getLetterGrade(studentAverage) + " | Status: " + statusAlert);
            }

            if (totalStudents > 0) {
                double overallClassAverage = totalClassSum / totalStudents;

                System.out.println("\n=== BATCH INSIGHTS ===");
                System.out.println("Class Average: " + String.format("%.2f", overallClassAverage) + "%");
                System.out.println("Top Student: " + valedictorian + " (" + String.format("%.2f", highestAverage) + "%)");
                System.out.println("Students Requiring Intervention: " + atRiskCount);
                System.out.println("Batch Health: " + getBatchHealth(overallClassAverage, atRiskCount));
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid number format entered.");
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred.");
        }
    }

    private static boolean isValid(double score) {
        return score >= 0 && score <= 100;
    }

    private static String getLetterGrade(double average) {
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }

    private static String getBatchHealth(double classAvg, int riskCount) {
        if (riskCount == 0 && classAvg >= 80) return "EXCELLENT";
        if (riskCount > 0 && classAvg < 70) return "CRITICAL";
        return "MODERATE";
    }
}