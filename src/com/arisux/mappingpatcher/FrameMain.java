package com.arisux.mappingpatcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FrameMain extends JFrame
{
	private static final long 		serialVersionUID 	= 1872799003418324788L;
	private JButton 				mappingInputButton;
	private JButton 				srcInputButton;
	private JButton 				outputInputButton;
	private JButton 				processButton;
	private JTextField 				mappingInputField;
	private JTextField 				srcInputField;
	private JTextField 				outputInputField;
	private JProgressBar 			progressBar;
	private JLabel 					labelPercentage;
	private JTextArea 				consoleOutput;
	private JScrollPane 			scrollConsolePane;
	private Dimension 				dimensions 			= new Dimension(860, 480);

	public FrameMain()
	{
		super("MCP Mapping Patcher");
		//this.setIconImage(new ImageIcon(this.getClass().getResource("/resources/icon.png")).getImage());
		
		this.setSize(dimensions);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel sectionNorth = new JPanel();
        this.getContentPane().add(sectionNorth, BorderLayout.NORTH);
        sectionNorth.setLayout(new BorderLayout());
        sectionNorth.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        sectionNorth.setBackground(Color.white);

	    JPanel sectionNorth1 = new JPanel();
	    sectionNorth.add(sectionNorth1, BorderLayout.NORTH);
	    sectionNorth1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 1));
	    sectionNorth1.setBackground(Color.white);
        
	    JPanel sectionNorth2 = new JPanel();
	    sectionNorth.add(sectionNorth2, BorderLayout.CENTER);
	    sectionNorth2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 1));
	    sectionNorth2.setBackground(Color.white);
	    
	    JPanel sectionNorth3 = new JPanel();
	    sectionNorth.add(sectionNorth3, BorderLayout.SOUTH);
	    sectionNorth3.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 1));
	    sectionNorth3.setBackground(Color.white);
        
	    JPanel sectionEast = new JPanel();
        this.getContentPane().add(sectionEast, BorderLayout.EAST);
        sectionEast.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
        sectionEast.setBackground(Color.white);
        
	    JPanel sectionSouth = new JPanel();
        this.getContentPane().add(sectionSouth, BorderLayout.SOUTH);
        sectionSouth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
        sectionSouth.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        sectionSouth.setBackground(Color.white);

	    JPanel sectionWest = new JPanel();
        this.getContentPane().add(sectionWest, BorderLayout.WEST);
        sectionWest.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
        sectionWest.setBackground(Color.white);
        
	    JPanel sectionCenter = new JPanel();
        this.getContentPane().add(sectionCenter, BorderLayout.CENTER);
        sectionCenter.setLayout(new BorderLayout());
        sectionCenter.setBackground(Color.white);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		{
			mappingInputButton = new JButton("...");
			mappingInputButton.setBackground(Color.white);
			mappingInputButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JFileChooser openFileSRG = new JFileChooser();

					openFileSRG.setCurrentDirectory(new java.io.File("."));
					openFileSRG.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					openFileSRG.setAcceptAllFileFilterUsed(false);

					openFileSRG.showOpenDialog(null);

					MappingPatcher.loader().setMappingsLocation(openFileSRG.getSelectedFile().getPath().toString());

					mappingInputField.setText(MappingPatcher.loader().getMappingsLocation());
				}
			});

			mappingInputButton.setSize(40, 25);
		}

		{
			srcInputButton = new JButton("...");
			srcInputButton.setBackground(Color.white);
			srcInputButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JFileChooser openFileSRC = new JFileChooser();

					openFileSRC.setCurrentDirectory(new java.io.File("."));
					openFileSRC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					openFileSRC.setAcceptAllFileFilterUsed(false);

					openFileSRC.showOpenDialog(null);

					MappingPatcher.loader().setSrcLocation(openFileSRC.getSelectedFile().getPath().toString());

					srcInputField.setText(MappingPatcher.loader().getSrcLocation());
				}
			});

			srcInputButton.setSize(40, 25);
		}

		{
			outputInputButton = new JButton("...");
			outputInputButton.setBackground(Color.white);
			outputInputButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JFileChooser openFileOutput = new JFileChooser();

					openFileOutput.setCurrentDirectory(new java.io.File("."));
					openFileOutput.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					openFileOutput.setAcceptAllFileFilterUsed(false);

					openFileOutput.showOpenDialog(null);

					MappingPatcher.loader().setOutputLocation(openFileOutput.getSelectedFile().getPath().toString());

					outputInputButton.setText(MappingPatcher.loader().getSrcLocation());
				}
			});

			outputInputButton.setSize(40, 25);
		}

		{
			processButton = new JButton("Run");
			processButton.setBackground(Color.white);
			processButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					MappingPatcher.loader().run();
				}
			});

			processButton.setSize(120, 25);
		}

		//Input Fields
		JLabel labelMappings = new JLabel("Mappings");
		{
			mappingInputField = new JTextField(MappingPatcher.loader().getMappingsLocation());
			mappingInputField.setPreferredSize(new Dimension(700, 20));
			mappingInputField.getDocument().addDocumentListener(new DocumentListener()
			{
				@Override
				public void insertUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setMappingsLocation(mappingInputField.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setMappingsLocation(mappingInputField.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setMappingsLocation(mappingInputField.getText());
				}
			});
		}

		JLabel labelSource = new JLabel("Sources");
		{
			srcInputField = new JTextField(MappingPatcher.loader().getSrcLocation());
			srcInputField.setPreferredSize(new Dimension(700, 20));

			srcInputField.getDocument().addDocumentListener(new DocumentListener()
			{
				@Override
				public void insertUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setSrcLocation(srcInputField.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setSrcLocation(srcInputField.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setSrcLocation(srcInputField.getText());
				}
			});
		}

		JLabel labelOutput = new JLabel("Output");
		{
			outputInputField = new JTextField(MappingPatcher.loader().getSrcLocation());
			outputInputField.setPreferredSize(new Dimension(700, 20));
			outputInputField.setText(MappingPatcher.loader().getOutputLocation());

			outputInputField.getDocument().addDocumentListener(new DocumentListener()
			{
				@Override
				public void insertUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setSrcLocation(outputInputField.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setSrcLocation(outputInputField.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent de)
				{
					MappingPatcher.loader().setSrcLocation(outputInputField.getText());
				}
			});
		}

		{
			progressBar = new JProgressBar();
			progressBar.setMinimum(0);
			progressBar.setMaximum(100);
			progressBar.setSize(this.dimensions.width - 60, 20);
		}

		{
			labelPercentage = new JLabel("PERCENT");
			labelPercentage.setText(progressBar.getPercentComplete() + "%");
			labelPercentage.setSize(this.dimensions.width - 25, 20);
		}

		{
			JLabel labelCopyright = new JLabel("WATERMARK");
			labelCopyright.setText(MappingPatcher.WATERMARK);
			labelCopyright.setSize(this.dimensions.width - 25, 20);
		}

		{
			setConsoleOutput(new JTextArea());
			getConsoleOutput().setEditable(false);
			getConsoleOutput().setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
			getConsoleOutput().setAutoscrolls(true);
			getConsoleOutput().setBackground(Color.white);
			
			scrollConsolePane = new JScrollPane(getConsoleOutput());
			scrollConsolePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollConsolePane.setSize(this.dimensions.width - 25, this.dimensions.height - 185);
			scrollConsolePane.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
			scrollConsolePane.setBackground(Color.white);
		}

		sectionNorth1.add(labelMappings);
		sectionNorth1.add(mappingInputField);
		sectionNorth1.add(mappingInputButton);
		
		sectionNorth2.add(labelSource);
		sectionNorth2.add(srcInputField);
		sectionNorth2.add(srcInputButton);
		
		sectionNorth3.add(labelOutput);
		sectionNorth3.add(outputInputField);
		sectionNorth3.add(outputInputButton);
		
		sectionSouth.add(processButton);
		sectionSouth.add(labelPercentage);
		sectionSouth.add(progressBar);
//		this.add(labelCopyright);
		sectionCenter.add(scrollConsolePane);

		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	public void setCompletionPercent(int percent)
	{
		this.progressBar.setValue(percent);
		this.labelPercentage.setText((double) percent + "%");
	}

	public JTextArea getConsoleOutput()
	{
		return consoleOutput;
	}

	public void setConsoleOutput(JTextArea consoleOutput)
	{
		this.consoleOutput = consoleOutput;
	}
}
