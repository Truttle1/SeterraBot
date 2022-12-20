package xyz.truttle1.seterrabot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel implements KeyListener
{
	private JFrame frame;
    // Not needed or useful! 
    //JPanel panel = new JPanel(); 
    private BufferedImage img;
    private BufferedImage img2;
    private int x;
	boolean painted = false;
	private JButton button;
	private boolean playing = false;
	public void createFrame() 
	{
	    frame = new JFrame("TEST");
	    frame.setPreferredSize(new Dimension(400, 250));
	    frame.setSize(400, 250);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //frame.getContentPane().add(main.panel);
	    frame.getContentPane().add(this);
	    
	    frame.addKeyListener(this);
	    
	    frame.setVisible(true);
	    
	}
	
	public void drawImage(BufferedImage image) {
	    this.img = image;
	    System.out.println(this.img);
	}
	
	public void drawImage2(BufferedImage image) {
	    this.img2 = image;
	    System.out.println(this.img2);
	}
	
	@Override
	public void repaint()
	{
		super.repaint();
		//System.out.println("repaint");
	}
	
    @Override
	public void paintComponent(Graphics g) 
    {
    	System.out.println("lol");
        super.paintComponent(g);
        g.clearRect(0, 0, 800, 450);
        g.setColor(Color.white);
        System.out.println(x);
        g.fillRect(0,0, 800, 450);
        // all JComponent instances are image observers
        //g.drawImage(img, 0, 0, null);
        g.drawImage(img, 0, 0, this);
        g.drawImage(img2, 0, 100, this);
        painted = true;
    }

	@Override
	public void keyTyped(KeyEvent e) 
	{
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_P)
		{
			this.playing = true;
		}
	}
	
	public boolean getPlaying()
	{
		if(this.playing)
		{
			this.playing = false;
			return true;
		}
		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
