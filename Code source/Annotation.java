import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Indique la port�e de r�tention de l'annotation
@Target(ElementType.METHOD) // Indique o� l'annotation peut �tre utilis�e (dans cette exemple, sur les m�thodes)
public @interface Annotation {
    // D�finissez ici les �l�ments de votre annotation (si n�cessaire)
}