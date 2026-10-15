package dutydowntime;

public class DutyRecommendation
{
    private LinkedChain<AcademicTask> academicTasks;
    private LinkedChain<HobbyTask> hobbyTasks;
    private boolean suggestNextHobby;

    /**
     * Initializes the academic and hobby task chains.
     */
    public DutyRecommendation()
    {
        this.academicTasks = new LinkedChain<AcademicTask>();
        this.hobbyTasks = new LinkedChain<HobbyTask>();
        this.suggestNextHobby = false;
    }

    /**
     * Adds an academic task to the chain.
     */
    public void addAcademicTask(
        String courseCode,
        String name,
        String dueDate,
        double hours)
    {
        AcademicTask task = new AcademicTask(courseCode, name, dueDate, hours);
        academicTasks.add(task);
    }

    /**
     * Adds a hobby task to the chain.
     */
    public void addHobbyTask(
        String category,
        String description,
        double hours,
        String goal)
    {
        HobbyTask task = new HobbyTask(category, description, hours, goal);
        hobbyTasks.add(task);
    }

    /**
     * Safely removes an academic task without throwing constructor exceptions.
     */
    public boolean removeAcademicTask(String courseCode, String name)
    {
        if (academicTasks.isEmpty() || courseCode == null || name == null)
        {
            return false;
        }
        for (int i = 0; i < academicTasks.size(); i++)
        {
            AcademicTask current = academicTasks.get(i);
            if (current.getCourseCode().equalsIgnoreCase(courseCode.trim())
                && current.getAssignmentName().equalsIgnoreCase(name.trim()))
            {
                return academicTasks.remove(current);
            }
        }
        return false;
    }

    /**
     * Safely removes a hobby task without throwing constructor exceptions.
     */
    public boolean removeHobbyTask(String category, String description)
    {
        if (hobbyTasks.isEmpty() || category == null || description == null)
        {
            return false;
        }
        for (int i = 0; i < hobbyTasks.size(); i++)
        {
            HobbyTask current = hobbyTasks.get(i);
            if (current.getHobbyCategory().equalsIgnoreCase(category.trim())
                && current.getActivityDescription().equalsIgnoreCase(
                    description.trim()))
            {
                return hobbyTasks.remove(current);
            }
        }
        return false;
    }

    /**
     * Marks an academic task complete by index.
     */
    public String completeAcademicTask(int position)
    {
        AcademicTask task = academicTasks.get(position);
        boolean newlyCompleted = task.markCompleted();
        if (newlyCompleted)
        {
            suggestNextHobby = true;
            String rec = getNextRecommendation();
            return "Academic task completed! " + rec;
        }
        return "Task is already completed.";
    }

    /**
     * Marks a hobby task complete by index.
     */
    public String completeHobbyTask(int position)
    {
        HobbyTask task = hobbyTasks.get(position);
        boolean newlyCompleted = task.markCompleted();
        if (newlyCompleted)
        {
            suggestNextHobby = false;
            String rec = getNextRecommendation();
            return "Hobby task completed! " + rec;
        }
        return "Hobby task is already completed.";
    }

    /**
     * Generates the next recommended activity alternating between duty and downtime.
     */
    public String getNextRecommendation()
    {
        if (suggestNextHobby)
        {
            for (int i = 0; i < hobbyTasks.size(); i++)
            {
                HobbyTask task = hobbyTasks.get(i);
                if (!task.isCompleted())
                {
                    return "Suggested Hobby: " + task.toString();
                }
            }
            for (int i = 0; i < academicTasks.size(); i++)
            {
                AcademicTask task = academicTasks.get(i);
                if (!task.isCompleted())
                {
                    return "No Hobby Tasks left. Suggested Academic Task: "
                        + task.toString();
                }
            }
            return "No tasks left. Add one or relax!";
        }
        else
        {
            for (int i = 0; i < academicTasks.size(); i++)
            {
                AcademicTask task = academicTasks.get(i);
                if (!task.isCompleted())
                {
                    return "Suggested Academic Task: " + task.toString();
                }
            }
            for (int i = 0; i < hobbyTasks.size(); i++)
            {
                HobbyTask task = hobbyTasks.get(i);
                if (!task.isCompleted())
                {
                    return "No Academic Tasks left. Suggested Hobby: "
                        + task.toString();
                }
            }
            return "No tasks left. Add one or relax!";
        }
    }

    public int countActiveAcademic()
    {
        int count = 0;
        for (int i = 0; i < academicTasks.size(); i++)
        {
            if (!academicTasks.get(i).isCompleted())
            {
                count++;
            }
        }
        return count;
    }

    public int countActiveHobby()
    {
        int count = 0;
        for (int i = 0; i < hobbyTasks.size(); i++)
        {
            if (!hobbyTasks.get(i).isCompleted())
            {
                count++;
            }
        }
        return count;
    }

    public int getAcademicCount()
    {
        return academicTasks.size();
    }

    public int getHobbyCount()
    {
        return hobbyTasks.size();
    }

    public AcademicTask getAcademicTask(int index)
    {
        return academicTasks.get(index);
    }

    public HobbyTask getHobbyTask(int index)
    {
        return hobbyTasks.get(index);
    }

    /**
     * Stretch Goal: Keyword search across academic and hobby tasks.
     */
    public String searchTasks(String query)
    {
        if (query == null || query.trim().isEmpty())
        {
            return "Please enter a valid search term.";
        }
        String cleanQuery = query.trim().toLowerCase();
        StringBuilder sb = new StringBuilder("=== Search Results ===\n");
        int matches = 0;

        for (int i = 0; i < academicTasks.size(); i++)
        {
            AcademicTask task = academicTasks.get(i);
            if (task.getCourseCode().toLowerCase().contains(cleanQuery)
                || task.getAssignmentName().toLowerCase().contains(cleanQuery))
            {
                sb.append(" [Academic] ").append(task.toString()).append("\n");
                matches++;
            }
        }
        for (int i = 0; i < hobbyTasks.size(); i++)
        {
            HobbyTask task = hobbyTasks.get(i);
            if (task.getHobbyCategory().toLowerCase().contains(cleanQuery)
                || task.getActivityDescription().toLowerCase().contains(
                    cleanQuery))
            {
                sb.append(" [Hobby] ").append(task.toString()).append("\n");
                matches++;
            }
        }

        if (matches == 0)
        {
            return "No tasks found matching: " + query;
        }
        return sb.toString();
    }

    public String toString()
    {
        StringBuilder academicString = new StringBuilder("Academic Tasks: [");
        for (int i = 0; i < academicTasks.size(); i++)
        {
            academicString.append(academicTasks.get(i).toString());
            if (i < academicTasks.size() - 1)
            {
                academicString.append(", ");
            }
        }
        academicString.append("]");

        StringBuilder hobbyString = new StringBuilder("Hobby Tasks: [");
        for (int i = 0; i < hobbyTasks.size(); i++)
        {
            hobbyString.append(hobbyTasks.get(i).toString());
            if (i < hobbyTasks.size() - 1)
            {
                hobbyString.append(", ");
            }
        }
        hobbyString.append("]");

        return academicString.toString() + "\n" + hobbyString.toString();
    }
}
