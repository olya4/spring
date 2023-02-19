package car;

public abstract class Car {
    public Engine engine;
    private String color;
    private String name;

    abstract void open();

    protected void start() {
        System.out.println("Car starting");
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private class Engine {
    }
}
