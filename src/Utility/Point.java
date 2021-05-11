package Utility;

public class Point {
	
	public int x;
	public int y;
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isEqual(Point other) {
		return this.x == other.x && this.y == other.y;
	}
	
	public boolean isNear(Point relativeTo, int range) {
		if (Math.abs(this.x - relativeTo.x) < range && Math.abs(this.y - relativeTo.y) < range) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
