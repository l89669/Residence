/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence.containers;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface CommandAnnotation {
    public boolean simple();

    public int priority();
}

