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

	ReportsType(int reportId, String reportTypeName) {
		this.reportId = reportId;
		this.reportTypeName = reportTypeName;
	}

	private String reportTypeName;
	private int reportId;

	public int getReportId() {
		return reportId;
	}

	public String getReportTypeName() {
		return reportTypeName;
	}
}
