package com.arabsoft.mailing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arabsoft.mailing.dao.EmailTemplateDao;
import com.arabsoft.mailing.entities.EmailTemplate;

@RestController
@RequestMapping(value = "/EmailTemplate")
public class EmailTemplateController {
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	@CrossOrigin
	@GetMapping(value = "/ListAll")
	public List<EmailTemplate> ListAll() {
		return this.emailTemplateDao.ListAll();
	}
	

}
