package ObjectClasses.TimeTable;

public class BasicLesson { //information about a lesson in the schedule
    private Subject subject; // subject name
    private int classId; // class id


    private int teacherId;  // teacher id


    /** Constructor */
    public BasicLesson(Subject subject, int classId, int teacherId) {
        this.subject = new Subject(subject);
        this.classId = classId;
        this.teacherId = teacherId;
    }

    /** clone constructor */
    public BasicLesson(BasicLesson basicLesson) {
        this.subject = new Subject(basicLesson.subject);
        this.classId = basicLesson.classId;
        this.teacherId = basicLesson.teacherId;
    }

    /** empty constructor */
    public BasicLesson() {
        this.subject = new Subject();
        this.classId = 0;
        this.teacherId = 0;
    }


    /** Getters */
    public Subject getSubject() {
        return this.subject; // return the subject
    }


    public int getTeacherId() {
        return this.teacherId; // return the teacherId
    }


}
