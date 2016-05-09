package com.webservice.healthcheck.process;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

public class JAXBContextHolder
{
  private static final Logger LOG = Logger.getLogger(JAXBContextHolder.class);
  private static final Map<Class, JAXBContext> contextClassMap = new HashMap<Class, JAXBContext>();
  private static final Map<String, JAXBContext> contextPackageMap = new HashMap<String, JAXBContext>();

  public static synchronized JAXBContext getContext(Class c)
  {
    if (contextClassMap.get(c) == null)
    {
      try
      {
        contextClassMap.put(c, JAXBContext.newInstance(c));
      }
      catch (JAXBException e)
      {
        LOG.error(e.getMessage(), e);
      }
    }
    return contextClassMap.get(c);
  }

  public static synchronized JAXBContext getContext(String p)
  {
    if (contextPackageMap.get(p) == null)
    {
      try
      {
        contextPackageMap.put(p, JAXBContext.newInstance(p));
      }
      catch (JAXBException e)
      {
        LOG.error(e.getMessage(), e);
      }
    }
    return contextPackageMap.get(p);
  }
}
