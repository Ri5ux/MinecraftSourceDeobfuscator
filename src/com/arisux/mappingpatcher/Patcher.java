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

import com.bytecode.opencsv.CSVReader;

public class Patcher extends Thread implements Runnable
{
	private Loader loader;

	public Patcher(Loader loader)
	{
		this.loader = loader;
	}

	@Override
	public void run()
	{
		if (!this.loader.nogui)
			this.loader.getMainInterface().getConsoleOutput().setText(null);

		(new File(loader.getOutputLocation())).mkdirs();

		try
		{
			processSources();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean processSources() throws IOException
	{
		File outputDir = new File(loader.getOutputLocation());
		File srcDir = new File(loader.getSrcLocation());
		File csvDirFields = new File(loader.getMappingsLocation() + "\\fields.csv");
		File csvDirParameters = new File(loader.getMappingsLocation() + "\\params.csv");
		File csvDirMethods = new File(loader.getMappingsLocation() + "\\methods.csv");

		mappingFileCheck(csvDirFields, MappingType.FIELD);
		mappingFileCheck(csvDirFields, MappingType.PARAMETER);
		mappingFileCheck(csvDirFields, MappingType.METHOD);

		if (!srcDir.exists())
		{
			loader.sendToConsole("Could not load sources from '" + srcDir + "'. Directory does not exist.");
			return false;
		}

		List<File> sources = getJavaSourcesInDir(loader.getSrcLocation());

		if (sources.size() <= 0)
		{
			loader.sendToConsole("No java sources found in '" + srcDir + "'. Cannot continue.");
			return false;
		}

		CSVReader srgReaderFields = new CSVReader(new FileReader(csvDirFields));
		CSVReader srgReaderParameters = new CSVReader(new FileReader(csvDirParameters));
		CSVReader srgReaderMethods = new CSVReader(new FileReader(csvDirMethods));

		List<String[]> mappingsFields = srgReaderFields.readAll();
		List<String[]> mappingsParameters = srgReaderParameters.readAll();
		List<String[]> mappingsMethods = srgReaderMethods.readAll();
		
		srgReaderFields.close();
		srgReaderParameters.close();
		srgReaderMethods.close();
		
		int lineNum = 0;

		for (File file : sources)
		{
			lineNum++;
			File fileSavePath = new File((file.getPath().replace(srcDir.getPath(), outputDir.getPath())));

			loader.sendToConsole("[LOAD] " + file.getAbsolutePath());

			String contents = readFile(file.getPath(), Charset.defaultCharset());

			contents = remap(file, mappingsFields, MappingType.FIELD, contents);
			contents = remap(file, mappingsParameters, MappingType.PARAMETER, contents);
			contents = remap(file, mappingsMethods, MappingType.METHOD, contents);

			saveContentsToFile(fileSavePath, contents);
			loader.sendToConsole("[SAVE] " + fileSavePath.getAbsolutePath());

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
		
		return true;
	}

	public boolean mappingFileCheck(File csvDir, MappingType type)
	{
		if (csvDir.exists())
		{
			loader.sendToConsole("Loading " + type.getName() + " mappings from '" + csvDir + "'");
			return true;
		}

		loader.sendToConsole("Could not load " + type.getName() + " mappings from '" + csvDir + "'. File does not exist.");
		return false;
	}

	public String remap(File source, List<String[]> mappings, MappingType type, String sourceContents) throws IOException
	{
		String newContents = sourceContents;
		
		for (String[] mapping : mappings)
		{
			String srgName = mapping[0];
			String mcpName = mapping[1];

			if (newContents.contains(srgName))
			{
				loader.sendToConsole("[" + source.getName() + "] Source class contains old " + type.getName() + " name '" + srgName + "', should be '" + mcpName + "'");
				newContents = newContents.replaceAll(srgName, mcpName);
			}
		}
		
		return newContents;
	}

	public List<File> getFilesInDirectory(String dir)
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
		}
		else
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

	public void saveContentsToFile(File file, String contents)
	{
		try
		{
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String readFile(String path, Charset encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
}
