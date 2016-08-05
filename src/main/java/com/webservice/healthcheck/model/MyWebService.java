package com.webservice.healthcheck.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MY_SERVICES")
public class MyWebService
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String serviceName;
  private String status;
  private String serviceUrl;
  private String userId;
  private String password;
  private Long executionTime;

  public String getServiceName()
  {
    return serviceName;
  }

  public void setServiceName(String serviceName)
  {
    this.serviceName = serviceName;
  }
  
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
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

public Long getExecutionTime() {
	return executionTime;
}

public void setExecutionTime(Long executionTime) {
	this.executionTime = executionTime;
}

}
