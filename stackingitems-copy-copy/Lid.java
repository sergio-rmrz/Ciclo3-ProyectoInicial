/**
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 3.0
 */
public class Lid implements StackableItem {
    /** Número lógico asociado a la taza correspondiente */
    private int number;
    /** Altura (grosor) de la tapa en píxeles */
    private int height;
    /** Ancho de la tapa en píxeles */
    private int width;
    /** Posición horizontal */
    private int xPosition;
    /** Posición vertical de referencia */
    private int yPosition;
    /** Color de la tapa */
    private String color;
    /** Indica si la tapa está visible en pantalla */
    private boolean isVisible;
    /** Indica si está adentro */
    private boolean inside = false;
    /** Nivel de anidamiento: 0=externo, 1=primer nivel, 2=segundo nivel */
    private int level;
    private static final int PIXEL_POR_CM = 5;
    private Rectangle shape;

    public Lid(int number, String color) {
        this.number = number;
        this.color = color;
        this.width = ((2 * number) - 1) * PIXEL_POR_CM;
        this.height = PIXEL_POR_CM; 
        this.xPosition = 130; 
        this.yPosition = 0;
        this.isVisible = false;
        this.level = 0;
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
    }

    public boolean isVisible() {
        return isVisible; 
    }

    public void moveVertical(int distancia) {
        erase();
        yPosition += distancia;
        draw();
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

    public int getSize() {
        return number; 
    }

    public String getColor() {
        return color; 
    }

    public int getYPosition() {
        return yPosition; 
    }

    public void setPosition(int x, int y) {
        xPosition = x;
        yPosition = y;
    }

    public boolean isInside() {
        return inside; 
    }
    
    public void setInside(boolean inside) {
        this.inside = inside; 
    }

    public int getHeight() {
        return height / PIXEL_POR_CM; 
    }

    private void draw() {
        if (isVisible) {
            if (shape != null) shape.makeInvisible();
            int esquinaX = xPosition - width / 2;
            shape = new Rectangle();
            shape.changeSize(height, width);
            shape.changeColor(color);
            shape.moveHorizontal(esquinaX - 70);
            shape.moveVertical(yPosition);
            shape.makeVisible();
        }
    }

    public void erase() {
        if (shape != null) 
            shape.makeInvisible();
    }
    
    public boolean isCup() {
        return false;
    }
    
    public boolean hasLidOn() { 
        return false;
    }
    
    public void showLid() {
    // Una tapa no tiene tapa
    }

}