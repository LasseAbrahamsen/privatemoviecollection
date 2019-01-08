package movieplayer.be;

/**
 *
 * @author a
 */
public class Category {
    
    private String name;
    private final int ID;

    public Category(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getID() {
        return ID;
    }
}
