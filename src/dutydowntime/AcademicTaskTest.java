import static org.junit.Assert.*;
import org.junit.Test;

public class AcademicTaskTest
{
    @Test
    public void constructorStoresValuesAndStartsIncomplete()
    {
        AcademicTask task = new AcademicTask(
            "CS 2114", "Homework 2", "10/20", 3.5);

        assertEquals("CS 2114", task.getCourseCode());
        assertEquals("Homework 2", task.getAssignmentName());
        assertEquals("10/20", task.getDueDate());
        assertEquals(3.5, task.getEstimatedHours(), 0.001);
        assertFalse(task.isCompleted());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsBlankCourseCode()
    {
        new AcademicTask(" ", "Homework", "10/20", 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullAssignmentName()
    {
        new AcademicTask("CS 2114", null, "10/20", 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsBlankDueDate()
    {
        new AcademicTask("CS 2114", "Homework", " ", 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNonPositiveHours()
    {
        new AcademicTask("CS 2114", "Homework", "10/20", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsInfiniteHours()
    {
        new AcademicTask("CS 2114", "Homework", "10/20",
            Double.POSITIVE_INFINITY);
    }

    @Test
    public void markCompletedOnlyChangesOnce()
    {
        AcademicTask task = new AcademicTask(
            "CS 2114", "Homework", "10/20", 1.0);
        assertTrue(task.markCompleted());
        assertTrue(task.isCompleted());
        assertFalse(task.markCompleted());
    }

    @Test
    public void equalsUsesCourseAndAssignmentIgnoringCase()
    {
        AcademicTask first = new AcademicTask(
            "CS 2114", "Homework 2", "10/20", 1.0);
        AcademicTask second = new AcademicTask(
            "cs 2114", "homework 2", "10/21", 4.0);
        AcademicTask different = new AcademicTask(
            "CS 2114", "Homework 3", "10/20", 1.0);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, different);
        assertNotEquals(first, null);
        assertNotEquals(first, new HobbyTask(
            "Gaming", "Play", 1.0, "One match"));
    }

    @Test
    public void toStringIncludesDetailsAndStatus()
    {
        AcademicTask task = new AcademicTask(
            "CS 2114", "Homework 2", "10/20", 3.5);
        assertTrue(task.toString().contains("CS 2114"));
        assertTrue(task.toString().contains("Homework 2"));
        assertTrue(task.toString().contains("10/20"));
        assertTrue(task.toString().contains("3.5"));
        assertTrue(task.toString().contains("Not done"));
        task.markCompleted();
        assertTrue(task.toString().contains("Done"));
    }
}
