package com.arabsoft.mailing.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "param_email")
public class EmailTemplate {
	

	@Id
	@Column(name = "COD_MAIL")
	private Integer Id; 
	@Column(name = "SUBJECT_EMAIL")
	private String Subject; 
	@Column(name = "BODY_MAIL")
	private String Content;
	@Column(name = "COD_LANID")
	private String Language;
	@Column(name = "EMAIL_TYPE")
	private String Type;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	         

}
