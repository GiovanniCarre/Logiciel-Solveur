import java.lang.reflect.Method;

public class Test {
	public static void main(String[] args) {
		Class<?> classe = Test.class;

        // Recherche de la m�thode annot�e @Annotation
        Method[] methods = classe.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Annotation.class)) {
                // La m�thode est annot�e avec @Annotation
                try {
                    // Cr�ez une instance de MoteurZeroPlus si n�cessaire
                    MoteurZeroPlus m = null;

                    // Ex�cutez la m�thode annot�e
                    method.invoke(null, m);

                    System.out.println("La m�thode annot�e a �t� ex�cut�e avec succ�s.");
                } catch (Exception e) {
                    System.err.println("Erreur lors de l'ex�cution de la m�thode annot�e.");
                    e.printStackTrace();
                }
            }
        }
	}
}
