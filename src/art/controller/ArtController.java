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
		artFrame = new ArtFrame();
	}
	
	public void Start()
	{
		
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
