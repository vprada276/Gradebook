import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        ArrayList<Student> listOfStudents = new ArrayList<>();

        System.out.println("Welcome to my grade book!");
        System.out.println("Please enter the information of the first student using the following format:");
        System.out.println("firstName lastName PID grade");
        System.out.println("Type DONE when you are finished entering students.");
        System.out.println("Type quit to exit.");

        while (true) {
            String input = scnr.nextLine().trim();

            if (input.equals("quit")) {
                System.out.println("Program ended.");
                break;
            }

            if (input.equals("DONE")) {
                if (listOfStudents.isEmpty()) {
                    System.out.println("No students were entered.");
                    System.out.println("Please enter at least one student before typing DONE.");
                    continue;
                }

                Gradebook gradebook = new Gradebook(listOfStudents);
                handleCommands(scnr, gradebook);
                break;
            }

            Student student = parseStudentInput(input, listOfStudents);
            if (student != null) {
                listOfStudents.add(student);
                System.out.println("Student added successfully.");
                System.out.println("Enter the next student using the same format, or type DONE.");
            }
        }

        scnr.close();
    }

    private static Student parseStudentInput(String input, ArrayList<Student> listOfStudents) {
        String[] token = input.split("\\s+");

        if (token.length != 4) {
            System.out.println("Incorrect number of elements provided. Format: firstName lastName PID grade");
            return null;
        }

        String firstName = token[0];
        String lastName = token[1];
        String pidStr = token[2];
        String scoreStr = token[3];

        if (!isValidFirstName(firstName)) {
            System.out.println("Invalid first name.");
            System.out.println("First name must be one word, letters only, start with a capital letter, and contain no spaces.");
            return null;
        }

        if (!isValidLastName(lastName)) {
            System.out.println("Invalid last name.");
            System.out.println("Last name must be one word, start with a capital letter, contain only letters, and may contain at most one dot.");
            return null;
        }

        int pid;
        try {
            pid = Integer.parseInt(pidStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid PID. PID must be a 7-digit number.");
            return null;
        }

        if (!isValidPid(pid)) {
            System.out.println("Invalid PID. PID must be a 7-digit number that does not start with 0.");
            return null;
        }

        if (pidExistsInList(pid, listOfStudents)) {
            System.out.println("Invalid PID. A student with that PID already exists.");
            return null;
        }

        int score;
        try {
            score = Integer.parseInt(scoreStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid score. Score must be an integer between 0 and 100.");
            return null;
        }

        if (!isValidScore(score)) {
            System.out.println("Invalid score. Score must be between 0 and 100.");
            return null;
        }

        Grade grade = new Grade(score);
        return new Student(firstName, lastName, pid, grade);
    }

    private static boolean pidExistsInList(int pid, ArrayList<Student> listOfStudents) {
        for (Student s : listOfStudents) {
            if (s.getPid() == pid) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidFirstName(String firstName) {
        return firstName.matches("[A-Z][a-zA-Z]*");
    }

    private static boolean isValidLastName(String lastName) {
        return lastName.matches("[A-Z][a-zA-Z]*\\.?[a-zA-Z]*");
    }

    private static boolean isValidPid(int pid) {
        return pid >= 1000000 && pid <= 9999999;
    }

    private static boolean isValidScore(int score) {
        return score >= 0 && score <= 100;
    }

    private static void handleCommands(Scanner scnr, Gradebook gradebook) {
        System.out.println("Gradebook ready.");
        printCommandMenu();

        while (true) {
            System.out.println("Please enter a new command:");
            String input = scnr.nextLine().trim();

            if (input.equals("quit")) {
                System.out.println("Program ended.");
                break;
            }

            if (input.equals("min score")) {
                System.out.println(gradebook.getMinScore());

            } else if (input.equals("min letter")) {
                System.out.println(gradebook.getMinLetterGrade());

            } else if (input.equals("max score")) {
                System.out.println(gradebook.getMaxScore());

            } else if (input.equals("max letter")) {
                System.out.println(gradebook.getMaxLetterGrade());

            } else if (input.equals("average score")) {
                System.out.println(gradebook.calculateAvg());

            } else if (input.equals("median score")) {
                System.out.println(gradebook.calculateMedian());

            } else if (input.equals("average letter")) {
                System.out.println(gradebook.getAverageLetter());

            } else if (input.equals("median letter")) {
                System.out.println(gradebook.getMedianLetter());

            } else if (input.equals("tab scores")) {
                gradebook.printAllStudentsScores();

            } else if (input.equals("tab letters")) {
                gradebook.printAllStudentsLetters();

            } else if (input.startsWith("letter ")) {
                handleLetterCommand(input, gradebook);

            } else if (input.startsWith("name ")) {
                handleNameCommand(input, gradebook);

            } else if (input.startsWith("change ")) {
                handleChangeCommand(input, gradebook);

            } else if (input.startsWith("save ")) {
                handleSaveCommand(input, gradebook);

            } else if (input.startsWith("load ")) {
                handleLoadCommand(input, gradebook);

            } else if (input.equals("help")) {
                printCommandMenu();

            } else {
                System.out.println("Invalid command. Type help to see available commands.");
            }
        }
    }

    private static void handleLetterCommand(String input, Gradebook gradebook) {
        String[] parts = input.split("\\s+");

        if (parts.length != 2) {
            System.out.println("Invalid command format. Use: letter XXXXXXX");
            return;
        }

        try {
            int pid = Integer.parseInt(parts[1]);
            System.out.println(gradebook.getLetterPid(pid));
        } catch (NumberFormatException e) {
            System.out.println("Invalid PID. PID must be numeric.");
        }
    }

    private static void handleNameCommand(String input, Gradebook gradebook) {
        String[] parts = input.split("\\s+");

        if (parts.length != 2) {
            System.out.println("Invalid command format. Use: name XXXXXXX");
            return;
        }

        try {
            int pid = Integer.parseInt(parts[1]);
            System.out.println(gradebook.getFullNamePid(pid));
        } catch (NumberFormatException e) {
            System.out.println("Invalid PID. PID must be numeric.");
        }
    }

    private static void handleChangeCommand(String input, Gradebook gradebook) {
        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            System.out.println("Invalid command format. Use: change XXXXXXX YY");
            return;
        }

        try {
            int pid = Integer.parseInt(parts[1]);
            int score = Integer.parseInt(parts[2]);

            if (!isValidScore(score)) {
                System.out.println("Invalid score. Score must be between 0 and 100.");
                return;
            }

            gradebook.changeGrade(pid, score);
        } catch (NumberFormatException e) {
            System.out.println("Invalid command. PID and score must be numeric.");
        }
    }

    private static void handleSaveCommand(String input, Gradebook gradebook) {
        String[] parts = input.split("\\s+", 2);

        if (parts.length != 2 || parts[1].isBlank()) {
            System.out.println("Invalid command format. Use: save filename.csv");
            return;
        }

        gradebook.saveToCSV(parts[1]);
    }

    private static void handleLoadCommand(String input, Gradebook gradebook) {
        String[] parts = input.split("\\s+", 2);

        if (parts.length != 2 || parts[1].isBlank()) {
            System.out.println("Invalid command format. Use: load filename.csv");
            return;
        }

        gradebook.loadFromCSV(parts[1]);
    }

    private static void printCommandMenu() {
        System.out.println("Available commands:");
        System.out.println("min score");
        System.out.println("min letter");
        System.out.println("max score");
        System.out.println("max letter");
        System.out.println("average score");
        System.out.println("median score");
        System.out.println("average letter");
        System.out.println("median letter");
        System.out.println("letter XXXXXXX");
        System.out.println("name XXXXXXX");
        System.out.println("change XXXXXXX YY");
        System.out.println("tab scores");
        System.out.println("tab letters");
        System.out.println("save filename.csv");
        System.out.println("load filename.csv");
        System.out.println("help");
        System.out.println("quit");
    }
}