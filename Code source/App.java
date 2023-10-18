public class App implements Runnable{
    public static void main(String[] args) throws Exception {

        Graphism.lancer();
        new Thread(new App()).start();
    }

    @Override
    public void run() {
    }
}
