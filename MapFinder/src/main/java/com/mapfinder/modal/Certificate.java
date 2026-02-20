//$Id$
package com.mapfinder.modal;

public class Certificate {
	int id;
	String name;
	String rating;
	String issued_by;

	public Certificate() {
		super();
	}

	public Certificate(int id, String name, String rating, String issued_by) {
		super();
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.issued_by = issued_by;
	}
	
	public Certificate(String name, String rating, String issued_by) {
		this.name = name;
		this.rating = rating;
		this.issued_by = issued_by;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id >= 0)
			this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null)
			this.name = name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		if (rating!=null)
			this.rating = rating;
	}

	public String getIssued_by() {
		return issued_by;
	}

	public void setIssued_by(String issued_by) {
		if (issued_by != null)
			this.issued_by = issued_by;
	}

}
