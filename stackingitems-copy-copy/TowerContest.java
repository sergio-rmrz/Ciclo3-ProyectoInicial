import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase que resuelve y simula el problema de la maratón Stacking Cups
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 3.0
 */
public class TowerContest {

    /**
     * Resuelve el problema de la maratón: encuentra el orden de inserción
     * de n tazas para que la torre tenga exactamente altura h.
     * @param n número de tazas
     * @param h altura deseada
     * @return orden de inserción como un String o "impossible" si no es posible
     */
    public static String solve(int n, long h) {
        
        long[] alturas = new long[n];
        for (int i = 0; i < n; i++) {
            alturas[i] = 2 * (i + 1) - 1;
        }
        long sumaTotal = (long) n * n;
        long minAltura = alturas[n - 1];
        if (h < minAltura || h > sumaTotal) 
            return "impossible";
        ArrayList<Long> orden = construirOrden(n, h, alturas, sumaTotal, minAltura);
        if (orden == null) 
            return "impossible";
        String resultado = "";
        for (int i = 0; i < orden.size(); i++) {
            resultado += orden.get(i);
            if (i < orden.size() - 1) resultado += " ";
        }
        return resultado;
    }
    
    private static ArrayList<Long> construirOrden(int n, long h, long[] alturas, long sumaTotal, long minAltura) {

        ArrayList<Long> orden = new ArrayList<Long>();
        
        // Caso: todas afuera
        if (h == sumaTotal) {
            for (int i = 0; i < n; i++) orden.add(alturas[i]);
            return orden;
        }
        
        // Caso en el que van todas adentro
        if (h == minAltura) {
            orden.add(alturas[n - 1]);
            for (int i = n - 2; i >= 0; i--) orden.add(alturas[i]);
            return orden;
        }
        
        // Caso general (podriamos decir que esto es para que se cumplan casos como el (4, 9) 
        boolean[] adentro = new boolean[n];
        long resto = h - 1;
        for (int i = n - 2; i >= 0; i--) {
            if (alturas[i] <= resto) {
                adentro[i] = true;
                resto -= alturas[i];
            }
        }
        if (resto == 0) {
            orden.add(alturas[n - 1]);
            for (int i = 0; i < n - 1; i++) {
                if (adentro[i]) orden.add(alturas[i]);
            }
            for (int i = 0; i < n - 1; i++) {
                if (!adentro[i]) orden.add(alturas[i]);
            }
            return orden;
        }
        
        // Caso: tazas afuera apiladas
        boolean[] afuera = new boolean[n];
        long restoAfuera = h;
        for (int i = n - 1; i >= 0; i--) {
            if (alturas[i] <= restoAfuera) {
                afuera[i] = true;
                restoAfuera -= alturas[i];
            }
        }
        if (restoAfuera == 0) {
            for (int i = 0; i < n; i++) {
                if (afuera[i]) orden.add(alturas[i]);
            }
            for (int i = n - 1; i >= 0; i--) {
                if (!afuera[i]) orden.add(alturas[i]);
            }
            return orden;
        }
        return null;
    }

    /**
     * Simula visualmente la solución del problema presentando 
     * Usa Tower para visualizar el orden de inserción que solve encontró
     * @param n número de tazas
     * @param h altura deseada
     */
    public static void simulate(int n, long h) {
        String resultado = solve(n, h);
        
        if (resultado.equals("impossible")) {
            JOptionPane.showMessageDialog(null, "No es posible construir una torre de altura " + h + " con " + n + " tazas");
            return;
        }
        long[] alturas = new long[n];
        for (int i = 0; i < n; i++) {
            alturas[i] = 2 * (i + 1) - 1;
        }
        ArrayList<Long> orden = construirOrden(n, h, alturas, (long) n * n, alturas[n - 1]); //Aquí reutilizamos el construirOrden de arriba del solve :D
        Tower tower = new Tower((int)(n * n));
        tower.makeVisible();
        
        for (int i = 0; i < orden.size(); i++) {
            int numeroCopa = (int)(orden.get(i) + 1) / 2;
            tower.pushCup(numeroCopa);
        }
    }
}