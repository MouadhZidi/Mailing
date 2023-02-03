package com.arabsoft.mailing.dao;

import com.arabsoft.mailing.entities.EmailSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSenderDao extends JpaRepository<EmailSender, Integer>  {
	
	@Query(nativeQuery = true, value ="SELECT DISTINCT PRM.PRUID, PRM.EMAIL_ADRESS, "
			+ "PRM.PASSWORD, PRM.HOST_NAME, PRM.PORT, PRM.START_TLS, PRM.PATH_LOGO \r\n" + 
			"FROM   PROFIL_MAIL PRM  \r\n" + 
			"WHERE  1=1\r\n")
	public EmailSender findByAdress();
}