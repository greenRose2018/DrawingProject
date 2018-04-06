package art.view;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import art.controller.ArtController;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Hashtable;

public class ArtPanel extends JPanel
{
	private final int MINIMUM_EDGE = 5;
	private final int MAXIMUM_EDGE = 20;
	private final int MINIMUM_SCALE = 20;
	private final int MAXIMUM_SCALE = 100;
	
	private ArtController app;
	
	private SpringLayout appLayout;
	private DrawingCanvas canvas;
	private JPanel buttonPanel;
	private JPanel sliderPanel;
	private JSlider scaleSlider;
	private JSlider edgeSlider;
	private JButton triangleBtn;
	private JButton rectangleBtn;
	private JButton ellipseBtn;
	private JButton polygonBtn;
	private JButton clearBtn;
	private JButton saveBtn;
	private JButton colorBtn;
	
	private int currentEdgeCount;
	private int currentScale;
	
	public ArtPanel(ArtController app)
	{
		super();
		
		this.app = app;
		appLayout = new SpringLayout();
		
		currentScale = MINIMUM_SCALE;
		currentEdgeCount = MINIMUM_EDGE;
		scaleSlider = new JSlider(MINIMUM_SCALE, MAXIMUM_SCALE);
		edgeSlider = new JSlider(MINIMUM_EDGE, MAXIMUM_EDGE);
		
		canvas = new DrawingCanvas(app);
		sliderPanel = new JPanel();
		buttonPanel = new JPanel(new GridLayout(0,1));
		
		triangleBtn = new JButton("add triangle");
		rectangleBtn = new JButton("add rectangle");
		ellipseBtn = new JButton("add ellipse");
		polygonBtn = new JButton("add polygon");
		clearBtn = new JButton("clear image");
		saveBtn = new JButton("save image");
		colorBtn = new JButton("change color");
		
		setupSliders();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	private void setupSliders()
	{
		Hashtable<Integer, JLabel> scaleLabels = new Hashtable<Integer, JLabel>();
		Hashtable<Integer, JLabel> edgeLabels = new Hashtable<Integer, JLabel>();
		
		scaleLabels.put(MINIMUM_SCALE, new JLabel("<HTML>Small<BR>Shape</HTML>"));
		scaleLabels.put((MAXIMUM_SCALE + MINIMUM_SCALE)/2, new JLabel("<HTML>Medium<BR>Shape</HTML>"));
		scaleLabels.put(MAXIMUM_SCALE, new JLabel("<HTML>Large<BR>Shape</HTML>"));
		
		edgeLabels.put(MINIMUM_EDGE,  new JLabel("Edges: " + MINIMUM_EDGE));
		edgeLabels.put(MAXIMUM_EDGE,  new JLabel("Edges: " + MAXIMUM_EDGE));
		
		scaleSlider.setLabelTable(scaleLabels);
		scaleSlider.setOrientation(JSlider.VERTICAL);
		scaleSlider.setSnapToTicks(true);
		scaleSlider.setMajorTickSpacing(10);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		
		edgeSlider.setLabelTable(edgeLabels);
		edgeSlider.setOrientation(JSlider.VERTICAL);
		edgeSlider.setSnapToTicks(true);
		edgeSlider.setMajorTickSpacing(3);
		edgeSlider.setMinorTickSpacing(1);
		edgeSlider.setPaintTicks(true);
		edgeSlider.setPaintLabels(true);
		
		
	}
	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(1024, 768));
		this.add(canvas);
		
		buttonPanel.setPreferredSize(new Dimension(200, 450));
		buttonPanel.add(triangleBtn);
		buttonPanel.add(rectangleBtn);
		buttonPanel.add(ellipseBtn);
		buttonPanel.add(polygonBtn);
		buttonPanel.add(clearBtn);
		buttonPanel.add(saveBtn);
		buttonPanel.add(colorBtn);
		
		sliderPanel.setPreferredSize(new Dimension(250, 450));
		sliderPanel.add(scaleSlider);
		sliderPanel.add(edgeSlider);
		
		this.add(buttonPanel);
		this.add(sliderPanel);
		
	}
	private void setupListeners()
	{
		edgeSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if(!edgeSlider.getValueIsAdjusting())
				{
					currentEdgeCount = edgeSlider.getValue();
				}
			}
		});
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
		
		if(coinFlip())
		{
			currentRectangle = new Rectangle(cornerX, cornerY, width, width);
		}
		else
		{
			int height = (int)(Math.random() * currentScale) + 1;
			currentRectangle = new Rectangle(cornerX, cornerY, width, height);
		}
		
		return currentRectangle;
	}
	private Ellipse2D createEllipse()
	{
		Ellipse2D ellipse = new Ellipse2D.Double();
		
		int cornerX = (int)(Math.random() * 600);
		int cornerY = (int)(Math.random() * 600);
		double width = Math.random() * currentScale + 1;
		if(coinFlip())
		{
			ellipse.setFrame(cornerX, cornerY, width, width);
		}
		else
		{
			double height = Math.random() * currentScale + 1;
			ellipse.setFrame(cornerX, cornerY, width, height);
		}
		
		return ellipse;
	}
}
