import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad para TowerContest
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 3.0
 */
public class TowerContestTest {

    // PRUEBAS SOLVE - LO QUE DEBERÍA HACER

    @Test
    public void accordingRSShouldSolveMinHeight() {
        String resultado = TowerContest.solve(4, 7);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveMaxHeight() {
        String resultado = TowerContest.solve(4, 16);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveGeneralCase() {
        String resultado = TowerContest.solve(4, 9);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveStackedOutside() {
        String resultado = TowerContest.solve(4, 8);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    @Test
    public void accordingRSShouldSolveOneCup() {
        String resultado = TowerContest.solve(1, 1);
        assertEquals("1", resultado);
    }

    @Test
    public void accordingRSShouldReturnCorrectOrderForMinHeight() {
        String resultado = TowerContest.solve(4, 7);
        assertEquals("7 5 3 1", resultado);
    }

    @Test
    public void accordingRSShouldReturnCorrectOrderForMaxHeight() {
        String resultado = TowerContest.solve(4, 16);
        assertEquals("1 3 5 7", resultado);
    }

    @Test
    public void accordingRSShouldReturnCorrectOrderForGeneralCase() {
        String resultado = TowerContest.solve(4, 9);
        assertEquals("7 3 5 1", resultado);
    }

    @Test
    public void accordingRSShouldReturnCorrectOrderForStackedOutside() {
        String resultado = TowerContest.solve(4, 8);
        assertEquals("1 7 5 3", resultado);
    }

    @Test
    public void accordingRSShouldSolveWithLargeN() {
        String resultado = TowerContest.solve(10, 100);
        assertNotNull(resultado);
        assertFalse(resultado.equals("impossible"));
    }

    // PRUEBAS SOLVE - LO QUE NO DEBERÍA HACER

    @Test
    public void accordingRSShouldReturnImpossibleWhenHeightTooLarge() {
        assertEquals("impossible", TowerContest.solve(4, 100));
    }

    @Test
    public void accordingRSShouldReturnImpossibleWhenHeightTooSmall() {
        assertEquals("impossible", TowerContest.solve(4, 6));
    }

    @Test
    public void accordingRSShouldReturnImpossibleWhenHeightZero() {
        assertEquals("impossible", TowerContest.solve(4, 0));
    }

    @Test
    public void accordingRSShouldReturnImpossibleWhenNegativeHeight() {
        assertEquals("impossible", TowerContest.solve(4, -1));
    }

    @Test
    public void accordingRSShouldReturnImpossibleForUnreachableHeight() {
        assertEquals("impossible", TowerContest.solve(4, 17));
    }

    // PRUEBAS SIMULATE - LO QUE DEBERÍA HACER

    @Test
    public void accordingRSShouldSimulateWithoutError() {
        TowerContest.simulate(4, 9);
        assertTrue(true);
    }

    @Test
    public void accordingRSShouldSimulateMinHeight() {
        TowerContest.simulate(4, 7);
        assertTrue(true);
    }

    @Test
    public void accordingRSShouldSimulateMaxHeight() {
        TowerContest.simulate(4, 16);
        assertTrue(true);
    }

    // PRUEBAS SIMULATE - LO QUE NO DEBERÍA HACER

    @Test
    public void accordingRSShouldNotSimulateImpossibleCase() {
        TowerContest.simulate(4, 100);
        assertTrue(true);
    }
}