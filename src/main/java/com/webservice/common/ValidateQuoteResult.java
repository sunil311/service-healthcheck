package com.webservice.common;

import java.util.ArrayList;
import java.util.List;

public class ValidateQuoteResult {
  
  private String statusCode;
  
  private List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();//TODO: possibly make this a user defined type if need more info than just the message
  
  /**
   * The generic message that should display in place of fatal exceptions
   */
  private String fatalErrorMessage;

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public List<ValidationMessage> getValidationMessages() {
    return validationMessages;
  }

  public void setValidationMessages(List<ValidationMessage> validationMessages) {
    this.validationMessages = validationMessages;
  }

  public String getFatalErrorMessage() {
    return fatalErrorMessage;
  }

  public void setFatalErrorMessage(String fatalErrorMessage) {
    this.fatalErrorMessage = fatalErrorMessage;
  }

}
