package dutydowntime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the generic LinkedChain data structure.
 */
public class LinkedChainTest
{
    private LinkedChain<String> chain;

    @BeforeEach
    public void setUp()
    {
        chain = new LinkedChain<String>();
    }

    @Test
    public void testConstructor()
    {
        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
    }

    @Test
    public void testAddNormalAndDuplicates()
    {
        assertTrue(chain.add("A"));
        assertEquals(1, chain.size());
        assertFalse(chain.isEmpty());
        assertEquals("A", chain.get(0));

        // Add places items at front (order becomes B, A)
        assertTrue(chain.add("B"));
        assertEquals(2, chain.size());
        assertEquals("B", chain.get(0));
        assertEquals("A", chain.get(1));

        // Duplicates are permitted
        assertTrue(chain.add("A"));
        assertEquals(3, chain.size());
        assertEquals(2, chain.count("A"));
    }

    @Test
    public void testAddNullThrowsException()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            chain.add(null);
        });
        assertEquals(0, chain.size());
    }

    @Test
    public void testAddToEnd()
    {
        assertTrue(chain.addToEnd("First"));
        assertTrue(chain.addToEnd("Second"));
        assertTrue(chain.addToEnd("Third"));

        assertEquals(3, chain.size());
        assertEquals("First", chain.get(0));
        assertEquals("Second", chain.get(1));
        assertEquals("Third", chain.get(2));
    }

    @Test
    public void testAddToEndNullThrowsException()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            chain.addToEnd(null);
        });
        assertEquals(0, chain.size());
    }

    @Test
    public void testRemoveHeadParameterless()
    {
        chain.addToEnd("A");
        chain.addToEnd("B");

        assertEquals("A", chain.remove());
        assertEquals(1, chain.size());
        assertEquals("B", chain.get(0));

        assertEquals("B", chain.remove());
        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
    }

    @Test
    public void testRemoveParameterlessFromEmptyChain()
    {
        assertNull(chain.remove());
        assertEquals(0, chain.size());
    }

    @Test
    public void testRemoveSpecificHeadNode()
    {
        chain.addToEnd("Head");
        chain.addToEnd("Middle");
        chain.addToEnd("Tail");

        assertTrue(chain.remove("Head"));
        assertEquals(2, chain.size());
        assertEquals("Middle", chain.get(0));
        assertEquals("Tail", chain.get(1));
    }

    @Test
    public void testRemoveSpecificMiddleNode()
    {
        chain.addToEnd("Head");
        chain.addToEnd("Middle");
        chain.addToEnd("Tail");

        assertTrue(chain.remove("Middle"));
        assertEquals(2, chain.size());
        assertEquals("Head", chain.get(0));
        assertEquals("Tail", chain.get(1));
    }

    @Test
    public void testRemoveSpecificTailNode()
    {
        chain.addToEnd("Head");
        chain.addToEnd("Middle");
        chain.addToEnd("Tail");

        assertTrue(chain.remove("Tail"));
        assertEquals(2, chain.size());
        assertEquals("Head", chain.get(0));
        assertEquals("Middle", chain.get(1));
    }

    @Test
    public void testRemoveFromEmptyChain()
    {
        assertFalse(chain.remove("Nonexistent"));
        assertEquals(0, chain.size());
    }

    @Test
    public void testRemoveNonExistentItem()
    {
        chain.addToEnd("A");
        chain.addToEnd("B");

        assertFalse(chain.remove("C"));
        assertEquals(2, chain.size());
    }

    @Test
    public void testRemoveNull()
    {
        chain.addToEnd("A");
        assertFalse(chain.remove(null));
        assertEquals(1, chain.size());
    }

    @Test
    public void testRemoveDuplicateOnlyRemovesFirstOccurrence()
    {
        chain.addToEnd("Dup");
        chain.addToEnd("Unique");
        chain.addToEnd("Dup");

        assertTrue(chain.remove("Dup"));
        assertEquals(2, chain.size());
        assertEquals("Unique", chain.get(0));
        assertEquals("Dup", chain.get(1));
    }

    @Test
    public void testGetOutOfBounds()
    {
        chain.addToEnd("OnlyItem");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.get(1);
        });
    }

    @Test
    public void testGetOnEmptyChain()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.get(0);
        });
    }

    @Test
    public void testContainsAndCount()
    {
        assertFalse(chain.contains("A"));
        assertEquals(0, chain.count("A"));
        assertEquals(0, chain.count(null));

        chain.addToEnd("A");
        chain.addToEnd("A");
        chain.addToEnd("B");

        assertTrue(chain.contains("A"));
        assertTrue(chain.contains("B"));
        assertFalse(chain.contains("C"));
        assertFalse(chain.contains(null));

        assertEquals(2, chain.count("A"));
        assertEquals(1, chain.count("B"));
        assertEquals(0, chain.count("C"));
    }

    @Test
    public void testClear()
    {
        chain.addToEnd("A");
        chain.addToEnd("B");
        chain.clear();

        assertEquals(0, chain.size());
        assertTrue(chain.isEmpty());
        assertNull(chain.remove());

        // Clearing an empty chain should not crash
        chain.clear();
        assertEquals(0, chain.size());
    }

    @Test
    public void testToArrayNormal()
    {
        chain.addToEnd("A");
        chain.addToEnd("B");

        String[] target = new String[2];
        String[] result = chain.toArray(target);

        assertSame(target, result);
        assertEquals("A", result[0]);
        assertEquals("B", result[1]);
    }

    @Test
    public void testToArrayBadInput()
    {
        chain.addToEnd("A");

        assertThrows(IllegalArgumentException.class, () -> {
            chain.toArray(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            chain.toArray(new String[0]);
        });
    }

    @Test
    public void testToString()
    {
        assertEquals("[]", chain.toString());

        chain.addToEnd("A");
        assertEquals("[A]", chain.toString());

        chain.addToEnd("B");
        assertEquals("[A, B]", chain.toString());
    }
}
