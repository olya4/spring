package shapes;

public class Main {
    public static void main(String[] args) {

        Range range = new Range(2);
        System.out.println(range.getTitle());
        System.out.println(range.area());
        System.out.println(range.perimeter());
        System.out.println();

        Square square = new Square(5);
        System.out.println(square.getTitle());
        System.out.println(square.perimeter());
        System.out.println(square.area());
        System.out.println();

        Triangle triangle = new Triangle(3, 4, 5);
        System.out.println(triangle.getTitle());
        System.out.println(triangle.perimeter());
        System.out.println(triangle.area());
        System.out.println();

    }
}
