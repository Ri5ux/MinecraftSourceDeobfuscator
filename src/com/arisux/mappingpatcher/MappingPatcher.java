package com.arisux.mappingpatcher;

public class MappingPatcher
{
	public static final String WEBSITE = "http://arisux.com/";
	public static final String WATERMARK = "Copyright \u00a9 2016 Arisux Technology Group - " + WEBSITE;
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
		MappingPatcher.loader = loader;
	}
}