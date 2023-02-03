package com.arabsoft.mailing.controller;


import com.arabsoft.mailing.dao.EmailSenderDao;
import com.arabsoft.mailing.entities.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EmailSender")
public class EmailSenderController {
	@Autowired
	private EmailSenderDao emailSenderDao;
	
	@CrossOrigin
	@GetMapping(value = "/findByAdress/{emailAdress}")
	public EmailSender findByAdress() {
		return this.emailSenderDao.findByAdress();
	}
}