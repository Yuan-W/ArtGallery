package com.comp1008.group26.Model;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class Partner {
	private int logo;
	private String name;
	private String details;
	private String link;

	public Partner(int logo, String name, String details, String link) {
		this.logo = logo;
		this.name = name;
		this.details = details;
		this.link = link;
	}

	public int getLogo() {

		return logo;
	}

	public void setLogo(int logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
