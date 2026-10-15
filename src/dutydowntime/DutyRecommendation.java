package dutydowntime;

public class DutyRecommendation
{
    private LinkedChain<AcademicTask> academicTasks;
    private LinkedChain<HobbyTask> hobbyTasks;
    private boolean suggestNextHobby;

    /**
     * Creates an empty recommendation system.
     */
    public DutyRecommendation()
    {
        this.academicTasks = new LinkedChain<AcademicTask>();
        this.hobbyTasks = new LinkedChain<HobbyTask>();
        this.suggestNextHobby = false;
    }

    /**
     * Adds an academic task to the end of the list.
     *
     * @param courseCode the class name (e.g. CS 2114)
     * @param name the assignment name
     * @param dueDate the due date
     * @param hours estimated hours to complete
     */
    public void addAcademicTask(
        String courseCode,
        String name,
        String dueDate,
        double hours)
    {
        AcademicTask task = new AcademicTask(courseCode, name, dueDate, hours);
        academicTasks.addToEnd(task);
    }

    /**
     * Adds a hobby task to the end of the list.
     *
     * @param category hobby category (e.g. Fitness)
     * @param description activity details
     * @param hours planned hours
     * @param goal target goal for the session
     */
    public void addHobbyTask(
        String category,
        String description,
        double hours,
        String goal)
    {
        HobbyTask task = new HobbyTask(category, description, hours, goal);
        hobbyTasks.addToEnd(task);
    }

    /**
     * Removes an academic task matching the course code and name.
     *
     * @param courseCode the course code
     * @param name the assignment name
     * @return true if removed, false otherwise
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
     * Removes a hobby task matching the category and description.
     *
     * @param category the hobby category
     * @param description the activity description
     * @return true if removed, false otherwise
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
     * Marks an academic task as completed and suggests a hobby next.
     *
     * @param position 0-based index of the task
     * @return result message
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
     * Marks a hobby task as completed and suggests an academic task next.
     *
     * @param position 0-based index of the task
     * @return result message
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
     * Recommends the next uncompleted task, alternating between work and play.
     *
     * @return recommended task string
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

    /**
     * Counts how many academic tasks are not yet completed.
     *
     * @return number of uncompleted academic tasks
     */
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

    /**
     * Counts how many hobby tasks are not yet completed.
     *
     * @return number of uncompleted hobby tasks
     */
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

    /**
     * Gets the total number of academic tasks.
     *
     * @return total academic tasks
     */
    public int getAcademicCount()
    {
        return academicTasks.size();
    }

    /**
     * Gets the total number of hobby tasks.
     *
     * @return total hobby tasks
     */
    public int getHobbyCount()
    {
        return hobbyTasks.size();
    }

    /**
     * Gets an academic task by index.
     *
     * @param index 0-based index
     * @return the academic task
     */
    public AcademicTask getAcademicTask(int index)
    {
        return academicTasks.get(index);
    }

    /**
     * Gets a hobby task by index.
     *
     * @param index 0-based index
     * @return the hobby task
     */
    public HobbyTask getHobbyTask(int index)
    {
        return hobbyTasks.get(index);
    }

    /**
     * Searches both lists for tasks matching a keyword.
     *
     * @param query search word
     * @return matching tasks or not-found message
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

    /**
     * Returns a numbered list of all tasks.
     *
     * @return readable list of tasks
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Academic Tasks ===\n");
        if (academicTasks.isEmpty())
        {
            sb.append("  (None)\n");
        }
        else
        {
            for (int i = 0; i < academicTasks.size(); i++)
            {
                sb.append("  ").append(i + 1).append(". ")
                  .append(academicTasks.get(i).toString()).append("\n");
            }
        }

        sb.append("\n=== Hobby Tasks ===\n");
        if (hobbyTasks.isEmpty())
        {
            sb.append("  (None)\n");
        }
        else
        {
            for (int i = 0; i < hobbyTasks.size(); i++)
            {
                sb.append("  ").append(i + 1).append(". ")
                  .append(hobbyTasks.get(i).toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
