package com.arisux.srgsrc;

public class Loader
{
	private FrameMain mainInterface;
	private Remapper remapper;
	private boolean auto = false;
	public boolean nogui = false;
	private String srgLocation = ".\\srg\\", srcLocation = ".\\src\\";

	public Loader(String[] args) throws Exception
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i];
			
			if (arg.equals("-help"))
			{
				this.sendToConsole("SRGSRC Mod Development Tool - Copyright (C) 2014 Arisux");
				this.sendToConsole("SYNTAX: -a <SRG DIRECTORY> <SRC DIRECTORY> (Auto runs the tool with the provided directories)");
				this.sendToConsole("SYNTAX: -nogui (Runs the tool in console mode)");
				this.sendToConsole("SYNTAX: -help (Shows help)");
				System.exit(0);
			}

			if (arg.equals("-a"))
			{
				if (args.length >= 3)
				{
					this.auto = true;
					this.srgLocation = args[i + 1];
					this.srcLocation = args[i + 2];
				} else
				{
					this.sendToConsole("Not enough arguments provided. SYNTAX: -a <SRG DIRECTORY> <SRC DIRECTORY>");
				}
			}

			if (arg.equals("-nogui"))
			{
				if (auto)
				{
					nogui = true;
					this.sendToConsole("Running SRGSRC in silent/console mode.");
				} else
				{
					nogui = false;
					this.sendToConsole("You must use the -a argument to run this tool in console mode.");
				}
			}

			builder.append("[Index:" + i + "][" + arg + "] ");
		}

		if (args.length > 0)
		{
			this.sendToConsole("Provided Arguments: " + builder.toString());
		}

		if (!nogui)
		{
			this.mainInterface = new FrameMain(this);
		}

		if (auto)
		{
			this.run();
		}
	}

	public void run()
	{
		if (this.getSrgLocation() != null && this.getSrcLocation() != null)
		{
			this.sendToConsole("Preparing to apply mappings to '" + this.getSrcLocation() + "' from '" + this.getSrgLocation() + "'");
			(remapper = new Remapper(this, this.getSrgLocation(), this.getSrcLocation())).start();
		}
	}

	public void sendToConsole(String str)
	{
		if (!nogui && mainInterface != null)
		{
			this.mainInterface.getConsoleOutput().append(str + "\n");
		}
		System.out.println(str);
	}

	public FrameMain getMainInterface()
	{
		return mainInterface;
	}

	public Remapper getRemapper()
	{
		return remapper;
	}

	public boolean isAuto()
	{
		return auto;
	}

	public String getSrcLocation()
	{
		return srcLocation;
	}

	public String getSrgLocation()
	{
		return srgLocation;
	}

	public void setSrcLocation(String srcLocation)
	{
		this.srcLocation = srcLocation;
	}

	public void setSrgLocation(String srgLocation)
	{
		this.srgLocation = srgLocation;
	}
}
