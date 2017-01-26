package Car;

public abstract class AbstractCar {
	
	private String name;
	private double positionX;
	private double positionY;
	private int speedfaktor;
	private boolean isDriving;
	private boolean finish;
	private String time;
	
	public double getPositionX() {
		return positionX;
	}
	
	public void setPositionX(double position) {
		this.positionX = position;
	}
	
	public double getPositionY() {
		return positionY;
	}
	
	public void setPositionY(double position) {
		this.positionY = position;
	}	

	public int getSpeed() {
		return speedfaktor;
	}
	public void setSpeed(int speed) {
		this.speedfaktor = speed;
	}

	public boolean isDrive() {
		return isDriving;
	}

	public void setDrive(boolean drive) {
		this.isDriving = drive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
