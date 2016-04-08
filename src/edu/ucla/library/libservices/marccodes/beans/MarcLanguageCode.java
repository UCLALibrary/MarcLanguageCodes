package edu.ucla.library.libservices.marccodes.beans;

public class MarcLanguageCode
{
  private String langCode;
  private String langName;
  private boolean deprecated;

  public MarcLanguageCode()
  {
  }

  public void setLangCode( String langCode )
  {
    this.langCode = langCode;
  }

  public String getLangCode()
  {
    return langCode;
  }

  public void setLangName( String langName )
  {
    this.langName = langName;
  }

  public String getLangName()
  {
    return langName;
  }

  public void setDeprecated( boolean deprecated )
  {
    this.deprecated = deprecated;
  }

  public boolean isDeprecated()
  {
    return deprecated;
  }

  public String toString()
  {
    return "my code = " + getLangCode() + "\t\tmy name = " + 
      getLangName() + ( isDeprecated() ? "\ti am deprecated" : "" );
  }
}
