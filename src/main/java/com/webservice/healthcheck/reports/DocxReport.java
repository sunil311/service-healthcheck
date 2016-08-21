package com.webservice.healthcheck.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.provider.ResourceLocator;
import com.webservice.helper.ESBHelper;

@Service
public class DocxReport implements ReportPublisher {
	@Override
	public void publish(Map<Integer, List<WebServiceHistory>> servicesMap,
			ResourceLocator resourceLocator) {
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(ESBHelper.createDocxReport(servicesMap));
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(resourceLocator.getTempReport()
					+ "/ServiceReport.docx"));
			try {
				document.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
