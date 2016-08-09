package com.webservice.healthcheck.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:config.properties")

public class ResourceLocator
{

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
  {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Value("${healthcheck.filestore}")
  private String fileStore;

  @Value("${healthcheck.filestore.filename}")
  private String fileName;

  public String getCompleteFIleName()
  {
    return getFileStore() + getFileName();
  }

  public String getFileStore()
  {
    return fileStore;
  }

  public String getFileName()
  {
    return fileName;
  }

}
