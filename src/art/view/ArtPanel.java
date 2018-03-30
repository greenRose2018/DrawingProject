package art.view;

import java.awt.*;
import art.controller.ArtController;
import javax.swing.*;

public class ArtPanel extends JPanel
{
	public ArtPanel()
	{
		super();
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	private void setupPanel()
	{
		
	}
	private void setupListeners()
	{
		
	}
	private void setupLayout()
	{
		
	}
	private boolean coinFlip()
	{
		return (int) (Math.random() * 2) == 0;
	}
	
	private Polygon createPolygon(int sides)
	{
		Polygon currentShape = new Polygon();
		
		int originX = (int) (Math.random() * 600);
		int originY = (int) (Math.random() * 600);
		
		for ( int index = 0; index < sides; index++)
		{
			int minus = coinFlip() ? -1 : 1;
			int shiftX = (int) (Math.random() * currentScale) * minus;
			minus = coinFlip() ? -1 : 1;
			int shiftY = (int) (Math.random() * currentScale) * minus;
			currentShape.addPoint(originX + shiftX, originY + shiftY);
		}
		
		return currentShape;
	}
	
	private Rectangle createRectangle()
	{
		Rectangle currentRectangle;
		
		int cornerX = (int) (Math.random() * 600);
		int cornerY = (int) (Math.random() * 600);
		int width = (int) (Math.random() * currentScale) + 1;
		
		return rec;
	}
}
