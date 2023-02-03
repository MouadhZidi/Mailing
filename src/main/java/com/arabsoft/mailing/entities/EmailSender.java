package com.arabsoft.mailing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROFIL_MAIL")
public class EmailSender {
	@Id
	@Column(name = "PRUID")
	private Integer Id; 
	
	@Column(name = "EMAIL_ADRESS")
	private String EmailAdress;
	
	@Column(name = "PASSWORD")
	private String Pwd; 
	
	@Column(name = "HOST_NAME")
	private String HostName; 
	
	@Column(name = "PORT")
	private Integer Port;
	
	@Column(name = "START_TLS")
	private String Starttls;
	
	@Column(name = "PATH_LOGO")
	private String PathLogo;
	
	public String getPathLogo() {
		return PathLogo;
	}
	public void setPathLogo(String pathLogo) {
		PathLogo = pathLogo;
	}
	public String getStarttls() {
		return Starttls;
	}
	public void setStarttls(String starttls) {
		Starttls = starttls;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getEmailAdress() {
		return EmailAdress;
	}
	public void setEmailAdress(String emailAdress) {
		EmailAdress = emailAdress;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public String getHostName() {
		return HostName;
	}
	public void setHostName(String hostName) {
		HostName = hostName;
	}
	public Integer getPort() {
		return Port;
	}
	public void setPort(Integer port) {
		Port = port;
	} 
}
