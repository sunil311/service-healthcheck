package com.webservice.healthcheck.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICES")
public class MyWebService
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String serviceName;
  private boolean isActive;
  private String serviceUrl;
  private String userId;

  private String password;

  public String getServiceName()
  {
    return serviceName;
  }

  public void setServiceName(String serviceName)
  {
    this.serviceName = serviceName;
  }

  public boolean isActive()
  {
    return isActive;
  }

  public void setActive(boolean isActive)
  {
    this.isActive = isActive;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getServiceUrl()
  {
    return serviceUrl;
  }

  public void setServiceUrl(String serviceUrl)
  {
    this.serviceUrl = serviceUrl;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

}
