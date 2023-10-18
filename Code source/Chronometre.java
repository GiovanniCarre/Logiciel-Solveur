public class Chronometre {
    private static long startTime;
    private static long endTime;
    

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void stop() {
        endTime = System.currentTimeMillis();
            
    }

    public static long time() {
        return endTime - startTime;
        
    }

    
}
