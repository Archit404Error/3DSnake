import java.util.*;

public class Food {
	boolean spawn;
	int posX, posY, canvX, canvY;

	public Food(boolean needed, int canvX, int canvY) {
		spawn = needed;
		this.canvX = canvX;
		this.canvY = canvY;
	}

	public void generateFood() {
		if (spawn) {
			Random r = new Random();
			posX = r.nextInt(canvX - 50);
			posY = r.nextInt(canvY - 50);
		}
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}
	public void needSpawn(boolean needed) {
		spawn = needed;
	}
	public boolean spawned() {
		return spawn;
	}
}
