package br.org.flem.primeiroemprego.util.annotation;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author emsilva
 */

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
    String value();
    String valueAdicional() default "";
}
