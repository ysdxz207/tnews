package com.puyixiaowo.tnews.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Moses
 * @date 2018-03-26 18:23:55
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ReqNotEmpty {
	String message() default "";
}
