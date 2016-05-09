package com.webservice.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ValidationMessage {
  
  private String type;
  
  private String message;
  
  protected String errorCode;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if(obj == null)
    {
      return false;
    }
    if(obj instanceof ValidationMessage)
    {
      ValidationMessage that = (ValidationMessage) obj;
      return StringUtils.equals(type, that.getType()) && StringUtils.equals(message, that.getMessage()) 
        && StringUtils.equals(errorCode, that.getErrorCode());
    }
    return false;
  }
  

  @Override
  public int hashCode()
  {
    HashCodeBuilder builder = new HashCodeBuilder();
    builder.append(type);
    builder.append(message);
    builder.append(errorCode);
    return builder.toHashCode();
  }
}