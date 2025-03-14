package CardUtil;

public class BoundingBox {
    public final int x1, y1, x2, y2;
    public BoundingBox(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public boolean encloses(BoundingBox other) {
        return this.x1 <= other.x1 && this.y1 <= other.y1 && this.x2 >= other.x2 && this.y2 >= other.y2;
    }
}
