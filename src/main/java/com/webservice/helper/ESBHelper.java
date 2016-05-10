package com.webservice.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.connecture.integration.esb.model.ESBResponse;
import com.webservice.exception.UnexpectedProcessException;
import com.webservice.healthcheck.process.IOUtils;
import com.webservice.healthcheck.process.JAXBContextHolder;

public class ESBHelper
{
  protected static final String AUTHORIZATION_HEADER = "Authorization";
  protected static final String APPLICATION_XML_VALUE = "application/xml";
  protected static final String CONTENT_TYPE_HEADER = "Content-Type";
  protected static final String ACCEPT_HEADER = "Accept";
  protected static final String EXTERNAL_SERVICE_ERROR_RESPONSE_CODE = "520";
  protected static final String SUCCESSFUL_ESB_RESPONSE_CODE = "200";
  public static final String EXTERNAL_ENDPOINT_ERROR_MESSAGE = "The UHG Service is Down at the moment. Please try again later.";

  private static final Log LOGGER = LogFactory.getLog(ESBHelper.class);

  public static boolean isSuccessfulEsbResponseCode(String esbResponseCode)
  {
    return SUCCESSFUL_ESB_RESPONSE_CODE.equals(esbResponseCode);
  }

  public static ESBResponse unmarshallEsbResponse(HttpResponse response)
    throws JAXBException, IOException
  {
    JAXBContext context = JAXBContextHolder.getContext(ESBResponse.class);
    ESBResponse esbResponse = (ESBResponse) IOUtils.secureUnmarshallXML(context, response
      .getEntity().getContent());
    return esbResponse;
  }

  public static boolean isExternalEndpointDown(String esbResponseCode)
  {
    return EXTERNAL_SERVICE_ERROR_RESPONSE_CODE.equals(esbResponseCode);
  }

  public static HttpResponse sendToHTTP(
    String xmlString,
    String esbUri,
    String esbUsername,
    String esbPassword) throws SocketTimeoutException
  {
    // uhgEsbRequestTransactionHandler.preESBCallProcess();
    try
    {
      HttpParams httpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(httpParams, 40000);
      HttpClient httpclient = new DefaultHttpClient(httpParams);
      HttpPost httpPost = getHttpRequest(xmlString, esbUri, esbUsername, esbPassword);
      return httpclient.execute(httpPost);
    }
    catch (ClientProtocolException e)
    {
      throw new UnexpectedProcessException(e);
    }
    catch (IOException e)
    {
      LOGGER.error(e);
      throw new SocketTimeoutException("Socket timeout while calling valiadte quote");
    }
    catch (Exception e)
    {
      LOGGER.error(e);
      throw new SocketTimeoutException("Socket timeout while calling valiadte quote");
    }
    finally
    {
      // uhgEsbRequestTransactionHandler.postESBCallProcess();
    }
  }

  public static HttpPost getHttpRequest(
    String xmlString,
    String httpURI,
    String esbUsername,
    String esbPassword) throws UnsupportedEncodingException
  {
    HttpEntity xmlEntity = new StringEntity(xmlString);
    HttpPost httpPost = new HttpPost(httpURI);
    httpPost.setEntity(xmlEntity);
    httpPost.setHeader(ACCEPT_HEADER, APPLICATION_XML_VALUE);
    httpPost.setHeader(CONTENT_TYPE_HEADER, APPLICATION_XML_VALUE);
    setAuthorizationHeader(esbUsername, esbPassword, httpPost);
    return httpPost;
  }

  /**
   * Sets the http authorization request header if the esbUsername and
   * esbPassword are both not null
   * 
   * @param esbUsername
   * @param esbPassword
   * @param httpPost
   */
  private static void setAuthorizationHeader(
    String esbUsername,
    String esbPassword,
    HttpPost httpPost)
  {

    if (esbUsername != null && esbPassword != null)
    {
      String userpassword = esbUsername + ":" + esbPassword;
      String encodedAuthorization = "Basic "
        + new String(Base64.encodeBase64(userpassword.getBytes()));
      httpPost.setHeader(AUTHORIZATION_HEADER, encodedAuthorization);
    }

  }

  public static String getXMLasString() throws FileNotFoundException, IOException
  {
    File xmlFile = new File(
      "E:\\jboss-5.1.0.GA\\filestore\\integration\\37371\\20160321_124701_KEzCj_ESB_VALIDATE_QUOTE_REQUEST_Q37371.xml");
    Reader fileReader = new FileReader(xmlFile);
    BufferedReader bufReader = new BufferedReader(fileReader);
    StringBuilder sb = new StringBuilder();
    String line = bufReader.readLine();
    while (line != null)
    {
      sb.append(line).append("\n");
      line = bufReader.readLine();
    }
    String xml2String = sb.toString();
    return xml2String;
  }
}