import java.util.ArrayList;

/**
 * Interfaz que representa un elemento apilable en la torre.
 * Implementada por Cup y Lid.
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 3.0
 */
public interface StackableItem {
    
    int getNumber();
    
    int getHeight();
    
    boolean isInside();
    
    void setInside(boolean value);
    
    int getLevel();
    
    void setLevel(int level);
    
    void erase();
    
    void makeVisible();
    
    void makeInvisible();
    
    int getYPosition();
    
    boolean isCup();
    
    boolean hasLidOn();
    
    void setPosition(int x, int y);
    
    void showLid();
    
}