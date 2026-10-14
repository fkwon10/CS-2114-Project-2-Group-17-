package dutydowntime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testDutyRecommendation {
    
    private DutyRecommendation recommendation;

    @BeforeEach
    public void setUp() {
        recommendation = new DutyRecommendation();
    }

    @Test
    public void testConstructorNormal() {
        assertEquals(0, recommendation.getAcademicCount());
        assertEquals(0, recommendation.getHobbyCount());
        assertEquals(0, recommendation.countActiveAcademic());
        assertEquals(0, recommendation.countActiveHobby());
    }

    @Test
    public void testEmptyRecommendationBadInput() {
        String rec = recommendation.getNextRecommendation();
        assertTrue(rec.contains("No tasks left"));
    }

    @Test
    public void testAddAcademicTaskNormal() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        assertEquals(1, recommendation.getAcademicCount());
        assertEquals(1, recommendation.countActiveAcademic());
    }

    @Test
    public void testAddAcademicTaskBadInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", -2.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            recommendation.addAcademicTask("", "Homework 2", "10/11", 3.0);
        });
        
        assertEquals(0, recommendation.getAcademicCount());
    }

    @Test
    public void testAddHobbyTaskNormal() {
        recommendation.addHobbyTask("Gaming", "Ranked match", 1.0, "Win 1 game");
        assertEquals(1, recommendation.getHobbyCount());
        assertEquals(1, recommendation.countActiveHobby());
    }

    @Test
    public void testAddHobbyTaskBadInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            recommendation.addHobbyTask("Gaming", "", 1.0, "Win 1 game");
        });
        
        assertEquals(0, recommendation.getHobbyCount());
    }

    @Test
    public void testRemoveAcademicTaskNormal() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        boolean removed = recommendation.removeAcademicTask("CS 2114", "Homework 2");
        
        assertTrue(removed);
        assertEquals(0, recommendation.getAcademicCount());
    }

    @Test
    public void testRemoveAcademicTaskBadInput() {
        boolean removedEmpty = recommendation.removeAcademicTask("MATH 2114", "Exam 1");
        assertFalse(removedEmpty);

        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        boolean removedMissing = recommendation.removeAcademicTask("CS 2114", "Nonexistent Homework");
        assertFalse(removedMissing);
        assertEquals(1, recommendation.getAcademicCount());
    }

    @Test
    public void testRemoveHobbyTaskNormal() {
        recommendation.addHobbyTask("Gaming", "Ranked match", 1.0, "Win 1 game");
        boolean removed = recommendation.removeHobbyTask("Gaming", "Ranked match");
        
        assertTrue(removed);
        assertEquals(0, recommendation.getHobbyCount());
    }

    @Test
    public void testRemoveHobbyTaskBadInput() {
        boolean removedEmpty = recommendation.removeHobbyTask("Fitness", "Gym");
        assertFalse(removedEmpty);

        recommendation.addHobbyTask("Gaming", "Ranked match", 1.0, "Win 1 game");
        boolean removedMissing = recommendation.removeHobbyTask("Gaming", "Nonexistent Hobby");
        assertFalse(removedMissing);
        assertEquals(1, recommendation.getHobbyCount());
    }

    @Test
    public void testCompleteAcademicTaskNormal() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        recommendation.addHobbyTask("Fitness", "Gym Workout", 1.0, "Complete 4 sets");

        String result = recommendation.completeAcademicTask(0);
        
        assertTrue(result.contains("Academic task completed!"));
        assertTrue(result.contains("Suggested Hobby:"));
    }

    @Test
    public void testCompleteAcademicTaskBadInput() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            recommendation.completeAcademicTask(5);
        });

        recommendation.completeAcademicTask(0);
        String secondAttempt = recommendation.completeAcademicTask(0);
        
        assertTrue(secondAttempt.contains("already completed"));
    }

    @Test
    public void testCompleteHobbyTaskNormal() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        recommendation.addHobbyTask("Fitness", "Gym Workout", 1.0, "Complete 4 sets");

        String result = recommendation.completeHobbyTask(0);
        
        assertTrue(result.contains("Hobby task completed!"));
        assertTrue(result.contains("Suggested Academic Task:"));
    }

    @Test
    public void testCompleteHobbyTaskBadInput() {
        recommendation.addHobbyTask("Fitness", "Gym Workout", 1.0, "Complete 4 sets");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            recommendation.completeHobbyTask(5);
        });

        recommendation.completeHobbyTask(0);
        String secondAttempt = recommendation.completeHobbyTask(0);
        
        assertTrue(secondAttempt.contains("already completed"));
    }

    @Test
    public void testGetNextRecommendationNormal() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        String rec1 = recommendation.getNextRecommendation();
        assertTrue(rec1.contains("Suggested Academic Task:"));

        recommendation.addHobbyTask("Gaming", "Ranked match", 1.0, "Win 1 game");
        recommendation.completeAcademicTask(0);
        
        String rec2 = recommendation.getNextRecommendation();
        assertTrue(rec2.contains("Suggested Hobby:"));
    }

    @Test
    public void testGetNextRecommendationBadInput() {
        recommendation.addAcademicTask("CS 2114", "Homework 2", "10/11", 3.0);
        recommendation.completeAcademicTask(0);
        
        String rec = recommendation.getNextRecommendation();
        assertTrue(rec.contains("No tasks left") || rec.contains("nothing left"));
    }
}
