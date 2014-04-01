package com.arisux.srgsrc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FrameMain extends JFrame
{
	private static final long serialVersionUID = 8882101553389545664L;
	
    private JButton srgInputButton, srcInputButton, processButton;
    private JTextField srgInputField, srcInputField;
    private JProgressBar progressBar;
    private JLabel labelPercentage, labelCopyright, labelSrc, labelSrg;
    private JTextArea consoleOutput;
    private JScrollPane scrollConsolePane;
    private String srgLocation, srcLocation;
    private Processor processor;
    private Dimension dimensions = new Dimension(860, 480);

    public FrameMain()
    {
    	super("SRGSRC - Minecraft Coder Pack Tool (Convert Java Sources from SRG to SRC)");
    	
        this.setSize(dimensions);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        {
	        srgInputButton = new JButton("...");
	        srgInputButton.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	            	JFileChooser openFileSRG = new JFileChooser();
	            	
	            	openFileSRG.setCurrentDirectory(new java.io.File("."));
	            	openFileSRG.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            	openFileSRG.setAcceptAllFileFilterUsed(false);
	            	
	            	openFileSRG.showOpenDialog(null);
	            	
	            	srgLocation = openFileSRG.getSelectedFile().getPath().toString();
	            	
	            	srgInputField.setText(srgLocation);
	            }
	        });
	        
	        srgInputButton.setSize(40, 25);
	        srgInputButton.setLocation(this.dimensions.width - 55, 10);
        }
        
        {
	        srcInputButton = new JButton("...");
	        srcInputButton.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	            	JFileChooser openFileSRC = new JFileChooser();
	            	
	            	openFileSRC.setCurrentDirectory(new java.io.File("."));
	            	openFileSRC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            	openFileSRC.setAcceptAllFileFilterUsed(false);
	            	
	            	openFileSRC.showOpenDialog(null);
	            	
	            	srcLocation = openFileSRC.getSelectedFile().getPath().toString();
	            	
	            	srcInputField.setText(srcLocation);
	            }
	        });
	        
	        srcInputButton.setSize(40, 25);
	        srcInputButton.setLocation(this.dimensions.width - 55, 45);
        }
        
        {
        	processButton = new JButton("Update");
        	processButton.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	            	if (srgLocation != null && srcLocation != null)
	            	{
	            		System.out.println("Ready to process SRG and SRC: " + srgLocation + " - " + srcLocation);
						(processor = new Processor(srgLocation, srcLocation)).start();
	            	}
	            }
	        });
	        
        	processButton.setSize(120, 25);
        	processButton.setLocation(this.dimensions.width - 135, this.dimensions.height - 60);
        }
        
        {
	        srgInputField = new JTextField("");
	        srgInputField.setSize(this.dimensions.width - 100, 26);
	        srgInputField.setLocation(45, 10);
	        
	        srgInputField.getDocument().addDocumentListener(new DocumentListener()
	        {
	            @Override
	            public void insertUpdate(DocumentEvent de)
	            {
	            	srgLocation = srgInputField.getText();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent de)
	            {
	            	srgLocation = srgInputField.getText();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent de)
	            {
	            	srgLocation = srgInputField.getText();
	            }
	        });
        }
        
        {
	        srcInputField = new JTextField("");
	        srcInputField.setSize(this.dimensions.width - 100, 26);
	        srcInputField.setLocation(45, 45);
	        
	        srcInputField.getDocument().addDocumentListener(new DocumentListener()
	        {
	            @Override
	            public void insertUpdate(DocumentEvent de)
	            {
	            	srcLocation = srcInputField.getText();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent de)
	            {
	            	srcLocation = srcInputField.getText();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent de)
	            {
	            	srcLocation = srcInputField.getText();
	            }
	        });
        }

        {
        	labelSrg = new JLabel("SRG");
        	labelSrg.setText("SRG");
        	labelSrg.setSize(this.dimensions.width - 25, 26);
        	labelSrg.setLocation(10, 10);
        }
        
        {
        	labelSrc = new JLabel("SRC");
        	labelSrc.setText("SRC");
        	labelSrc.setSize(this.dimensions.width - 25, 26);
        	labelSrc.setLocation(10, 45);
        }
        
        {
        	progressBar = new JProgressBar();
        	progressBar.setMinimum(0);
        	progressBar.setMaximum(100);
        	progressBar.setSize(this.dimensions.width - 60, 26);
        	progressBar.setLocation(45, 80);
        }
        
        {
        	labelPercentage = new JLabel("PERCENT");
        	labelPercentage.setText(progressBar.getPercentComplete() + "%");
        	labelPercentage.setSize(this.dimensions.width - 25, 26);
        	labelPercentage.setLocation(10, 80);
        }
        
        {
        	labelCopyright = new JLabel("COPYRIGHT");
        	labelCopyright.setText("Copyright \u00a9 2014 Arisux - http://arisux.x10.mx/");
        	labelCopyright.setSize(this.dimensions.width - 25, 26);
        	labelCopyright.setLocation(10, this.dimensions.height - 62);
        }
        
        {
        	consoleOutput = new JTextArea();
        	consoleOutput.setSize(this.dimensions.width - 25, this.dimensions.height - 185);
        	consoleOutput.setLocation(10, 115);
        	consoleOutput.setEditable(false);
        	consoleOutput.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));

        	scrollConsolePane = new JScrollPane(consoleOutput);
        	scrollConsolePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        	scrollConsolePane.setSize(this.dimensions.width - 25, this.dimensions.height - 185);
        	scrollConsolePane.setLocation(10, 115);
        }
        
        this.add(srgInputButton);
        this.add(srcInputButton);
        this.add(srgInputField);
        this.add(srcInputField);
        this.add(labelSrg);
        this.add(labelSrc);
        this.add(processButton);
        this.add(progressBar);
        this.add(labelPercentage);
        this.add(labelCopyright);
        this.add(scrollConsolePane);

        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setResizable(false);
    }

	public void setCompletionPercent(int percent)
	{
		this.progressBar.setValue(percent);
		this.labelPercentage.setText((double)percent + "%");
	}
	
	public void sendToConsole(String str)
	{
		this.consoleOutput.append(str + "\n");
	}
}

