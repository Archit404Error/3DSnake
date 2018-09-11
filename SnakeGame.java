import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

//Created by Archit
public class SnakeGame {
	static int highscore = 0;
	public JFrame frame = new JFrame("Snake!");
	private Movement m;
	public int[] pY = { 0 };
	public int[] pX = { 0 };

	public SnakeGame(boolean execute) {
		frame.setSize(1000, 800);
		if (execute == true) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			m = new Movement(pX, pY);

			frame.add(m);// adds thread to frame
			frame.setVisible(true);
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {

						pX[0] = 1;
						pY[0] = 0;

					} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {

						pX[0] = -1;
						pY[0] = 0;

					} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {

						pY[0] = 1;
						pX[0] = 0;
					} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {

						pY[0] = -1;
						pX[0] = 0;
					}
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
				}
			});
		}
	}

	public int getFrameWidth() {
		return frame.getWidth();
	}

	public int getFrameHeight() {
		return frame.getHeight();
	}

	public static void main(String[] args) {
		SnakeGame a = new SnakeGame(true);
	}
}

class Movement extends JComponent {// thread class
	Graphics2D graphic;
	int snakeX = 0, snakeY = 0;
	SnakeBody snake = new SnakeBody(snakeX, snakeY, 50, 50);
	Food f = new Food(true, 1000, 800);
	CopyOnWriteArrayList<SnakeBody> BodyParts = new CopyOnWriteArrayList<SnakeBody>();
	CopyOnWriteArrayList<String> Movements = new CopyOnWriteArrayList<String>();
	SnakeGame s = new SnakeGame(false);
	int points = 0;

	public CopyOnWriteArrayList<SnakeBody> getBody() {
		return BodyParts;
	}

	public Movement(int[] pX, int[] pY) {
		BodyParts.add((SnakeBody) snake);
		Thread animationThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					repaint();
					die();
					if (pX[0] == 1) {

						snake.updatePos((int) snake.getX() + 10, (int) snake.getY());
						for (int i = BodyParts.size() - 1; i > 0; i--) {
							SnakeBody currentPart = BodyParts.get(i);
							SnakeBody nextPart = BodyParts.get(i - 1);
							currentPart.updatePos((int) nextPart.getX(), (int) nextPart.getY());
						}
					} else if (pX[0] == -1) {

						snake.updatePos((int) snake.getX() - 10, (int) snake.getY());
						for (int i = BodyParts.size() - 1; i > 0; i--) {
							SnakeBody currentPart = BodyParts.get(i);
							SnakeBody nextPart = BodyParts.get(i - 1);
							currentPart.updatePos((int) nextPart.getX(), (int) nextPart.getY());
						}
					} else if (pY[0] == 1) {

						snake.updatePos((int) snake.getX(), (int) snake.getY() - 10);
						for (int i = BodyParts.size() - 1; i > 0; i--) {
							SnakeBody currentPart = BodyParts.get(i);
							SnakeBody nextPart = BodyParts.get(i - 1);
							currentPart.updatePos((int) nextPart.getX(), (int) nextPart.getY());
						}
					} else if (pY[0] == -1) {

						snake.updatePos((int) snake.getX(), (int) snake.getY() + 10);
						for (int i = BodyParts.size() - 1; i > 0; i--) {
							SnakeBody currentPart = BodyParts.get(i);
							SnakeBody nextPart = BodyParts.get(i - 1);
							currentPart.updatePos((int) nextPart.getX(), (int) nextPart.getY());
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		animationThread.start();
	}

	public SnakeBody getSnake() {
		return snake;
	}

	public void reset() {

	}

	public void die() {
		if ((int) snake.getX() >= s.getFrameWidth() || (int) snake.getY() >= s.getFrameHeight()
				|| (int) snake.getX() < 0 || (int) snake.getY() < 0) {
			System.exit(0);
		}
	}

	public void paintComponent(Graphics g) {
		graphic = (Graphics2D) g;
		graphic.setFont(new Font("Courier", Font.BOLD, 60));
		graphic.drawString("Points: " + points, 0, 100);
		for (int i = 0; i < BodyParts.size(); i++) {
			graphic.setColor(Color.black);
			graphic.fillRect((int) BodyParts.get(i).getX(), (int) BodyParts.get(i).getY(), 50, 50);
			graphic.setColor(Color.RED);
			graphic.fillRect((int) BodyParts.get(i).getX() + 15, (int) BodyParts.get(i).getY(), 50, 50);
		}
		f.generateFood();
		graphic.fillRect(f.getX(), f.getY(), 15, 15);

		if (f.spawned()) {
			f.needSpawn(false);
		}

		if (snake.contains(f.getX(), f.getY())) {
			points += 60;
			f.needSpawn(true);
			for (int i = 0; i <= 5; i++) {
				SnakeBody s = new SnakeBody((int) BodyParts.get(BodyParts.size() - 1).getX() - 200,
						(int) BodyParts.get(BodyParts.size() - 1).getY(), 50, 100);
				BodyParts.add(s);
			}
		}

	}
}
