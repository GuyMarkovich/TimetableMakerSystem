package ObjectClasses.Users;

import java.util.HashMap;

public class ClassObj {

    private int classId;
    private int size;

    private int majorId;

    private HashMap<Integer, Integer> hoursPerSubject; // holds the number of hours per subject

    private HashMap<Integer, Integer> assignedTeachers; // holds the teacher id for each subject


    // constructor for a class object
    public ClassObj(int classId, int size, int majorId) {
        this.classId = classId;
        this.size = size;
        this.majorId = majorId;
        this.hoursPerSubject = new HashMap<Integer, Integer>();
    }

    public void setHoursPerSubject(HashMap<Integer, Integer> hoursPerSubject) {
        // deep clone hoursPerSubject
        for (int key : hoursPerSubject.keySet()) {
            this.hoursPerSubject.put(key, hoursPerSubject.get(key));
        }
    }


    // getters
    public int getClassId() {
        return this.classId;
    }
    public int getSize() {
        return this.size;
    }
    public int getMajorId() {
        return this.majorId;
    }

    public int getHoursPerSubject(int subjectId) {
        return this.hoursPerSubject.get(subjectId);
    }


}
