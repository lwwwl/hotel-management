package com.example.hotelmanagement.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that an App authentication token is required.
 * Can be applied to classes or methods.
 * The token should be provided in the Authorization header as "Bearer {token}".
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAppToken {
}

