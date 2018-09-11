import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

public class SnakeBody extends Rectangle{

	boolean turnUp = false;
	boolean turnDown = false;
	boolean turnLeft = false;
	boolean turnRight = false;

	public SnakeBody(int posX, int posY, int length, int width) {
		super(posX, posY, length, width);
	}

	public void updatePos(int posX, int posY) {
		super.setLocation(posX, posY);
	}
	
	public void fixTurnUP(boolean turn) {
		turnUp = turn;
	}
	
	public boolean getTurnUP() {
		return turnUp;
	}
	
	public void fixTurnDown(boolean turn) {
		turnDown = turn;
	}
	
	public boolean getTurnDown() {
		return turnDown;
	}
	
	public void fixTurnLeft(boolean turn) {
		turnLeft = turn;
	}
	
	public boolean getTurnLeft() {
		return turnLeft;
	}
	
	public void fixTurnRight(boolean turn) {
		turnRight = turn;
	}
	
	public boolean getTurnRight() {
		return turnRight;
	}
}
