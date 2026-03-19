import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Casos de prueba para la clase de prueba común TowerContestCTest
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 3.0
 */
public class TowerContestCTest {
    
    @Test
    public void accordingRSShouldSolveExampleFromContest() {
        assertEquals("7 3 5 1", TowerContest.solve(4, 9));
    }

    @Test
    public void accordingRSShouldReturnImpossibleForContestExample() {
        assertEquals("impossible", TowerContest.solve(4, 100));
    }

    @Test
    public void accordingRSShouldSolveMinHeightForContest() {
        String resultado = TowerContest.solve(4, 7);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveMaxHeightForContest() {
        String resultado = TowerContest.solve(4, 16);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveStackedOutsideForContest() {
        String resultado = TowerContest.solve(4, 8);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveOneCupForContest() {
        assertEquals("1", TowerContest.solve(1, 1));
    }

    @Test
    public void accordingRSShouldReturnImpossibleWhenHeightBelowMinForContest() {
        assertEquals("impossible", TowerContest.solve(4, 6));
    }

    @Test
    public void accordingRSShouldReturnImpossibleWhenHeightAboveMaxForContest() {
        assertEquals("impossible", TowerContest.solve(4, 17));
    }
}