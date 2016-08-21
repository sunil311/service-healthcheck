package com.webservice.healthcheck.reports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.provider.ResourceLocator;
import com.webservice.helper.ESBHelper;

@Service
public class PDFReport implements ReportPublisher {
	@Override
	public void publish(Map<Integer, List<WebServiceHistory>> servicesMap,
			ResourceLocator resourceLocator) {
		try {
			createPdf(resourceLocator.getTempReport() + "/ServiceReport.pdf",
					servicesMap);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @param servicesMap
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createPdf(String filename,
			Map<Integer, List<WebServiceHistory>> servicesMap)
			throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();
		document.addCreationDate();
		document.setMargins(2, 4, 2, 4);
		document.setPageSize(PageSize.A4);
		List<Paragraph> paragraphs = ESBHelper.createPDFReport(servicesMap);
		for (Paragraph paragraph : paragraphs) {
			document.add(paragraph);
		}

		document.close();
	}
}
