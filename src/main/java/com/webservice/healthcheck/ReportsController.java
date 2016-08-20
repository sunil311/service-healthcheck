/**
 * 
 */
package com.webservice.healthcheck;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webservice.exception.ReportPublisherException;
import com.webservice.healthcheck.process.ServicehealthcheckProcess;
import com.webservice.healthcheck.provider.ResourceLocator;
import com.webservice.healthcheck.reports.ReportFactory;
import com.webservice.healthcheck.reports.ReportPublisher;
import com.webservice.healthcheck.reports.ReportsType;
import com.webservice.helper.ESBHelper;

/**
 * @author kuldeep
 * 
 */
@Controller
public class ReportsController {

	@Autowired
	ReportFactory reportFactory;
	@Autowired
	ServicehealthcheckProcess servicehealthcheckProcess;
	@Autowired
	ResourceLocator resourceLocator;

	@RequestMapping(value = "publishReport/{type}", method = RequestMethod.GET)
	public void downloadServiceReport(String ids, HttpServletResponse response,
			HttpServletRequest request, ModelAndView modelAndView,
			@PathVariable("type") String type) throws ReportPublisherException,
			IOException {
		ReportsType reportsType = ESBHelper.createReportType(type);
		if (reportsType != null) {
			ReportPublisher reportPublisher = reportFactory
					.getReportPublisher(reportsType);
			reportPublisher.publish(
					servicehealthcheckProcess.prepareWebServiceHistory(),
					resourceLocator);
			if (!reportsType.equals(ReportsType.REPORT_MAIL)) {
				File reportFile = new File(resourceLocator.getTempReport()
						+ "/ServiceReport." + type);
				if (!reportFile.exists()) {
					String errorMessage = "Sorry. The file you are looking for does not exist";
					System.out.println(errorMessage);
					OutputStream outputStream = response.getOutputStream();
					outputStream.write(errorMessage.getBytes(Charset
							.forName("UTF-8")));
					outputStream.close();
					return;
				}
				String mimeType = URLConnection
						.guessContentTypeFromName(reportFile.getName());
				if (mimeType == null) {
					System.out
							.println("mimetype is not detectable, will take default");
					mimeType = "application/octet-stream";
				}
				System.out.println("mimetype : " + mimeType);
				response.setContentType(mimeType);
				response.setHeader(
						"Content-Disposition",
						String.format("inline; filename=\""
								+ reportFile.getName() + "\""));
				response.setContentLength((int) reportFile.length());
				InputStream inputStream = new BufferedInputStream(
						new FileInputStream(reportFile));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				reportFile.delete();
			}
		}
	}
}
