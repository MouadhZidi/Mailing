package com.arabsoft.mailing.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arabsoft.mailing.entities.EmailTemplate;

@Repository
public interface EmailTemplateDao extends JpaRepository<EmailTemplate, Integer> {
	
	@Query(nativeQuery = true, value ="SELECT prm.COD_MAIL, prm.SUBJECT_EMAIL, prm.BODY_MAIL, prm.COD_LANID, prm.EMAIL_TYPE\r\n" + 
			"FROM   PARAM_EMAIL prm \r\n" + 
			"WHERE  1=1 ")
	public List<EmailTemplate> ListAll();
	
	@Query(nativeQuery = true, value ="SELECT prm.COD_MAIL, prm.SUBJECT_EMAIL, prm.BODY_MAIL, prm.COD_LANID, prm.EMAIL_TYPE\r\n" + 
			"FROM   PARAM_EMAIL prm \r\n" + 
			"WHERE  1=1 " + 
			"AND    prm.COD_MAIL= :id \r\n" )
	public EmailTemplate Get(@Param("id") Integer id);
}
