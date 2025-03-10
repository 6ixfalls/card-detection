public class Point {
    public final int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Point add(Point p) {
        return new Point(this.x + p.x, this.y + p.y);
    }
    public Point div(int n) {
        return new Point(this.x / n, this.y / n);
    }
    public int distSquared(Point p) {
        int x = this.x - p.x;
        int y = this.y - p.y;
        return x * x + y * y;
    }
    public boolean equals(Point p) {
        return this.x == p.x && this.y == p.y;
    }
}
