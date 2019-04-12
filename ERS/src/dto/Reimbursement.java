package dto;

import java.sql.Date;

public class Reimbursement {
	private long reimbId = 0;
	private double reimbAmount = 0;
	private Date dateSubmitted;
	private Date dateResolved;
	private String description = "";
	private String author = "";
	private String resolver = "";
	private String statusId = "";
	private String typeId = "";

	public Reimbursement() {
	}

	public Reimbursement(double reimbAmount, String description, String author, String typeId) {
		this.reimbAmount = reimbAmount;
		this.description = description;
		this.author = author;
		this.typeId = typeId;
	}

	public Reimbursement(long reimbId, double reimbAmount, Date dateSubmitted, Date dateResolved, String description,
			String author, String resolver, String statusId, String typeId) {
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusId = statusId;
		this.typeId = typeId;
	}

	public long getReimbId() {
		return reimbId;
	}

	public double getReimbAmount() {
		return reimbAmount;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public Date getDateResolved() {
		return dateResolved;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public String getResolver() {
		return resolver;
	}

	public String getStatusId() {
		return statusId;
	}

	public String getTypeId() {
		return typeId;
	}
}
