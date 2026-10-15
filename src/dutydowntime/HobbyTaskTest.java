import static org.junit.Assert.*;
import org.junit.Test;

public class HobbyTaskTest
{
    @Test
    public void constructorStoresValuesAndStartsIncomplete()
    {
        HobbyTask task = new HobbyTask(
            "Fitness", "Gym Workout", 1.0, "Complete 4 sets");

        assertEquals("Fitness", task.getHobbyCategory());
        assertEquals("Gym Workout", task.getActivityDescription());
        assertEquals(1.0, task.getTargetHours(), 0.001);
        assertEquals("Complete 4 sets", task.getTargetGoal());
        assertFalse(task.isCompleted());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsBlankCategory()
    {
        new HobbyTask(" ", "Gym Workout", 1.0, "Complete 4 sets");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullDescription()
    {
        new HobbyTask("Fitness", null, 1.0, "Complete 4 sets");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsBlankGoal()
    {
        new HobbyTask("Fitness", "Gym Workout", 1.0, " ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNonPositiveHours()
    {
        new HobbyTask("Fitness", "Gym Workout", 0, "Complete 4 sets");
    }

    @Test
    public void markCompletedOnlyChangesOnce()
    {
        HobbyTask task = new HobbyTask(
            "Fitness", "Gym Workout", 1.0, "Complete 4 sets");
        assertTrue(task.markCompleted());
        assertTrue(task.isCompleted());
        assertFalse(task.markCompleted());
    }

    @Test
    public void equalsUsesCategoryAndDescriptionIgnoringCase()
    {
        HobbyTask first = new HobbyTask(
            "Fitness", "Gym Workout", 1.0, "Four sets");
        HobbyTask second = new HobbyTask(
            "fitness", "gym workout", 2.0, "Five sets");
        HobbyTask different = new HobbyTask(
            "Fitness", "Run", 1.0, "One mile");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertNotEquals(first, different);
        assertNotEquals(first, null);
        assertNotEquals(first, new AcademicTask(
            "CS 2114", "Homework", "10/20", 1.0));
    }

    @Test
    public void toStringIncludesDetailsAndStatus()
    {
        HobbyTask task = new HobbyTask(
            "Fitness", "Gym Workout", 1.0, "Complete 4 sets");
        assertTrue(task.toString().contains("Fitness"));
        assertTrue(task.toString().contains("Gym Workout"));
        assertTrue(task.toString().contains("1.0"));
        assertTrue(task.toString().contains("Complete 4 sets"));
        assertTrue(task.toString().contains("Not done"));
        task.markCompleted();
        assertTrue(task.toString().contains("Done"));
    }
}
