package com.puyixiaowo.tnews.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Determine request paramaters is empty or not.</p>
 *
 * <p>The process is in {@link com.puyixiaowo.tnews.bean.RequestBean}</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ReqNotEmpty {
	String message() default "";
}
