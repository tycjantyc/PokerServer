package pl.edu.agh.kis.pz1;
import pl.edu.agh.kis.pz1.util.Player;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokerServer {
    static int ile_graczy;
    public static void main(String [] args) throws Exception {


        try (ServerSocket listener = new ServerSocket(9000)) {
            System.out.println("Poker Server is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(100);
            while (true) {
                System.out.println("Nowa sesja...");
                System.out.println("Podaj ilość graczy (pomiędzy 2 a 4)(wybierz -1 żeby zamknąć serwer): ");
                Scanner scanner = new Scanner(System.in);
                ile_graczy = scanner.nextInt();
                if(ile_graczy == -1){
                    break;
                }

                if(ile_graczy > 4 || ile_graczy<2){
                    System.out.println("Zła ilość graczy sproboj ponownie!");
                    continue;
                }


                for(int i = 0;i<ile_graczy;i++){
                    pool.execute(new Player(listener.accept(), i+1, ile_graczy));
                }
            }
            System.out.println("Zamykanie...");
        }
    }
}
