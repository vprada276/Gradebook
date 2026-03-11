
public class Grade {

    private int score;
    private String letterGrade;


    public int getScore() {
        return score;
    }


    public Grade(int score){
        this.score = score;
    }
    public String getLetterGrade(int score) {

        if(score >= 95) {
            letterGrade = "A";
        }else if(score >= 90) {
            letterGrade = "A-";
        }else if(score >= 87) {
            letterGrade = "B+";
        }else if(score >= 83) {
            letterGrade = "B";
        }else if(score >= 80) {
            letterGrade = "B-";
        }else if(score >= 77) {
            letterGrade = "C+";
        }else if(score >= 70) {
            letterGrade = "C";
        }else if(score >= 60) {
            letterGrade = "D";
        }else{
            letterGrade = "F";
        }
        return letterGrade;
    }


}

