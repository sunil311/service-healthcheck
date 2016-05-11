package com.webservice.healthcheck.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICES_HISTORY")
public class WebServiceHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String serviceName;
	private String serviceUrl;
	private String status;
	private Date lastStatusTime;
	private Integer webServiceId;
	private Long executionTime;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

	public Date getLastStatusTime() {
		return lastStatusTime;
	}

	public void setLastStatusTime(Date lastStatusTime) {
		this.lastStatusTime = lastStatusTime;
	}

	public Integer getWebServiceId() {
		return webServiceId;
	}

	public void setWebServiceId(Integer webServiceId) {
		this.webServiceId = webServiceId;
	}

	public Long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}
}
