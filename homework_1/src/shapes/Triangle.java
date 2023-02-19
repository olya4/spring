package shapes;

public class Triangle extends GeometricShape {

    private int a;
    private int b;
    private int c;

    public Triangle(int a, int b, int c) {
        super("Треугольник");
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double perimeter() {
        return a + b + c;
    }

    @Override
    public double area() {
        double p = perimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
