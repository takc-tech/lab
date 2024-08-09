package com.takc_tech;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.takc_tech.annnotation.Range;

public class Mapper<T> {

	private Constructor<T> constructor;

	private List<FieldInfo<T>> fieldInfoList;

	public Mapper(Class<T> clazz) throws ReflectiveOperationException {

		constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);

		fieldInfoList = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			fieldInfoList.add(new FieldInfo<T>(clazz, field));
		}

	}

	public T generate(String source) throws ReflectiveOperationException {
		return this.generate(source, constructor.newInstance());
	}

	public T generate(String source, T instance) throws ReflectiveOperationException {

		assert instance != null : "instance is null";

		for (FieldInfo<T> field : fieldInfoList) {
			field.getSetter().invoke(instance, convert(field, source));
		}

		return instance;
	}

	public String convert(FieldInfo<T> field, String source) {

		var range = field.getAnnotation(Range.class);
		if (range == null) return null;

		return source.substring(range.from() - 1, range.to());
	}

}
