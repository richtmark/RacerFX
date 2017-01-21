package Car;

public abstract class AbstractCar {
	
	private String name;
	private double position;
	private double velocity;
	private int speed;
	private boolean drive;
	
	public double getPosition() {
		return position;
	}
	
	public void setPosition(double position) {
		this.position = position;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isDrive() {
		return drive;
	}

	public void setDrive(boolean drive) {
		this.drive = drive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
