package com.arisux.mappingpatcher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import au.com.bytecode.opencsv.CSVReader;

public class Remapper extends Thread implements Runnable
{
	private String srg, src, contents;
	private boolean processingFields;
	private Loader loader;

	public Remapper(Loader loader, String srg, String src)
	{
		this.loader = loader;
		this.setSrgDir(srg);
		this.setSrcDir(src);
	}

	@Override
	public void run()
	{
		if (!this.loader.nogui) this.loader.getMainInterface().getConsoleOutput().setText(null);
		(new File(getSrcOutDir())).mkdirs();

		try
		{
			processSources();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean processSources() throws IOException
	{
		File srcDir = new File(getSrcDir());
		File csvDirFields = new File(getSrgDir() + "\\fields.csv");
		File csvDirMethods = new File(getSrgDir() + "\\methods.csv");

		if (csvDirFields.exists())
		{
			loader.sendToConsole("Loading mapped fields from '" + csvDirFields + "'");
		} else
		{
			loader.sendToConsole("Could not load mapped fields from '" + csvDirFields + "'. File does not exist.");
			return false;
		}

		if (csvDirMethods.exists())
		{
			loader.sendToConsole("Loading mapped methods from '" + csvDirMethods + "'");
		} else
		{
			loader.sendToConsole("Could not load mapped methods from '" + csvDirMethods + "'. File does not exist.");
			return false;
		}

		if (!srcDir.exists())
		{
			loader.sendToConsole("Could not sources from '" + srcDir + "'. Directory does not exist.");
			return false;
		}

		List<File> sources = getJavaSourcesInDir(getSrcDir());

		if (sources.size() <= 0)
		{
			loader.sendToConsole("No java sources found in '" + srcDir + "'. Cannot continue.");
			return false;
		}

		CSVReader srgReaderFields = new CSVReader(new FileReader(csvDirFields));
		CSVReader srgReaderMethods = new CSVReader(new FileReader(csvDirMethods));

		int lineNum = 0;

		for (File file : sources)
		{
			lineNum++;

			loader.sendToConsole("[LOAD] " + file.getPath());
			loader.sendToConsole("[SAVE] " + (file.getPath().replace(getSrcDir(), getSrcOutDir())));

			contents = readFile(file.getPath(), Charset.defaultCharset());

			remapFields(file, srgReaderFields);
			remapMethods(file, srgReaderMethods);

			saveContentsToFile((file.getPath().replace(getSrcDir(), getSrcOutDir())), contents);

			final float percent = ((float) lineNum / (float) sources.size()) * 100F;
			loader.sendToConsole("[Progress] " + (double) percent + "%");

			if (!loader.nogui)
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						loader.getMainInterface().setCompletionPercent((int) percent);
					}
				});
			}
		}
		srgReaderFields.close();
		srgReaderMethods.close();

		return true;
	}

	public void remapFields(File source, CSVReader srgReader) throws IOException
	{
		List<String[]> fields = srgReader.readAll();

		for (String[] field : fields)
		{
			if (contents.contains(field[0]))
			{
				loader.sendToConsole("[" + source.getName() + "] Source contains old field SRG name '" + field[0] + "', should be '" + field[1] + "'");
				this.contents = contents.replaceAll(field[0], field[1]);
			}
		}
	}

	public void remapMethods(File source, CSVReader srgReader) throws IOException
	{
		List<String[]> methods = srgReader.readAll();

		int lineNum = 0;

		for (String[] method : methods)
		{
			lineNum++;

			if (contents.contains(method[0]))
			{
				loader.sendToConsole("[" + source.getName() + "] Source contains old method SRG name '" + method[0] + "', should be '" + method[1] + "'");
				this.contents = contents.replaceAll(method[0], method[1]);
			}
		}
	}

	public List getFilesInDirectory(String dir)
	{
		File[] srcList = (new File(dir)).listFiles();
		List<File> allFiles = new ArrayList<File>();

		if (srcList != null)
		{
			for (File file : srcList)
			{
				allFiles.add(file);

				if (file.isDirectory())
				{
					allFiles.addAll(getFilesInDirectory(file.getPath()));
				}
			}
		} else
		{
			loader.sendToConsole("Directory '" + dir + "' does not exist. Please provide a valid directory.");
			return null;
		}

		return allFiles;
	}

	public List<File> getJavaSourcesInDir(String dir)
	{
		final List<File> javaSources = new ArrayList<File>();

		for (File file : (List<File>) getFilesInDirectory(dir))
		{
			if (file.getName().endsWith(".java"))
			{
				javaSources.add(file);
			}
		}

		return javaSources;
	}

	public void saveContentsToFile(String filename, String contents)
	{
		try
		{
			File file = new File(filename);

			if (!file.exists())
			{
				if (!file.isDirectory())
				{
					File containingDir = new File(file.getPath().replace(file.getName(), ""));
					containingDir.mkdirs();
				}
				file.createNewFile();
			}

			PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8");
			writer.print(contents);
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String readFile(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public String getSrgDir()
	{
		return srg;
	}

	public void setSrgDir(String srg)
	{
		this.srg = srg;
	}

	public String getSrcDir()
	{
		return src;
	}

	public void setSrcDir(String src)
	{
		this.src = src;
	}

	public String getSrcOutDir()
	{
		return src + "\\..\\output";
	}
}
