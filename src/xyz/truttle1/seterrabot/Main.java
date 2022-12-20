package xyz.truttle1.seterrabot;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main 
{
	public static final int SCROLL_X = 1490;
	public static final int SCROLL_Y = 940;
	public static final int MASK = InputEvent.BUTTON1_DOWN_MASK;
	public static final boolean RESET = false;
	
	public static ArrayList<State> states;
	public static final int SENSITIVITY = 10;

	public static final int X = 1390;
	public static final int Y = 102;
	public static final int W = 100;
	public static final int H = 58;
	
	public static void main(String[] args) throws AWTException, InterruptedException
	{
		Robot robot = new Robot();

		Window window = new Window();

		states = new ArrayList<State>();
		if(RESET)
		{

			robot.mouseMove(SCROLL_X, SCROLL_Y);

			for(int i = 0; i < 7; i++)
			{
				Thread.sleep(100);
				robot.mousePress(MASK);
				robot.mouseRelease(MASK);
			}
			robot.mouseMove(SCROLL_X, 90);
			
			/*
			for(int i = 0; i < 154; i++)
			{
				Thread.sleep(100);
				robot.mousePress(MASK);
				robot.mouseRelease(MASK);
			}
			*/
		}
		Thread.sleep(1000);
		BufferedImage image = new Robot().createScreenCapture(
				new Rectangle(X, Y, W, H));

		window.createFrame();
		window.drawImage(image);
		while(true)
		{
			Thread.sleep(50);
			BufferedImage new_image = new Robot().createScreenCapture(
					new Rectangle(X, Y, W, H));

			if(compareImages(new_image, image) > SENSITIVITY)
			{
				PointerInfo a = MouseInfo.getPointerInfo();
				Point b = a.getLocation();
				int x = (int) b.getX();
				int y = (int) b.getY();
				System.out.println("STATE CLICKED AT " + x + "," + y);
				robot.mouseMove(x, y);
				
				State state = new State();
				state.x = x;
				state.y = y;
				state.image = image;
				
				states.add(state);
				window.drawImage2(image);
				window.repaint();
				image = new_image;
				window.drawImage(image);
				window.repaint();
				
			}
			
			if(window.getPlaying())
			{
				play(window);
			}
		}
		
	}
	
	private static void play(Window window) throws AWTException, InterruptedException
	{
		int x = 0;
		BufferedImage flag = new Robot().createScreenCapture(
				new Rectangle(X, Y, W, H));
		Robot robot = new Robot();
		while(x+2 <= states.size())
		{
			int bestStateIndex = getBestState(flag);
			
			System.out.println("STATE: " + bestStateIndex);

			robot.mouseMove(states.get(bestStateIndex).x, states.get(bestStateIndex).y);
			
			//Thread.sleep(10);
			robot.mousePress(MASK);
			robot.mouseRelease(MASK);
			//Thread.sleep(10);
			BufferedImage newFlag = new Robot().createScreenCapture(
					new Rectangle(X, Y, W, H));

			window.drawImage(newFlag);
			window.repaint();
			//Thread.sleep(10);
			
			if(compareImages(flag, newFlag) > SENSITIVITY)
			{
				x++;
				flag = newFlag;
				window.drawImage2(flag);
				window.repaint();
			}
		}
	}
	
	private static int getBestState(BufferedImage image)
	{
		float lowest = 9999999;
		int best = 0;
		for(int i = 0; i < states.size(); i++)
		{
			float a = compareImages(states.get(i).image, image);
			if(a < lowest)
			{
				best = i;
				lowest = a;
			}
		}
		return best;
	}
	
	private static float compareImages(BufferedImage i1, BufferedImage i2)
	{
		int diff = 0;
		for(int x = 0; x < i1.getWidth(); x++)
		{
			for(int y = 0; y < i1.getHeight(); y++)
			{
				int color1 = i1.getRGB(x, y);
				int red1 =   (color1 & 0x00ff0000) >> 16;
		        int green1 = (color1 & 0x0000ff00) >> 8;
		        int blue1 =   color1 & 0x000000ff;
		        
				int color2 = i2.getRGB(x, y);
				int red2 =   (color2 & 0x00ff0000) >> 16;
		        int green2 = (color2 & 0x0000ff00) >> 8;
		        int blue2 =   color2 & 0x000000ff;

		        diff += Math.abs(red1-red2);
		        diff += Math.abs(green1-green2);
		        diff += Math.abs(blue1-blue2);
		        
		        
			}
		}

		return ((float)(diff)) / ( i1.getWidth() * i1.getHeight() * 3);
	}
}
