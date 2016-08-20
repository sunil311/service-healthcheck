package com.webservice.healthcheck.reports;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.webservice.healthcheck.model.WebServiceHistory;
import com.webservice.healthcheck.provider.ResourceLocator;
import com.webservice.helper.ESBHelper;

@Service
public class MailReport implements ReportPublisher {

	@Override
	public void publish(Map<Integer, List<WebServiceHistory>> servicesMap,
			ResourceLocator resourceLocator) {
		String reportReceivers[] = resourceLocator.getReportReceiverMails()
				.split(",");

		final String user = "kdmalviyan@gmail.com";
		final String password = "kds@2015";
		Properties props = new Properties();
		/*
		 * String host = "smtp.gmail.com"; String port = "587"; String
		 * SSLProtocol = "OFF"; String TLSProtocol = "ON";
		 * props.setProperty("mail.smtp.host", host);
		 * props.put("mail.smtp.auth", "true"); props.put(SSLProtocol,
		 * SSLProtocol); props.put(port, port); props.put(TLSProtocol,
		 * TLSProtocol);
		 */
		/*props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");*/

		/*
		 * Session session = Session.getDefaultInstance(props, new
		 * javax.mail.Authenticator() { protected PasswordAuthentication
		 * getPasswordAuthentication() { return new PasswordAuthentication(user,
		 * password); } });
		 */
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"healthservicereports@connecture.com"));
			for (String reportReceiver : reportReceivers) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						reportReceiver));
				msg.setSubject("Webservice Health Check Report");
				msg.setText(ESBHelper.createReport(servicesMap));
				Transport.send(msg);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
