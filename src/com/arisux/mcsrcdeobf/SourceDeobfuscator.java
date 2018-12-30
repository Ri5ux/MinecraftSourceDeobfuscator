package com.arisux.mcsrcdeobf;

public class SourceDeobfuscator
{
	public static final String WEBSITE = "http://dev.arisux.com/";
	public static final String WATERMARK = "Copyright \u00a9 2018-2019 ASX - " + WEBSITE;
	private static Loader loader;
	
	public static void main(String[] args)
	{
		try
		{
			new Loader(args);
		} catch (Exception e)
		{
			System.out.println("A fatal error occurred, cannot continue: " + e);
			e.printStackTrace();
		}
	}
	
	public static Loader loader()
	{
		return loader;
	}
	
	public static void setLoader(Loader loader)
	{
	    SourceDeobfuscator.loader = loader;
	}
}