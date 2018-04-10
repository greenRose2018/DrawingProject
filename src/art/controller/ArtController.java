package art.controller;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import art.view.*;

public class ArtController
{
	private ArtFrame artFrame;

	public ArtController()
	{
		artFrame = new ArtFrame(this);
	}
	
	public void Start()
	{
		JOptionPane.showMessageDialog(artFrame, "Welcome to art!");
	}
	

	public JFrame getFrame() 
	{
		return artFrame;
	}

	public void handleErrors(IOException error) 
	{
		System.out.println(error.getMessage());
	}
}
