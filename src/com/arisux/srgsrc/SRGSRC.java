package com.arisux.srgsrc;

public class SRGSRC
{
	public static void main(String[] args)
	{
		try
		{
			Loader loader = new Loader(args);
		} catch (Exception e)
		{
			System.out.println("A fatal error occurred, cannot continue: " + e);
			e.printStackTrace();
		}
	}
}