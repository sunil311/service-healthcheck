package com.webservice.healthcheck.reports;

import com.webservice.exception.ReportPublisherException;

public class ReportFactory {
	public ReportPublisher getReportPublisher(ReportsType reportsType)
			throws ReportPublisherException {
		if (reportsType.equals(ReportsType.REPORT_DOCX)) {
			return new DocxReport();
		} else if (reportsType.equals(ReportsType.REPORT_PDF)) {
			return new PDFReport();
		} else if (reportsType.equals(ReportsType.REPORT_MAIL)) {
			return new MailReport();
		} else {
			throw new ReportPublisherException("Report publisher not found...");
		}
	}
}
