package com.arisux.srgsrc;

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

public class Processor extends Thread implements Runnable
{
	private String srg, src, contents;
	private boolean processingFields;
	private FrameMain mainInterface = (FrameMain) Application.getMainInterface();

	public Processor(String srg, String src)
	{
		this.setSrgDir(srg);
		this.setSrcDir(src);
	}

	@Override
	public void run()
	{
		(new File(getSrcOutDir())).mkdirs();
		
		try 
		{
			processSources();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void processSources() throws IOException
	{
		final List<File> sources = getFilesInDirectory(getSrcDir());
		final List<File> javaSources = new ArrayList<File>();
		
		for (File file: sources)
		{
			if (file.getName().endsWith(".java"))
			{
				javaSources.add(file);
			}
		}
		
		int lineNum = 0;
		
		for (File file : javaSources)
		{
			lineNum++;

			mainInterface.sendToConsole("[INPUT-DIR] " + file.getPath());
			mainInterface.sendToConsole("[OUTPUT-DIR] " + (file.getPath().replace(getSrcDir(), getSrcOutDir())));

			this.contents = readFile(file.getPath(), Charset.defaultCharset());
			
			processFields(file);
			processMethods(file);
			
			saveContentsToFile((file.getPath().replace(getSrcDir(), getSrcOutDir())), contents);
			
			final float percent = ((float)lineNum / (float)javaSources.size()) * 100F;
			System.out.println("[COMPLETION] " + (double)percent + "%");
				
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					mainInterface.setCompletionPercent((int)percent);
				}
			});
		}
	}
	
	public void processFields(File source) throws IOException
	{
		CSVReader srgReader = new CSVReader(new FileReader(getSrgDir() + "/fields.csv"));
		
		List<String[]> fields = srgReader.readAll();

		int lineNum = 0;
		
		for (String[] field : fields)
		{
			lineNum++;
			
			if (contents.contains(field[0]))
			{
				mainInterface.sendToConsole("[" + source.getName() + "] Source contains old field SRG name '" + field[0] + "', should be '" + field[1] + "'");
				this.contents = contents.replaceAll(field[0], field[1]);
			}
		}
	}
	
	public void processMethods(File source) throws IOException
	{
		CSVReader srgReader = new CSVReader(new FileReader(getSrgDir() + "/methods.csv"));
		
		List<String[]> methods = srgReader.readAll();

		int lineNum = 0;
		
		for (String[] method : methods)
		{
			lineNum++;
			
			if (contents.contains(method[0]))
			{
				mainInterface.sendToConsole("[" + source.getName() + "] Source contains old method SRG name '" + method[0] + "', should be '" + method[1] + "'");
				this.contents = contents.replaceAll(method[0], method[1]);
			}
		}
	}
	
	public List getFilesInDirectory(String dir)
	{
		File[] srcList = (new File(dir)).listFiles();
		List<File> allFiles = new ArrayList<File>();
		
		for (File file: srcList)
		{
			allFiles.add(file);
			
			if (file.isDirectory())
			{
				allFiles.addAll(getFilesInDirectory(file.getPath()));
			}
		}
		
		return allFiles;
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
