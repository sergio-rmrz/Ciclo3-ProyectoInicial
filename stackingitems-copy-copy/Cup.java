import java.util.ArrayList;

/**
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 3.0
 */
public class Cup implements StackableItem {
    /** Número lógico que identifica el tamaño de la taza */
    private int number;
    /** Altura de la taza en píxeles */
    private int height;
    /** Ancho de la taza en píxeles */
    private int width;
    /** Posición horizontal */
    private int xPosition;
    /** Posición vertical que representa la base de la taza */
    private int yPosition;
    /** Color de la taza */
    private String color;
    /** Indica si la taza está visible en pantalla */
    private boolean isVisible;
    /** Indica si la taza tiene tapa */
    private boolean hasLid;
    /** Indica si está adentro */ 
    private boolean inside;
    /** Nivel de anidamiento: 0=externo, 1=primer nivel, 2=segundo nivel */
    private int level;
    private boolean lidActive;
    private Lid lid;
    private static final int PIXEL_POR_CM = 5;
    private Rectangle shape1;
    private Rectangle shape2;
    
    public Cup(int number, String color) {
        this.number = number;
        this.height = calcularHeight(number);
        this.color = color;
        this.xPosition = 130; 
        this.yPosition = 200;
        this.width = calcularHeight(number);
        this.isVisible = false;
        this.inside = false;
        this.lidActive = false;
        this.level = 0;
        this.lid = new Lid(number, color);
    }
    
    public int calcularHeight(int number) {
        return ((2 * number) - 1) * PIXEL_POR_CM;
    }
    
    public int getLevel() {
        return level; 
    }
    
    public void setLevel(int level) { 
        this.level = level;
    }
    
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    public void makeInvisible() {
        erase();
        isVisible = false;
        lid.makeInvisible();
    }
    
    public void activateLid() { 
        lidActive = true; 
    }

    public void showLid() {
        lid.setPosition(xPosition, yPosition - height);
        lid.makeVisible();
    }
    
    public void eraseLid() {
        lid.erase();
    }
    
    public void hideLid() {
        lidActive = false;
        lid.makeInvisible();
    }
    
    public boolean hasLidOn() { 
        return lidActive;
    }
    
    public void moverVertical(int distancia) {
        erase();
        yPosition += distancia;
        if (lid.isVisible()){
            lid.setPosition(xPosition, yPosition - height + PIXEL_POR_CM);
        }
        draw();
    }

    public Lid getLid() {
        return lid; 
    }
    public void setPosition(int x, int y) {
        xPosition = x;
        yPosition = y;
        if (lid.isVisible()){
            lid.setPosition(x, y - height);
        }
    }
    public void moveTo(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }
    public int getNumber() { 
        return number; 
    }

    public int getHeight() { 
        return height / PIXEL_POR_CM; 
    }
    public String getColor() {
        return color; 
    }
    public boolean isInside() { 
        return inside; 
    }

    public void setInside(boolean value) {
        inside = value; 
    }
    
    public void gotALid() { 
        hasLid = true; 
    }
    
    public void draw() {
        if (isVisible) {
            int grosor = 7;
            int esquinaX = xPosition - width / 2;
            int esquinaY = yPosition - height;
            shape1 = new Rectangle();
            shape1.changeSize(height, width);
            shape1.changeColor(color);
            shape1.moveHorizontal(esquinaX - 70);
            shape1.moveVertical(esquinaY);
            shape1.makeVisible();
            shape2 = new Rectangle();
            shape2.changeSize(height - grosor, width - 2 * grosor);
            shape2.changeColor("white");
            shape2.moveHorizontal((esquinaX + grosor) - 70);
            shape2.moveVertical(esquinaY);
            shape2.makeVisible();
        }
    }
    
    public void erase() {
        if (shape1 != null) shape1.makeInvisible();
        if (shape2 != null) shape2.makeInvisible();
    }
    
    public int getYPosition() { 
        return yPosition;
    }
    
    public boolean isCup() {
        return true;
    }
    
}