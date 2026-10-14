package dutydowntime;

public class DutyRecommendation {
    private LinkedChain<AcademicTask> academicTasks;
    private LinkedChain<HobbyTask> hobbyTasks;
    private boolean suggestNextHobby;
    
    public DutyRecommendation() {
        this.academicTasks = new LinkedChain<AcademicTask>();
        this.hobbyTasks = new LinkedChain<HobbyTask>();
        this.suggestNextHobby = false;
    }
    
    public void addAcademicTask(String courseCode, String name, String dueDate, double hours) {
        AcademicTask task = new AcademicTask(courseCode, name, dueDate, hours);
        academicTasks.add(task);
    }
    
    public void addHobbyTask(String category, String description, double hours, String goal) {
        HobbyTask task = new HobbyTask(category, description, hours, goal);
        hobbyTasks.add(task);
    }
    
    public boolean removeAcademicTask(String courseCode, String name) {
        if (academicTasks.isEmpty()) {
            return false;
        }
     
        AcademicTask dummyTask = new AcademicTask(courseCode, name, "01/01", 1.0);
        return academicTasks.remove(dummyTask);
       
    }
    
    public boolean removeHobbyTask(String category, String description) {
        if (hobbyTasks.isEmpty()) {
            return false;
        }
        
        HobbyTask dummyTask = new HobbyTask(category, description, 1.0, "Goal");
        return hobbyTasks.remove(dummyTask);
        
    }
    
    public String completeAcademicTask(int position) {
        AcademicTask task = academicTasks.getEntry(position);
        boolean newlyCompleted = task.markCompleted();
        if (newlyCompleted) {
            suggestNextHobby = true;
            String rec = getNextRecommendation();
            return "Academic task completed! " + rec;
        } else {
            return "Task is already completed.";
        }
    }
    
    public String completeHobbyTask(int position) {
        HobbyTask task = hobbyTasks.getEntry(position);
        boolean newlyCompleted = task.markCompleted();
        if (newlyCompleted) {
            suggestNextHobby = false;
            String rec = getNextRecommendation();
            return "Hobby task completed! " + rec;
        } else {
            return "Hobby task is already completed.";
        }
    }
    
    public String getNextRecommendation() {
        if (suggestNextHobby) {
            for (HobbyTask task : hobbyTasks) {
                if (!task.isCompleted()) {
                    return "Suggested Hobby: " + task.toString();
                }
            }
            for (AcademicTask task : academicTasks) {
                if (!task.isCompleted()) {
                    return "No Hobby Tasks left. Suggested Academic Task: " + task.toString();
                }
            }
            return "No tasks left. Add one or relax!";
        } else {
            for (AcademicTask task : academicTasks) {
                if (!task.isCompleted()) {
                    return "Suggested Academic Task: " + task.toString();
                }
            }
            for (HobbyTask task : hobbyTasks) {
                if (!task.isCompleted()) {
                    return "No Academic Tasks left. Suggested Hobby: " + task.toString();
                }
            }
            return "No tasks left. Add one or relax!";
        }
    }
    
    public int countActiveAcademic() {
        int count = 0;
        for(AcademicTask task : academicTasks) {
            if (!task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    public int countActiveHobby() {
        int count = 0;
        for (HobbyTask task : hobbyTasks) {
            if (!task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    public int getAcademicCount() {
        return academicTasks.size();
    }
    
    public int getHobbyCount() {
        return hobbyTasks.size();
    }
    
    public String searchTasks(String query) {
        String ans = "";
        for(AcademicTask task : academicTasks) {
            String name = task.getAssignmentName();
            if(name.contains(query)) {
                ans+= task.toString();
                ans+= " ";
            }
        }
        for (HobbyTask task : hobbyTasks) {
            String name = task.getActivityDescription();
            if(name.contains(query)) {
                ans+= task.toString();
                ans+= " ";
            }
        }
    }
    
    public String toString() {
        String academicString = "Academic Tasks: [";
        String hobbyString = "Hobby Tasks: [";
        
        for(AcademicTask task : academicTasks) {
            academicString+= task.toString();
            academicString+= ", ";
        }
        academicString += "]";
        
        for (HobbyTask task : hobbyTasks) {
            hobbyString+= task.toString();
            hobbyString+= ", ";
        }
        
        hobbyString += "]";
        
        return academicString + "\n" + hobbyString;
    }
}
