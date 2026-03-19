import java.util.Stack;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;

 
/**
 * Representa una torre de copas apiladas con sus tapas.
 * Las tapas pueden existir de forma independiente (tapas sueltas) o asociadas a una copa específica
 * La torre mantiene el orden real de inserción de todos los elementos calcula de nuevo todas las posiciones visuales mediante el uso de la función redraw()
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 2.6
 */

public class Tower {   
    /** Pila de copas */
    private Stack<Cup> cups;
    /** Lista de tapas que no tienen copa (tapas sueltas)*/
    private ArrayList<Lid> looseLids;
    /** Orden de procesamiento en la inserción y posicioón en el redraw()*/
    private ArrayList<StackableItem> insertionOrder;
    /** Indica si la torre es o no visible*/
    private boolean isVisible;
    /** Indica si la última acción fue valida*/
    private boolean isOK;
    /** Coordenada X en el canvas*/
    private int baseX = 130;
    /** Coordenada Y en el canvas*/
    private int baseY = 200;
    /** Altúra maxima de la torre*/
    private int maxHeight;
    /** Colores usados para asignarselos a cada número en la construcción*/
    private static final String[] COLORES = {
        "red", "blue", "green", "yellow", "magenta",
        "orange", "cyan", "pink", "gray", "darkGray" } ;
    
    // -------------------------------------
    // ------ //arreglar constructoress 
    // -------------------------------------
    //
    // CONSTRUCTORES :D
    //
    
    /** 
     *Constructor de ciclo 1, crea una torre vacia con una altura maxima definida
     */
    public Tower(int maxHeight)
    {
        cups = new Stack<Cup>();
        looseLids = new ArrayList<Lid>();
        insertionOrder = new ArrayList<StackableItem>();
        this.maxHeight = maxHeight;
        isVisible = false;
        isOK = true;
    }
    
    /**
     *Constructor de ciclo 2, crea una torre de copas de tamaños impares consecutivos
     */
    public Tower(int numCups, int ignorar) 
    {
        cups = new Stack<Cup>();
        looseLids = new ArrayList<Lid>();
        insertionOrder = new ArrayList<StackableItem>();
        isVisible = false;
        isOK = true;
        int alturaTotal = 0;
        for (int k = 1; k <= numCups; k++) alturaTotal += (2 * k - 1);
        this.maxHeight = alturaTotal;
        for (int k = 1; k <= numCups; k++) {
            Cup c = new Cup((2 * k) - 1, COLORES[(k - 1) % COLORES.length]);
            cups.push(c);
            insertionOrder.add(c);
        }
    }

    // COPAS

