package shapes;

public abstract class GeometricShape {

    private String title;

    public GeometricShape(String title) {
        this.title = title;
    }

    public abstract double perimeter();

    public abstract double area();

    public String getTitle() {
        return title;
    }
}
