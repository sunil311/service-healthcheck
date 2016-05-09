package com.webservice.healthcheck.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.FileUtils;
//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;

/**
 * Utility to simplify work with files
 */
public class IOUtils
{

  /*public static String generateFileByTemplate(Template template, Map<String, String> values)
  {
    VelocityContext velocityContext = new VelocityContext(values);
    StringWriter writer = new StringWriter();
    template.merge(velocityContext, writer);
    return writer.toString();
  }
*/
  public static void saveXmlToFile(String xmlSource, String fileName)
      throws IOException, URISyntaxException
  {
    URI uri = new URI(fileName);
    FileUtils.writeStringToFile(new File(uri), xmlSource);
  }

  public static void marshallToXML(Class clazz, Object toMarshal, String fullFileName)
      throws JAXBException, URISyntaxException
  {
    URI uri = new URI(fullFileName);
    JAXBContext context = JAXBContextHolder.getContext(clazz);
    Marshaller m = context.createMarshaller();
    m.marshal(toMarshal, new File(uri));
  }
  
  /**
   * Securely unmarshall the XML to prevent XML External Entity Injection attacks.
   * @param context
   * @param input
   * @return
   * @throws JAXBException
   * @throws IOException
   */
  public static Object secureUnmarshallXML(JAXBContext context, InputStream input) 
    throws JAXBException, IOException
  {
    Unmarshaller unmarshaller = context.createUnmarshaller();
    XMLInputFactory xif =  XMLInputFactory.newInstance();
    xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    XMLStreamReader xsr = null;
    try
    {
      xsr = xif.createXMLStreamReader(input);
    }
    catch (XMLStreamException e)
    {
      throw new IOException(e);
    }
    return unmarshaller.unmarshal(xsr);    
  }

}
