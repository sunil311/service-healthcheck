package com.webservice.healthcheck.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.model.MyWebService;
import com.webservice.helper.ESBHelper;

@Service
public class DocxReport implements ReportPublisher {

	@Override
	public void publish(List<MyWebService> services)
			throws UnexpectedProcessException, IOException, JAXBException,
			JSONException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MM_dd_yyyy_HH_mm_ss");
		String dateStr = dateFormat.format(new Date());
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(ESBHelper.createReport(services));
		FileOutputStream out = new FileOutputStream(new File("ServiceReport_"
				+ dateStr + ".docx"));
		document.write(out);
		out.close();
	}
}
