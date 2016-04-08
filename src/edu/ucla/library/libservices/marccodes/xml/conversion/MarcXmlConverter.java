package edu.ucla.library.libservices.marccodes.xml.conversion;

import edu.ucla.library.libservices.marccodes.beans.MarcLanguageCode;

import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MarcXmlConverter
{
  public static Vector<MarcLanguageCode> convertLanguages( NodeList nodes )
  {
    Vector<MarcLanguageCode> languages;

    languages = new Vector<MarcLanguageCode>();

    for ( int i = 0; i < nodes.getLength(); i++ )
    {
      NodeList children;
      String code, lang;
      boolean deprecated;
      MarcLanguageCode theLanguage;

      children = nodes.item( i ).getChildNodes();
      code = "";
      lang = "";
      deprecated = false;
      theLanguage = new MarcLanguageCode();

      for ( int j = 0; j < children.getLength(); j++ )
      {
        Node childNode;

        childNode = children.item( j );
        if ( childNode.getNodeName().equalsIgnoreCase( "name" ) )
          lang = childNode.getTextContent();
        if ( childNode.getNodeName().equalsIgnoreCase( "code" ) )
        {
          code = childNode.getTextContent();
          if ( childNode.hasAttributes() )
          {
            NamedNodeMap atts;

            atts = childNode.getAttributes();
            for ( int k = 0; k < atts.getLength(); k++ )
            {
              if ( atts.item( k ).getNodeName().equalsIgnoreCase( "status" ) && 
                   atts.item( k ).getNodeValue().equalsIgnoreCase( "obsolete" ) )
              {
                deprecated = true;
              }
            }
          }
        }
      }
      theLanguage.setLangName(lang);
      theLanguage.setLangCode(code);
      theLanguage.setDeprecated(deprecated);
      languages.add(theLanguage);
    }

    return languages;
  }
}