    /**
     * Agrega una copa a la torre con un color automatico
     * Valida que no exista una copa de el mismo tamaño
     * Vaida que la copa no sobrepase el limite de altura de la torre
     */
    public void pushCup(int number)
    {
        String color = COLORES[(number - 1) % COLORES.length];
        Cup nueva = new Cup(number, color);
        if (repSize(number)) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Error, ya existe una copa con este tamaño.");
            return;
        }
        if (getHeight() + nueva.getHeight() > maxHeight) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Esta copa sobrepasa el límite máximo de la torre");
            return;
        }
        cups.push(nueva);
        insertionOrder.add(nueva);
        redraw();
        isOK = true;
    }
    
    /**
     * Elimina la útima copa insertada en la torre
     */
    public Cup popCup()
    {
        if (!cups.isEmpty()) {
            Cup removida = cups.pop();
            insertionOrder.remove(removida);
            removida.makeInvisible();
            redraw();
            isOK = true;
            return removida;
        }
        isOK = false;
        return null;
    }
    
    /**
     * Elimina la copa con el número especificado en la torre
     * Valida que la copa que se intenta remover exista en la torre
     */
    public void removeCup(int number)
    {
        if (cups.isEmpty()) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "La torre está vacía");
            return;
        }
        for (int i = 0; i < cups.size(); i++) {
            if (cups.get(i).getNumber() == number) {
                Cup c = cups.get(i);
                c.makeInvisible();
                cups.remove(i);
                insertionOrder.remove(c);
                redraw();
                isOK = true;
                return;
            }
        }
        isOK = false;
        JOptionPane.showMessageDialog(null, "No hay una copa con este tamaño en la torre");
    }

    // TAPAS
    
    /**
     * Agrega una tapa suelta en la torre
     */
    public void pushLid(int number)
    {
        if (findLooseLid(number) != null) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Ya existe una tapa con este número");
            return;
        }
        if (getHeight() + 1 > maxHeight) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "La tapa sobrepasa el límite máximo");
            return;
        }
        String color = COLORES[(number - 1) % COLORES.length];
        Lid nueva = new Lid(number, color);
        looseLids.add(nueva);
        insertionOrder.add(nueva);
        redraw();
        isOK = true;
    }
    
    /**
     * Elimina la última tapa insertada en la torre
     */
    public Cup popLid() {
        for (int i = insertionOrder.size() - 1; i >= 0; i--) {
            if (!insertionOrder.get(i).isCup()) {
                StackableItem l = insertionOrder.get(i);
                l.erase();
                insertionOrder.remove(i);
                looseLids.remove(l);
                redraw();
                isOK = true;
                return null;
            }
        }
        for (int i = cups.size() - 1; i >= 0; i--) {
            if (cups.get(i).hasLidOn()) {
                cups.get(i).hideLid();
                isOK = true;
                return cups.get(i);
            }
        }
        isOK = false;
        JOptionPane.showMessageDialog(null, "No hay tapas en la torre");
        return null;
    }
    
    /**
     * Elimina una tapa en especifico según su número 
     */
    public void removeLid(int number) {
        for (Cup c : cups) {
            if (c.getNumber() == number && c.hasLidOn()) {
                c.hideLid();
                redraw();
                isOK = true;
                return;
            }
        }
        Lid suelta = findLooseLid(number);
        if (suelta != null) {
            suelta.erase();
            looseLids.remove(suelta);
            insertionOrder.remove(suelta);
            redraw();
            isOK = true;
            return;
        }
        isOK = false;
        JOptionPane.showMessageDialog(null, "No existe una tapa con este número ");
    }

    // REORGANIZACIÓN DE LA TORRE :D

    /**
     * Ordena todas las copas existentes en la torre de mayor a menor 
     */
    public void orderTower()
    {
        ArrayList<Cup> lista = new ArrayList<Cup>(cups);
        Collections.sort(lista, (c1, c2) -> c2.getNumber() - c1.getNumber());
        cups.clear();
        // Reconstruir insertionOrder: cups ordenadas + tapas sueltas al final
        ArrayList<Lid> sueltas = new ArrayList<Lid>(looseLids);
        insertionOrder.clear();
        int altura = 0;
        for (Cup c : lista) {
            int h = c.getHeight() + (c.hasLidOn() ? 1 : 0);
            if (altura + h <= maxHeight) {
                c.setInside(false);
                cups.push(c);
                insertionOrder.add(c);
                altura += h;
            }
        }
        for (Lid l : sueltas) insertionOrder.add(l);
        redraw();
        isOK = true;
    }

    /**
     * Invierte el orden que maneje la torre en ese preciso momento
     */
    public void reverseTower()
    {
        ArrayList<Cup> lista = new ArrayList<Cup>(cups);
        Collections.reverse(lista);
        cups.clear();
        ArrayList<Lid> sueltas = new ArrayList<Lid>(looseLids);
        insertionOrder.clear();
        int altura = 0;
        for (Cup c : lista) {
            int h = c.getHeight() + (c.hasLidOn() ? 1 : 0);
            if (altura + h <= maxHeight) {
                c.setInside(false);
                cups.push(c);
                insertionOrder.add(c);
                altura += h;
            }
        }
        for (Lid l : sueltas) insertionOrder.add(l);
        redraw();
        isOK = true;
    }

    /**
     * Intercambia la posición de 2 elementos en la torre
     * Cada uno de los "elementos" se identifica como: {"cup"/"lid", "número"}
     * Valida que ambos elementos que se buscan intercambiar existan
     * Valida que no se realice el intercambio con el mismo elemento, en caso de que esto sea así, da error
     * @param o1 Identificador del primer elemento
     * @param o2 Identificador del segundo elemento
     */
    public void swap(String[] o1, String[] o2)
    {
        // Responsabilidad de la taza
        int num1 = Integer.parseInt(o1[1]);
        int num2 = Integer.parseInt(o2[1]);
        ArrayList<Cup> lista = new ArrayList<Cup>(cups);
        int idx1 = -1, idx2 = -1;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNumber() == num1) idx1 = i;
            if (lista.get(i).getNumber() == num2) idx2 = i;
        }
        if (idx1 == -1 || idx2 == -1) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Uno o ambos objetos no existen en la torre");
            return;
        }
        if (idx1 == idx2) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "No se puede intercambiar un objeto consigo mismo");
            return;
        }
        // Re-ajuste en el InsertionOrder
        int io1 = insertionOrder.indexOf(lista.get(idx1));
        int io2 = insertionOrder.indexOf(lista.get(idx2));
        Cup temp = lista.get(idx1);
        lista.set(idx1, lista.get(idx2));
        lista.set(idx2, temp);
        insertionOrder.set(io1, lista.get(idx1));
        insertionOrder.set(io2, lista.get(idx2));
        cups.clear();
        for (Cup c : lista) cups.push(c);
        redraw();
        isOK = true;
    }

    /**
     * Cubre las copas que tienen su tapa correspondida dentro de la torre
     */
    public void cover()
    {
        for (Cup c : cups) {
            if (!c.hasLidOn()) {
                Lid suelta = findLooseLid(c.getNumber());
                if (suelta != null) {
                    looseLids.remove(suelta);
                    insertionOrder.remove(suelta);
                    suelta.erase();
                    c.activateLid();
                    if (isVisible) c.showLid();
                }
            }
        }
        redraw();
        isOK = true;
    }

    // OTRAS METODOS QUE SE PIDEN EN EL PROYECTO

    /**
     * Calcula la altura de la torre
     * @return altura total de la torre
     */
        public int getHeight() {
        int topeExterno = 0;
        int topeInterno = 0;
        StackableItem ultimaExterna = null;
        StackableItem ultimaInterna = null;
    
        for (StackableItem item : insertionOrder) {
            int number = item.getNumber();
            int size = item.getHeight();
            int extNum = (ultimaExterna != null) ? ultimaExterna.getNumber() : 0;
    
            if (ultimaExterna == null || number > extNum) {
                // Va afuera
                topeExterno += size;
                if (item.hasLidOn()) topeExterno += 1;
                if (item.isCup()) { ultimaExterna = item; ultimaInterna = null; }
            } else {
                // Va adentro
                int intNum = (ultimaInterna != null) ? ultimaInterna.getNumber() : 0;
                if (ultimaInterna == null || number > intNum) {
                    // Primer nivel adentro
                    topeInterno += size;
                    if (item.isCup()) ultimaInterna = item;
                }
                // Si va dentro de ultimaInterna, no suma
            }
        }
    
        for (Lid l : looseLids) {
            if (!l.isInside()) topeExterno += 1;
        }
    
        return Math.max(topeExterno, 1 + topeInterno);
    }

    /**
     * Retorna los números de todas las copas que se encuentren "tapadas" esto quiere decir que su respectiva tapa, se encuentra justo sobre ellas
     * @return Arreglo con los números de las copas tapadas
     */
    public int[] lidedCups()
    {
        ArrayList<Integer> tapadas = new ArrayList<Integer>();
        for (Cup c : cups) {
            if (c.hasLidOn()) tapadas.add(c.getNumber());
        }
        Collections.sort(tapadas);
        return tapadas.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Retorna todos los elementos de la torre organizados mediante la inserción que se les dió
     * @return Arreglo con tipo y número de cada elemento
     */
    public String[][] stackingItems() {
        ArrayList<String[]> items = new ArrayList<String[]>();
        for (StackableItem item : insertionOrder) {
            if (item.isCup()) {
                items.add(new String[]{"cup", String.valueOf(item.getNumber())});
                if (item.hasLidOn()) items.add(new String[]{"lid", String.valueOf(item.getNumber())});
            } else {
                items.add(new String[]{"lid", String.valueOf(item.getNumber())});
            }
        }
        return items.toArray(new String[0][]);
    }

    /**
     * Intercambia dos copas con el fin de reducir la altura de la torre
     */
    public String[][] swapToReduce(){
        int alturaActual = getHeight();
        for (int i = 0; i < cups.size(); i++) {
            for (int j = i + 1; j < cups.size(); j++) {
                Cup ci = cups.get(i);
                Cup cj = cups.get(j);
                int io1 = insertionOrder.indexOf(ci);
                int io2 = insertionOrder.indexOf(cj);
                cups.set(i, cj);
                cups.set(j, ci);
                insertionOrder.set(io1, cj);
                insertionOrder.set(io2, ci);
                redraw();
                if (getHeight() < alturaActual) {
                    cups.set(i, ci);
                    cups.set(j, cj);
                    insertionOrder.set(io1, ci);
                    insertionOrder.set(io2, cj);
                    redraw();
                    isOK = true;
                    return new String[][]{
                        {cj.hasLidOn() ? "lid" : "cup", String.valueOf(cj.getNumber())},
                        {ci.hasLidOn() ? "lid" : "cup", String.valueOf(ci.getNumber())}
                    };
                }
                cups.set(i, ci);
                cups.set(j, cj);
                insertionOrder.set(io1, ci);
                insertionOrder.set(io2, cj);
                redraw();
            }
        }
        isOK = true;
        return null;
    }

    // UTILIDADES :D

    /**
     * Hace visible la torre
     */
    public void makeVisible()
    {
        isVisible = true;
        redraw();
        drawRule();
    }

    /**
     * Hace invisible la torre
     */
    public void makeInvisible()
    {
        for (Cup c : cups) c.makeInvisible();
        for (Lid l : looseLids) l.makeInvisible();
        isVisible = false;
    }

    /**
     * Indica si la última acción realizada fue o no valida mediante un boolean
     */
    public boolean isOk() {
        return isOK; 
    }

    /**
     * Reinicia la torre, elimina todo lo que hay en la torre y la deja vacia
     */
    public void exit()
    {
        makeInvisible();
        cups.clear();
        looseLids.clear();
        insertionOrder.clear();
        isOK = true;
    }

    // PARTE DEL REDRAW (Fallan ciertos casos todavia profe, perdón :p )

    /**
     * Dibuja todos los elementos según las posiciones, tambien recalcula las posiciones dentro de la torre
     * Aplica para diferentes casos (los cuales intentaré mencionar dentro del redraw con comentarios)
     */
   private void redraw() {
        int yActual = baseY;
        int yTope = baseY;
        int yTopeInterno = baseY;
        StackableItem anteriorCup = null;
        StackableItem anteriorLid = null;
        StackableItem ultimaExterna = null;
        StackableItem ultimaInterna = null;
        boolean primerElemento = true;
    
        for (Cup c : cups) { c.erase(); c.eraseLid(); }
        for (Lid l : looseLids) l.erase();
    
        for (StackableItem item : insertionOrder) {
            int number = item.getNumber();
            int size = item.getHeight();
    
            int extNum = (ultimaExterna != null) ? ultimaExterna.getNumber() : 0;
            boolean extLid = (ultimaExterna != null) && ultimaExterna.hasLidOn();
            boolean bloqueadaPorLid = false;
    
            for (int i = insertionOrder.indexOf(item) - 1; i >= 0; i--) {
                StackableItem prev = insertionOrder.get(i);
                if (prev.isCup()) break;
                if (!prev.isCup() && prev.getNumber() >= extNum) { bloqueadaPorLid = true; break; }
            }
    
            int intNum = (ultimaInterna != null) ? ultimaInterna.getNumber() : 0;
            boolean cabeAdentro = number < extNum && !extLid && !bloqueadaPorLid && (ultimaInterna == null || number < intNum);
            boolean encimaDentro = ultimaInterna != null && number < extNum && !extLid && number >= intNum;
    
            int antNum = anteriorCup != null ? anteriorCup.getNumber() : (anteriorLid != null ? anteriorLid.getNumber() : 0);
            boolean antIn = anteriorCup != null ? anteriorCup.isInside() : (anteriorLid != null ? anteriorLid.isInside() : false);
            int antY = anteriorCup != null ? anteriorCup.getYPosition() : (anteriorLid != null ? anteriorLid.getYPosition() + 5 : baseY);
            int antSize = anteriorCup != null ? anteriorCup.getHeight() : 1;
    
            if (primerElemento) {
                primerElemento = false;
                yActual = baseY;
                item.setInside(false);
                if (item.isCup()) { ultimaExterna = item; ultimaInterna = null; }
                yTope = item.isCup() ? baseY - size * 5 : baseY - 5;
                yTopeInterno = baseY;
            } else if (number > extNum) {
                yActual = yTope;
                item.setInside(false);
                if (item.isCup()) { ultimaExterna = item; ultimaInterna = null; }
                yTope = item.isCup() ? yActual - size * 5 : yActual - 5;
                yTopeInterno = baseY;
            } else if (cabeAdentro) {
                if (antIn && number < antNum) {
                    yActual = antY - 7;
                } else if (antIn && number >= antNum) {
                    yActual = antY - antSize * 5;
                } else {
                    yActual = ultimaExterna.getYPosition() - 7;
                }
                item.setInside(true);
                if (item.isCup() && (ultimaInterna == null || number > intNum)) ultimaInterna = item;
                int topC = item.isCup() ? yActual - size * 5 : yActual - 5;
                if (topC < yTope) yTope = topC;
                if (item.isCup() && topC < yTopeInterno) yTopeInterno = topC;
            } else if (encimaDentro) {
                if (!item.isCup() && anteriorLid != null && anteriorLid.isInside() && number != intNum) {
                    yActual = anteriorLid.getYPosition();
                } else if (item.isCup() && anteriorLid != null && anteriorLid.isInside()) {
                    yActual = anteriorLid.getYPosition();
                } else {
                    yActual = ultimaInterna.getYPosition() - ultimaInterna.getHeight() * 5;
                }
                item.setInside(true);
                if (item.isCup()) ultimaInterna = item;
                int topC2 = item.isCup() ? yActual - size * 5 : yActual - 5;
                if (topC2 < yTope) yTope = topC2;
                if (item.isCup() && topC2 < yTopeInterno) yTopeInterno = topC2;
            } else {
                yActual = yTope;
                item.setInside(false);
                if (item.isCup()) { ultimaExterna = item; ultimaInterna = null; }
                yTope = item.isCup() ? yActual - size * 5 : yActual - 5;
                yTopeInterno = baseY;
            }
    
            item.setPosition(baseX, item.isCup() ? yActual : yActual - 5);
    
            if (isVisible) {
                item.makeVisible();
                if (item.hasLidOn()) item.showLid();
            }
    
            if (item.isCup()) { anteriorCup = item; anteriorLid = null; }
            else { anteriorLid = item; anteriorCup = null; }
        }
    
        if (isVisible) {
            for (Cup c : cups) { if (c.hasLidOn()) c.showLid(); }
        }
    }


    // METODOS PRIVADOS :D

    /**
     * Calcula el top de la torre, lo que es equivalente a su posición Y
     * @return Coordenada Y de la parte más alta de la torre
     */
    private int getTop(Cup c) {
        return c.getYPosition() - c.getHeight() * 5;
    }

    /**
     * Busca una tapa suelta mediante su número identificador en looselids
     * @param number Número a verificar
     * @return True en caso de que ya exista una copa con dicho número
     */
    private Lid findLooseLid(int number)
    {
        for (Lid l : looseLids) {
            if (l.getNumber() == number)
            return l;
        }
        return null;
    }

    /**
     * Verifica que no se repita el tamaño del número indicativo en la torre
     * @param number Número a verificar
     * @return True si ya existe una copa con este número   
     */
    private boolean repSize(int number)
    {
        for (Cup c : cups) {
            if (c.getNumber() == number) 
            return true; 
        }
        return false;
    }

    /**
     * Verifica que no se repita el color del tamaño de la torre    // ESTE NO LO ESTAMOS USANDO POR EL MOMENTO PERO NO LO BORRAMOS PQ PUEDE SER ÚLIL DESPUÉS
     */
    private boolean repColor(String color)
    {
        for (Cup c : cups) {
            if (c.getColor().equals(color)) 
            return true; 
        }
        return false;
    }

    /**
     * Dibuja una regla en la parte lateral izquierda del Canvas hasta la altura máxima de la torre
     */
    public void drawRule()
    {
        int escala = 5;
        for (int i = 0; i <= maxHeight; i++) {
            Rectangle r = new Rectangle();
            r.changeSize(2, (i % 5 == 0) ? 20 : 10);
            r.moveHorizontal(baseX - 200);
            r.moveVertical(baseY - (i * escala));
            r.changeColor("black");
            r.makeVisible();
        }
    }
    
}