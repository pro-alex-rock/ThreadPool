package app;

/**
 * @author Oleksandr Haleta
 * 2021
 */
public class Test implements Runnable {
    private int number;

    public Test(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println("Test class print message: " + number);
    }
}
