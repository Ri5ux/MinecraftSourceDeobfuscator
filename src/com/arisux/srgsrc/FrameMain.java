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
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FrameMain extends JFrame
{
	private JButton srgInputButton, srcInputButton, processButton;
	private JTextField srgInputField, srcInputField;
	private JProgressBar progressBar;
	private JLabel labelPercentage, labelCopyright, labelSrc, labelSrg;
	private JTextArea consoleOutput;
	private JScrollPane scrollConsolePane;
	private Dimension dimensions = new Dimension(860, 480);
	private Loader loader;

	public FrameMain(final Loader loader)
	{
		super("SRGSRC");

		this.loader = loader;
		this.setSize(dimensions);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

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

					loader.setSrgLocation(openFileSRG.getSelectedFile().getPath().toString());

					srgInputField.setText(loader.getSrgLocation());
				}
			});

			srgInputButton.setSize(40, 28);
			srgInputButton.setLocation(this.dimensions.width - 55, 9);
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

					loader.setSrcLocation(openFileSRC.getSelectedFile().getPath().toString());

					srcInputField.setText(loader.getSrcLocation());
				}
			});

			srcInputButton.setSize(40, 28);
			srcInputButton.setLocation(this.dimensions.width - 55, 44);
		}

		{
			processButton = new JButton("Run");
			processButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					loader.run();
				}
			});

			processButton.setSize(120, 25);
			processButton.setLocation(this.dimensions.width - 134, this.dimensions.height - 62);
		}

		{
			srgInputField = new JTextField(loader.getSrgLocation());
			srgInputField.setSize(this.dimensions.width - 100, 26);
			srgInputField.setLocation(45, 10);

			srgInputField.getDocument().addDocumentListener(new DocumentListener()
			{
				@Override
				public void insertUpdate(DocumentEvent de)
				{
					loader.setSrgLocation(srgInputField.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent de)
				{
					loader.setSrgLocation(srgInputField.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent de)
				{
					loader.setSrgLocation(srgInputField.getText());
				}
			});
		}

		{
			srcInputField = new JTextField(loader.getSrcLocation());
			srcInputField.setSize(this.dimensions.width - 100, 26);
			srcInputField.setLocation(45, 45);

			srcInputField.getDocument().addDocumentListener(new DocumentListener()
			{
				@Override
				public void insertUpdate(DocumentEvent de)
				{
					loader.setSrcLocation(srcInputField.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent de)
				{
					loader.setSrcLocation(srcInputField.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent de)
				{
					loader.setSrcLocation(srcInputField.getText());
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
			setConsoleOutput(new JTextArea());
			getConsoleOutput().setSize(this.dimensions.width - 25, this.dimensions.height - 185);
			getConsoleOutput().setLocation(10, 115);
			getConsoleOutput().setEditable(false);
			getConsoleOutput().setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));

			scrollConsolePane = new JScrollPane(getConsoleOutput());
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
