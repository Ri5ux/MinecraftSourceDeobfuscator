package com.arisux.srgsrc;


public class Loader
{
	private FrameMain mainInterface;
	private Remapper remapper;
	private boolean auto = false;
	boolean nogui = false;
	private String srgLocation = "", srcLocation = "";

	public Loader(String[] args)
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i];

			if (arg.equals("-a"))
			{
				auto = true;

				this.srcLocation = args[i + 1];
				this.srgLocation = args[i + 2];
			}

			if (arg.equals("-nogui"))
			{
				nogui = true;
				System.out.println("Running SRGSRC in silent/console mode.");
			}

			builder.append("[" + i + "][" + arg + "] ");
		}

		System.out.println("Provided Arguments: " + builder.toString());

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
		if (!nogui)
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
