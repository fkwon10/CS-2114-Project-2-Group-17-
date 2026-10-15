package dutydowntime;

public class HobbyTask
{
    private String hobbyCategory;
    private String activityDescription;
    private double targetHours;
    private String targetGoal;
    private boolean isCompleted;

    /**
     * Creates a new hobby task.
     *
     * @param hobbyCategory
     *            the type of hobby
     * @param activityDescription
     *            the activity description
     * @param targetHours
     *            the planned time for the activity
     * @param targetGoal
     *            the goal for the activity
     */
    public HobbyTask(
        String hobbyCategory,
        String activityDescription,
        double targetHours,
        String targetGoal)
    {
        if (hobbyCategory == null || hobbyCategory.trim().isEmpty()
            || activityDescription == null
            || activityDescription.trim().isEmpty() || targetGoal == null
            || targetGoal.trim().isEmpty() || targetHours <= 0
            || Double.isNaN(targetHours) || Double.isInfinite(targetHours))
        {
            throw new IllegalArgumentException(
                "Invalid hobby task information.");
        }

        this.hobbyCategory = hobbyCategory.trim();
        this.activityDescription = activityDescription.trim();
        this.targetHours = targetHours;
        this.targetGoal = targetGoal.trim();
        this.isCompleted = false;
    }


    public String getHobbyCategory()
    {
        return hobbyCategory;
    }


    public String getActivityDescription()
    {
        return activityDescription;
    }


    public double getTargetHours()
    {
        return targetHours;
    }


    public String getTargetGoal()
    {
        return targetGoal;
    }


    public boolean isCompleted()
    {
        return isCompleted;
    }


    /**
     * Marks this task as completed.
     *
     * @return true if newly completed, false if already completed
     */
    public boolean markCompleted()
    {
        if (isCompleted)
        {
            return false;
        }

        isCompleted = true;
        return true;
    }


    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (!(other instanceof HobbyTask))
        {
            return false;
        }

        HobbyTask task = (HobbyTask)other;

        return hobbyCategory.equalsIgnoreCase(task.hobbyCategory)
            && activityDescription.equalsIgnoreCase(task.activityDescription);
    }


    @Override
    public int hashCode()
    {
        return (hobbyCategory.toLowerCase() + "|"
            + activityDescription.toLowerCase()).hashCode();
    }


    @Override
    public String toString()
    {
        String status = isCompleted ? "Done" : "Not done";

        return hobbyCategory + " - " + activityDescription + " | Target hours: "
            + targetHours + " | Goal: " + targetGoal + " | Status: " + status;
    }
}
