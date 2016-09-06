package com.gt22.gt22core.texturegen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 *	If this annotated to Item/Block field json generator will skip it, use for custom renderers
 */
public @interface DoNotApplyTexture {

}
