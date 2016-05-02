package network.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Define an attribute to be key/value of an {@link Advertisement}
 * @author Julien Prudhomme
 *
 */
@Target({ ElementType.FIELD })
public @interface AdvertisementAttribute {
	public boolean enabled() default true;
	public boolean indexed() default false;
	public boolean signable() default true;
}
