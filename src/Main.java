import java.io.IOException;
import lesson44.Lesson47Server;

public class Main {
    public static void main(String[] args) {
        try {
            new Lesson47Server("localhost", 9889).start();
            System.out.println("Сервер запущен на http://localhost:9889");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}