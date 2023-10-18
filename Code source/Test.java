import java.lang.reflect.Method;

public class Test {
	public static void main(String[] args) {
		Class<?> classe = Test.class;

        // Recherche de la méthode annotée @Annotation
        Method[] methods = classe.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Annotation.class)) {
                // La méthode est annotée avec @Annotation
                try {
                    // Créez une instance de MoteurZeroPlus si nécessaire
                    MoteurZeroPlus m = null;

                    // Exécutez la méthode annotée
                    method.invoke(null, m);

                    System.out.println("La méthode annotée a été exécutée avec succès.");
                } catch (Exception e) {
                    System.err.println("Erreur lors de l'exécution de la méthode annotée.");
                    e.printStackTrace();
                }
            }
        }
	}
}
