package com.arisux.srgsrc;

import javax.swing.JFrame;

public class Application
{
	private static JFrame mainInterface;
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		System.out.println("Application Started");
		
		mainInterface = new FrameMain();
	}
	
	public static JFrame getMainInterface()
	{
		return mainInterface;
	}
}
