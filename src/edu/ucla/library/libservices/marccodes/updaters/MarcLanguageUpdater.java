package edu.ucla.library.libservices.marccodes.updaters;

import edu.ucla.library.libservices.marccodes.beans.MarcLanguageCode;
import edu.ucla.library.libservices.marccodes.xml.conversion.MarcXmlConverter;
import edu.ucla.library.libservices.marccodes.xml.retrieval.MarcXmlRetriever;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
import java.util.Vector;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class MarcLanguageUpdater
{
  private static Properties props;
  private static DriverManagerDataSource ds;

  public static void main( String[] args )
  {
    Vector<MarcLanguageCode> codes;

    loadProperties( "C:\\Temp\\lang\\langs.properties" ); //args[ 0 ] );
    makeConnection();

    codes = 
        MarcXmlConverter.convertLanguages( 
          MarcXmlRetriever.getLanguages( props.getProperty( "XML_URL" ), 
                                         props.getProperty( "BASE_ELEMENT" ) ) );

    for ( MarcLanguageCode theCode: codes )
    {
      System.out.println( "dealing with language " + theCode.toString() );
      JdbcTemplate counter, updater;

      counter = new JdbcTemplate( ds );
      updater = new JdbcTemplate( ds );

      if ( counter.queryForInt( props.getProperty( "COUNT_SQL" ), 
                                new Object[] { theCode.getLangCode() } ) == 
           0 )
      {
        System.out.println( "\t\tadding new language" );
        updater.update( props.getProperty( "INSERT_SQL" ), 
                        new Object[] { theCode.getLangCode(), 
                                       theCode.getLangName(), 
                                       ( theCode.isDeprecated() ? "Y" : 
                                         "N" ) } );
      }
      else
      {
        System.out.println( "\t\tupdating old language" );
        updater.update( props.getProperty( "UPDATE_SQL" ), 
                        new Object[] { theCode.getLangName(), 
                                       ( theCode.isDeprecated() ? "Y" : 
                                         "N" ), theCode.getLangCode() } );
      }
    }
  }

  private static void loadProperties( String propFile )
  {
    props = new Properties();
    try
    {
      props.load( new FileInputStream( new File( propFile ) ) );
    }
    catch ( IOException ioe )
    {
      ioe.printStackTrace();
    }
  }

  private static void makeConnection()
  {
    ds = new DriverManagerDataSource();
    ds.setDriverClassName( props.getProperty( "DRIVER_CLASSNAME" ) );
    ds.setUrl( props.getProperty( "DB_URL" ) );
    ds.setUsername( props.getProperty( "DB_USERNAME" ) );
    ds.setPassword( props.getProperty( "DB_PASSWORD" ) );
  }

}
