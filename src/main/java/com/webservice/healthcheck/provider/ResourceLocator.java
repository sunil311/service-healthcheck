package com.webservice.healthcheck.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:config.properties")
public class ResourceLocator {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${healthcheck.filestore}")
	private String fileStore;

	@Value("${healthcheck.filestore.filename}")
	private String fileName;

	@Value("${healthcheck.reports.receiverMails}")
	private String reportReceiverMails;

	@Value("${healthcheck.reports.tempReport}")
	private String tempReport;

	public String getCompleteFIleName() {
		return getFileStore() + getFileName();
	}

	public String getFileStore() {
		return fileStore;
	}

	public String getFileName() {
		return fileName;
	}

	public String getReportReceiverMails() {
		return reportReceiverMails;
	}

	public void setReportReceiverMails(String reportReceiverMails) {
		this.reportReceiverMails = reportReceiverMails;
	}

	public String getTempReport() {
		return tempReport;
	}

	public void setTempReport(String tempReport) {
		this.tempReport = tempReport;
	}

}
