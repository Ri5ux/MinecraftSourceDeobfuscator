package com.arisux.mappingpatcher;

public class Loader
{
	private FrameMain mainInterface;
	private Patcher remapper;
	private boolean auto = false;
	public boolean nogui = false;
	private String mappingsLocation = "mappings\\";
	private String srcLocation = "src\\";
	private String outputLocation = "output\\";

	public Loader(String[] args) throws Exception
	{
		MappingPatcher.setLoader(this);
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i];
			
			if (arg.equals("-help"))
			{
				this.sendToConsole("MCP Mapping Patcher");
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
					this.mappingsLocation = args[i + 1];
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
					this.sendToConsole("Running in silent/console mode.");
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
			this.mainInterface = new FrameMain();
		}

		if (auto)
		{
			this.run();
		}
	}

	public void run()
	{
		if (this.getMappingsLocation() != null && this.getSrcLocation() != null)
		{
			this.sendToConsole("Preparing to apply mappings to '" + this.getSrcLocation() + "' from '" + this.getMappingsLocation() + "'");
			(remapper = new Patcher(this)).start();
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

	public Patcher getRemapper()
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

	public String getMappingsLocation()
	{
		return mappingsLocation;
	}

	public void setSrcLocation(String srcLocation)
	{
		this.srcLocation = srcLocation;
	}

	public void setMappingsLocation(String srgLocation)
	{
		this.mappingsLocation = srgLocation;
	}

	public String getOutputLocation()
	{
		return outputLocation;
	}

	public void setOutputLocation(String outputLocation)
	{
		this.outputLocation = outputLocation;
	}
}
