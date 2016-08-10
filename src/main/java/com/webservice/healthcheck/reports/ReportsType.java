/**
 * 
 */
package com.webservice.healthcheck.reports;

/**
 * @author kuldeep
 * 
 */
public enum ReportsType {

	REPORT_DOCX(1, "docx"), REPORT_MAIL(2, "mail"), REPORT_PDF(3, "pdf");

	ReportsType(int reportId, String reportName) {
		this.reportId = reportId;
		this.reportName = reportName;
	}

	private String reportName;
	private int reportId;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
}
