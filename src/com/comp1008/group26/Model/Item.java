package com.comp1008.group26.Model;

public class Item {

	public static final int TEXT = 0;
	public static final int VIDEO = 1;
	public static final int AUDIO = 2;
	public static final int IMAGE = 3;
	private int type=0; //0 text		1 video		2 audio		3 image
	private String title="";
	private String summary="";
	private int image_src=0;
	private String body="";
	private int link=0;
	private String caption="";
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getImage_src() {
		return image_src;
	}
	public void setImage_src(int image_src) {
		this.image_src = image_src;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getLink() {
		return link;
	}
	public void setLink(int link) {
		this.link = link;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}
