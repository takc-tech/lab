package com.takc_tech.bean;

import com.takc_tech.annnotation.Range;

public class Item {

	@Range(from = 1, to = 5)
	String key1;

	@Range(from = 6, to = 10)
	String key2;

	@Range(from = 11, to = 15)
	String key3;

	void setKey1(String key1) {
		this.key1 = key1;
	}

	void setKey2(String key2) {
		this.key2 = key2;
	}

	void setKey3(String key3) {
		this.key3 = key3;
	}

	@Override
	public String toString() {
		return "Item [key1=" + key1 + ", key2=" + key2 + ", key3=" + key3 + "]";
	}

}
