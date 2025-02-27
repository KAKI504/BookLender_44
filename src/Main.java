import java.io.IOException;
import lesson44.Lesson45Server;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson45Server("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}