package org.softuni.residentevil.common.annotations;

import org.softuni.residentevil.entities.enums.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PreAuthenticate {

    boolean loggedIn() default false;

    UserRole role() default UserRole.USER;
}
