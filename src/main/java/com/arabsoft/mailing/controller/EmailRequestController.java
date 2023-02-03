package com.arabsoft.mailing.controller;

import org.springframework.web.bind.annotation.RestController;
import com.arabsoft.mailing.service.EmailRequestService;
import com.arabsoft.mailing.dao.EmailTemplateDao;
import com.arabsoft.mailing.entities.EmailRequest;
import com.arabsoft.mailing.entities.EmailResponse;
import com.arabsoft.mailing.entities.EmailTemplate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/mailing")
public class EmailRequestController {
  @Autowired
  private EmailRequestService emailRequestService;
  @Autowired
  private EmailTemplateDao emailTemplateDao;
  
	//send simple email
  @PostMapping("/sendemail")
  public ResponseEntity sendEmail(@RequestBody EmailRequest request)
  {
      
      EmailResponse emailResponse;
	  System.out.println(request);
      emailResponse = this.emailRequestService.sendEmail(request);
      if(emailResponse.getCode()=="0"){
          return  ResponseEntity.ok(emailResponse);
      }else{
          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
      }
      
      
  }

  //send email with file
  @PostMapping("/sendemailattachement")
  public ResponseEntity sendEmailWithAttachment(@RequestBody EmailRequest request)
  {
      System.out.println(request);    
      EmailResponse emailResponse = this.emailRequestService.sendWithAttachment(request);
      if(emailResponse.getCode()=="0"){
          return  ResponseEntity.ok(emailResponse);
      }else{
          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
      }
  }
  
  //send email with html content
  @PostMapping("/sendemailhtml")
  public ResponseEntity sendEmailHtml(@RequestBody EmailRequest request)
  {
      System.out.println(request);     
      EmailResponse emailResponse = this.emailRequestService.sendHtmlTemplate(request);
      if(emailResponse.getCode()=="0"){
          return  ResponseEntity.ok(emailResponse);
      }else{
          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
      }
      
      
  }

  //send email with inline image
  @PostMapping("/sendemailinlineimage")
  public ResponseEntity sendEmailWithInlineImage(@RequestBody EmailRequest request)
  {
      System.out.println(request);
      EmailResponse emailResponse = this.emailRequestService.sendEmailInlineImage(request);
      if(emailResponse.getCode()=="0"){
          return  ResponseEntity.ok(emailResponse);
      }else{
          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
      }
  }
  
  //Send email combined Simple mail and Mail with attachement
  @PostMapping("/sendemailcombined")
  public ResponseEntity sendEmailCombined(@RequestBody EmailRequest request)
  {
      System.out.println(request);
      EmailResponse emailResponse = this.emailRequestService.sendEmailCombined(request);
      if(emailResponse.getCode()=="0"){
          return  ResponseEntity.ok(emailResponse);
      }else{
          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
      }
  }

}
