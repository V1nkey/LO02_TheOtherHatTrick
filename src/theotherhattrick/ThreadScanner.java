package theotherhattrick;

import views.ConsoleView;

import java.util.Scanner;

public class ThreadScanner implements Runnable {
    private static ThreadScanner tScanner = null;
    private String result;
    private boolean waitingResponse;
    private Game game;
    private Thread t;

    private ThreadScanner() {
        this.game = Game.getInstance();
        this.t = new Thread(this);
    }

    public static ThreadScanner getInstance() {
        if (tScanner == null)
            tScanner = new ThreadScanner();

        return tScanner;
    }

    @Override
    public void run() {
        this.waitingResponse = true;
        Scanner scanner = new Scanner(System.in);
        this.result = scanner.nextLine().toLowerCase();
        this.waitingResponse = false;
    }

    public String getResult() {
        return this.result;
    }

    public boolean getWaitingResponse() {
        return this.waitingResponse;
    }

    public void start() {
        this.t.run();
    }
}