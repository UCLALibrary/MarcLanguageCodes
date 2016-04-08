package edu.ucla.library.libservices.marccodes.xml.retrieval;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class MarcXmlRetriever
{
  public static NodeList getLanguages(String xmlUrl, String baseElement)
  {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    NodeList nodes;

    document = null;
    nodes = null;
    
    try
    {
      factory = DocumentBuilderFactory.newInstance();
      builder = factory.newDocumentBuilder();
      document = 
        builder.parse( new URL( xmlUrl ).openStream() );
      nodes = document.getElementsByTagName( baseElement );
    }
    catch ( ParserConfigurationException pce )
    {
      pce.printStackTrace();
    }
    catch ( SAXException saxe )
    {
      saxe.printStackTrace();
    }
    catch ( IOException ioe )
    {
      ioe.printStackTrace();
    }

    return nodes;
  }
}
