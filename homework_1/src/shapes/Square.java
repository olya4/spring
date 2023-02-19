package shapes;

public class Square extends GeometricShape {

    private int a;

    public Square(int a) {
        super("Квадрат");
        this.a = a;
    }

    @Override
    public double perimeter() {
        return 4 * a;
    }

    @Override
    public double area() {
        return a * a;
    }
}
