import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Gradebook {

    private ArrayList<Student> listOfStudents;

    public Gradebook(ArrayList<Student> listOfStudents) {
        if (listOfStudents == null) {
            throw new IllegalArgumentException("Student list cannot be null.");
        }
        this.listOfStudents = new ArrayList<>(listOfStudents);
    }

    private void validateNotEmpty() {
        if (listOfStudents.isEmpty()) {
            throw new IllegalStateException("Gradebook is empty.");
        }
    }

    private String convertScoreToLetter(int score) {
        Grade g = new Grade(score);
        return g.getLetterGrade(score);
    }

    public double calculateAvg() {
        validateNotEmpty();

        double sum = 0;
        for (Student s : listOfStudents) {
            sum += s.getGrade().getScore();
        }

        return sum / listOfStudents.size();
    }

    public float calculateMedian() {
        validateNotEmpty();

        int n = listOfStudents.size();
        int[] scores = new int[n];
        int i = 0;

        for (Student s : listOfStudents) {
            scores[i++] = s.getGrade().getScore();
        }

        Arrays.sort(scores);

        if (n % 2 == 0) {
            return (scores[n / 2] + scores[(n / 2) - 1]) / 2.0f;
        } else {
            return scores[n / 2];
        }
    }

    public void printAllStudentsScores() {
        validateNotEmpty();

        System.out.printf("%-15s %-15s %-15s %-10s%n", "First name", "Last name", "PID", "Score");

        for (Student s : listOfStudents) {
            System.out.printf(
                    "%-15s %-15s %-15d %-10d%n",
                    s.getFirstName(),
                    s.getLastName(),
                    s.getPid(),
                    s.getGrade().getScore()
            );
        }
    }

    public void printAllStudentsLetters() {
        validateNotEmpty();

        System.out.printf("%-15s %-15s %-15s %-10s%n", "First name", "Last name", "PID", "Letter");

        for (Student s : listOfStudents) {
            System.out.printf(
                    "%-15s %-15s %-15d %-10s%n",
                    s.getFirstName(),
                    s.getLastName(),
                    s.getPid(),
                    s.getGrade().getLetterGrade(s.getGrade().getScore())
            );
        }
    }

    public int getMinScore() {
        validateNotEmpty();

        int min = Integer.MAX_VALUE;

        for (Student s : listOfStudents) {
            int score = s.getGrade().getScore();
            if (score < min) {
                min = score;
            }
        }

        return min;
    }

    public String getMinLetterGrade() {
        return convertScoreToLetter(getMinScore());
    }

    public int getMaxScore() {
        validateNotEmpty();

        int max = Integer.MIN_VALUE;

        for (Student s : listOfStudents) {
            int score = s.getGrade().getScore();
            if (score > max) {
                max = score;
            }
        }

        return max;
    }

    public String getMaxLetterGrade() {
        return convertScoreToLetter(getMaxScore());
    }

    public String getAverageLetter() {
        int avgLetter = (int) Math.round(calculateAvg());
        return convertScoreToLetter(avgLetter);
    }

    public String getMedianLetter() {
        int medLetter = Math.round(calculateMedian());
        return convertScoreToLetter(medLetter);
    }

    public String getLetterPid(int pid) {
        for (Student s : listOfStudents) {
            if (s.getPid() == pid) {
                return s.getGrade().getLetterGrade(s.getGrade().getScore());
            }
        }
        return "Student not found, enter valid PID";
    }

    public String getFullNamePid(int pid) {
        for (Student s : listOfStudents) {
            if (s.getPid() == pid) {
                return s.getFirstName() + " " + s.getLastName();
            }
        }
        return "Student not found, enter valid PID";
    }

    public void changeGrade(int pid, int newScore) {
        for (Student student : listOfStudents) {
            if (student.getPid() == pid) {
                student.setScore(new Grade(newScore));
                System.out.println("Grade updated successfully.");
                return;
            }
        }

        System.out.println("Student not found, enter valid PID");
    }

    public boolean pidExists(int pid) {
        for (Student s : listOfStudents) {
            if (s.getPid() == pid) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Student> getListOfStudents() {
        return new ArrayList<>(listOfStudents);
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        listOfStudents.add(student);
    }

    public boolean removeStudentByPid(int pid) {
        for (int i = 0; i < listOfStudents.size(); i++) {
            if (listOfStudents.get(i).getPid() == pid) {
                listOfStudents.remove(i);
                return true;
            }
        }
        return false;
    }

    public void saveToCSV(String fileName) {
        validateNotEmpty();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("FirstName,LastName,PID,Score,LetterGrade");
            writer.newLine();

            for (Student s : listOfStudents) {
                String firstName = escapeCSV(s.getFirstName());
                String lastName = escapeCSV(s.getLastName());
                int pid = s.getPid();
                int score = s.getGrade().getScore();
                String letter = escapeCSV(s.getGrade().getLetterGrade(score));

                writer.write(firstName + "," + lastName + "," + pid + "," + score + "," + letter);
                writer.newLine();
            }

            System.out.println("CSV file saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving CSV file: " + e.getMessage());
        }
    }

    public void loadFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            int loadedCount = 0;
            int skippedCount = 0;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip header
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 4) {
                    skippedCount++;
                    continue;
                }

                String firstName = parts[0].trim();
                String lastName = parts[1].trim();

                int pid;
                int score;

                try {
                    pid = Integer.parseInt(parts[2].trim());
                    score = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    skippedCount++;
                    continue;
                }

                if (pidExists(pid)) {
                    skippedCount++;
                    continue;
                }

                if (score < 0 || score > 100) {
                    skippedCount++;
                    continue;
                }

                Student student = new Student(firstName, lastName, pid, new Grade(score));
                listOfStudents.add(student);
                loadedCount++;
            }

            System.out.println("CSV loaded successfully.");
            System.out.println("Students added: " + loadedCount);
            System.out.println("Rows skipped: " + skippedCount);

        } catch (IOException e) {
            System.out.println("Error loading CSV file: " + e.getMessage());
        }
    }

    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }

        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }

        return value;
    }
}