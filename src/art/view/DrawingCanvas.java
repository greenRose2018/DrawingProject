package art.view;

import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import art.controller.ArtController;

public class DrawingCanvas extends JPanel
{
	private ArrayList<Polygon> triangleList;
	private ArrayList<Polygon> polygonList;
	private ArrayList<Ellipse2D> ellipseList;
	private ArrayList<Rectangle> rectangleList;
	private ArtController app;
	private int previousX;
	private int previousY;
	
	private BufferedImage canvasImage;
	
	public DrawingCanvas(ArtController app)
	{
		this.app = app;
		
		previousX = Integer.MIN_VALUE;
		previousY = Integer.MIN_VALUE;
		triangleList = new ArrayList<Polygon>();
		polygonList = new ArrayList<Polygon>();
		ellipseList = new ArrayList<Ellipse2D>();
		rectangleList = new ArrayList<Rectangle>();
		
		canvasImage = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
		this.setMinimumSize(new Dimension(600,600));
		this.setPreferredSize(new Dimension(600,600));
		this.setMaximumSize(getPreferredSize());
	}
	
	private void updateImage()
	{
		Graphics2D currentGraphics = (Graphics2D) canvasImage.getGraphics();
		
		for(Ellipse2D current : ellipseList)
		{
			currentGraphics.setColor(randomColor());
			currentGraphics.setStroke(new BasicStroke(2));
			currentGraphics.fill(current);
			currentGraphics.setColor(randomColor());
			currentGraphics.draw(current);
		}
		
		for(Polygon currentTri : triangleList)
		{
			currentGraphics.setColor(randomColor());
			currentGraphics.fill(currentTri);
		}
		
		for(Polygon currentPoly : polygonList)
		{
			currentGraphics.setColor(randomColor());
			currentGraphics.setStroke(new BasicStroke(4));
			currentGraphics.draw(currentPoly);
		}
		
		for(Rectangle currentRect : rectangleList)
		{
			currentGraphics.setColor(randomColor());
			currentGraphics.fill(currentRect);
		}
		//you never call paint component only repaint.
		//because paint component uses up to much memory it is better that the operating 
		//system handles it
		//the memory from the painting is dispose of with the code below
		
		currentGraphics.dispose();
		repaint();
	}
	
	public void addShape(Shape current)
	{
		if(current instanceof Polygon)
		{
			if(((Polygon)current).xpoints.length == 3)
			{
				triangleList.add((Polygon)current);
			}
			else
			{
				polygonList.add((Polygon)current);
			}
		}
		else if(current instanceof Ellipse2D)
		{
			ellipseList.add((Ellipse2D)current);
		}
		else 
		{
			rectangleList.add((Rectangle) current);
		}
		updateImage();
	}
	
	public void clear()
	{
		canvasImage = new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB);
		ellipseList.clear();
		triangleList.clear();
		polygonList.clear();
		rectangleList.clear();
		updateImage();
	}
	
	public void save()
	{
		try
		{
			JFileChooser saveDialog = new JFileChooser();
			saveDialog.showSaveDialog(app.getFrame());
			String savePath = saveDialog.getSelectedFile().getPath();
			ImageIO.write(canvasImage, "PNG", new File(savePath));
		}
		catch(IOException error)
		{
			app.handleErrors(error);
		}
	}
	
	public void changeBackground()
	{
		Graphics2D current = canvasImage.createGraphics();
		current.setPaint(randomColor());
		current.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());
		updateImage();
	}
	
	public void drawOnCanvas(int xPosition, int yPosition)
	{
		Graphics2D current  = canvasImage.createGraphics();
		current.setPaint(Color.BLACK);
		current.setStroke(new BasicStroke(3));
		
		if(previousX == Integer.MIN_VALUE)
		{
			current.drawLine(xPosition,yPosition,xPosition,yPosition);
			
		}
		else
		{
			current.drawLine(previousX,previousY,xPosition,yPosition);
		}
		
		previousX = xPosition;
		previousY = yPosition;
		updateImage();
		
	}
	
	public void resetLine()
	{
		previousX = Integer.MIN_VALUE;
		previousY = Integer.MIN_VALUE;
	}
	
	private Color randomColor()
	{
		int red = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int alpha = (int) (Math.random() * 256);
		
		return new Color(red, green, blue, alpha);
	}
	
	//this method is required
	@Override
	protected void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		graphics.drawImage(canvasImage, 0, 0, null);
				
	}
}
