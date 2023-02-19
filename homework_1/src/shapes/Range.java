package shapes;

public class Range extends GeometricShape {

    private int a;

    public Range(int a) {
        super("Круг");
        this.a = a;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * a;
    }

    @Override
    public double area() {
        return Math.PI * a * a;
    }
}
