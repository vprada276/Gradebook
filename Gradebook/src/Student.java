public class Student {

    private String firstName;
    private String lastName;
    private int pid;
    private Grade grade;

    public Student (String firstName, String lastName, int pid, Grade grade){
        this.firstName = firstName;
        this.lastName = lastName;
        this.pid = pid;
        this.grade = grade;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getPid() {
        return pid;
    }

    public void setScore(Grade grade){//grade setter, used when user wants to change grade from specific student
        this.grade = grade;
    }
    public Grade getGrade() {
        return grade;
    }


}


