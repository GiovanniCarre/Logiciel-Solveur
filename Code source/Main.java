public class Main implements Runnable {
	public static void main(String[] args) throws Exception {

		Graphism.lancer();
		new Thread(new Main()).start();
	}

	@Override
	public void run() {
	}
}
