package dutydowntime;

public class AcademicTask
{
    private String courseCode;
    private String assignmentName;
    private String dueDate;
    private double estimatedHours;
    private boolean isCompleted;

    /**
     * Creates a new academic task.
     *
     * @param courseCode
     *            the course code
     * @param assignmentName
     *            the assignment name
     * @param dueDate
     *            the assignment due date
     * @param estimatedHours
     *            the estimated time to finish
     */
    public AcademicTask(
        String courseCode,
        String assignmentName,
        String dueDate,
        double estimatedHours)
    {
        if (courseCode == null || courseCode.trim().isEmpty()
            || assignmentName == null || assignmentName.trim().isEmpty()
            || dueDate == null || dueDate.trim().isEmpty()
            || estimatedHours <= 0 || Double.isNaN(estimatedHours)
            || Double.isInfinite(estimatedHours))
        {
            throw new IllegalArgumentException(
                "Invalid academic task information.");
        }

        this.courseCode = courseCode.trim();
        this.assignmentName = assignmentName.trim();
        this.dueDate = dueDate.trim();
        this.estimatedHours = estimatedHours;
        this.isCompleted = false;
    }


    public String getCourseCode()
    {
        return courseCode;
    }


    public String getAssignmentName()
    {
        return assignmentName;
    }


    public String getDueDate()
    {
        return dueDate;
    }


    public double getEstimatedHours()
    {
        return estimatedHours;
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

        if (!(other instanceof AcademicTask))
        {
            return false;
        }

        AcademicTask task = (AcademicTask)other;

        return courseCode.equalsIgnoreCase(task.courseCode)
            && assignmentName.equalsIgnoreCase(task.assignmentName);
    }


    @Override
    public int hashCode()
    {
        return (courseCode.toLowerCase() + "|" + assignmentName.toLowerCase())
            .hashCode();
    }


    @Override
    public String toString()
    {
        String status = isCompleted ? "Done" : "Not done";

        return courseCode + " - " + assignmentName + " | Due: " + dueDate
            + " | Estimated hours: " + estimatedHours + " | Status: " + status;
    }
}
