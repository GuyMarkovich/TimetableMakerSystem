package ObjectClasses.TimeTable;

// class the subject of a lesson including all teachers that teach it
public class Subject {
    private int subjectId;
    private String subjectName;
    private int teacherId; //teacherId of the teacher who teaches this subject



    public Subject(int subjectId, String subjectName, int teacherId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
    }


    // clone constructor
    public Subject(Subject subject) {
        this.subjectId = subject.subjectId;
        this.subjectName = subject.subjectName;
        this.teacherId = subject.teacherId;
    }

    // empty constructor
    public Subject() {
        this.subjectId = 0;
        this.subjectName = "";
        this.teacherId = 0;
    }

    // Getters
    public int getSubjectId() {
        return this.subjectId;
    }
    public String getSubjectName() {
        return this.subjectName;
    }
    public int getTeacherId() {
        return this.teacherId;
    }

    // Setters
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
