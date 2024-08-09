package com.takc_tech;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldInfo<T> {

	/** アノテーション */
	private Annotation[] annotations;

	/** セッター(キャッシュ) */
	private Method setter;

	public FieldInfo(Class<T> clazz, Field field) throws NoSuchMethodException, SecurityException {

		annotations = field.getAnnotations();

		var fieldName = field.getName();
		var setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		setter = clazz.getDeclaredMethod(setterName, field.getType());
		setter.setAccessible(true);
	}

	public <S extends Annotation> S getAnnotation(Class<S> clazz) {

		for (Annotation annotation : annotations) {
			if (clazz.isInstance(annotation)) {
				return clazz.cast(annotation);
			}
		}
		return null;
	}

	public Method getSetter() {
		return this.setter;
	}

}