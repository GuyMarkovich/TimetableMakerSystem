package ObjectClasses.TimeTable;

public class BasicLesson { //information about a lesson in the schedule
    private Subject subject; // subject name
    private int classId; // class id


    private int teacherId;  // teacher id


    // Constructor
    public BasicLesson(Subject subject, int classId, int teacherId) {
        this.subject = new Subject(subject);
        this.classId = classId;
        this.teacherId = teacherId;
    }

    // empty constructor
    public BasicLesson() {
        this.subject = new Subject();
        this.classId = 0;
        this.teacherId = 0;
    }


    // Getters
    public Subject getSubject() {
        return this.subject;
    }
    public int getClassId() {
        return this.classId;
    }

    public int getTeacherId() {
        return this.teacherId;
    }

    // Setters
    public void setSubject(Subject subject) {
        this.subject = new Subject(subject);
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
