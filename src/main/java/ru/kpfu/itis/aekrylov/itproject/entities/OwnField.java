package ru.kpfu.itis.aekrylov.itproject.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/8/16 4:44 PM
 *
 * Marks entities' fields not mapped to DB fields
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface OwnField {}
