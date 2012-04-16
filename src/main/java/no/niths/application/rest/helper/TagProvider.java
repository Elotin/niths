package no.niths.application.rest.helper;

import java.io.Serializable;

public class TagProvider implements Serializable {
	
	private static final long serialVersionUID = -1339434700493159630L;
	private String tag;

	public TagProvider(String tag) {
		setTag(tag);
	}
	
	public TagProvider() {
		this(null);
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Override
	public String toString() {
		return tag;
	}
}