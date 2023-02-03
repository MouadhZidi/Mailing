package com.arabsoft.mailing.entities;

public class EmailRequest {
    private String from;
    private String to;
    private String to_cc;
    private String to_bcc;
    private String subject;
    private String message;
    private String path_report;
  //  private Integer IdTemplate;
     
	public String getPath_report() {
		return path_report;
	}
	public void setPath_report(String path_report) {
		this.path_report = path_report;
	}
	public String getFrom() { 
    	return from; 
    }
    public void setFrom(String from) { 
    	this.from = from; 
    }
    public String getTo() { 
    	return to; 
    }
    public void setTo(String to) { 
    	this.to = to; 
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "EmailRequest{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", to_cc='" + to_cc + '\'' +
                ", to_bcc='" + to_bcc + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", path_report='" + path_report + '\'' +
                '}';
    }
	public String getTo_cc() {
		if (to_cc == null)
		  return "";
		else
		return to_cc;
	}
	public void setTo_cc(String to_cc) {
		this.to_cc = to_cc;
	}
	public String getTo_bcc() {
		if (to_bcc == null)
			  return "";
		else
		   return to_bcc;
	}
	public void setTo_bcc(String to_bcc) {
		this.to_bcc = to_bcc;
	}
    
	/*public Integer getIdTemplate() {
		return IdTemplate;
	}
	public void setIdTemplate(Integer idTemplate) {
		IdTemplate = idTemplate;
	}*/


}
