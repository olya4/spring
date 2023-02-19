package car;

public class Main {
    public static void main(String[] args) {
        LightWeightCar lightWeightCar = new LightWeightCar();
        lightWeightCar.open();
        lightWeightCar.start();
        lightWeightCar.move();
        System.out.println();

        Lorry lorry = new Lorry();
        lorry.open();
        lorry.start();
        lorry.move();
        lorry.stop();

    }
}
