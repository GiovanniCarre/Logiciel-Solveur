import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Indique la portée de rétention de l'annotation
@Target(ElementType.METHOD) // Indique où l'annotation peut être utilisée (dans cette exemple, sur les méthodes)
public @interface Annotation {
    // Définissez ici les éléments de votre annotation (si nécessaire)
}