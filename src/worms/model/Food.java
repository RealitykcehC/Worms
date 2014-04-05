package worms.model;



public class Food {

	private double X, Y;
	
	private static final double foodRadius = 0.2;
	private static final double foodIncrease = 1.1;
	
	public Food(double Xcoordinaat, double Ycoordinaat){
		
		this.X = Xcoordinaat;
		this.Y = Ycoordinaat;
		
	}
	
	
	/**
	 * getX() gives back the X coordinate of the object Food
	 * 
	 * @return this.x
	 */
	public double getX(){
		
		return this.X;
	}
	/**
	 * getY() gives back the Y coordinate of the object Food
	 * 
	 * @return this.y
	 */
	public double getY(){
		
		return this.Y;
		
	}
	/**
	 * getFoodIncrease () gives back the multiplier (1.1) of
	 * the increase (0.1) in the object worm's radius
	 * 
	 * @return foodIncrease (=1.1 == 10% increase)
	 */
	public double getFoodIncrease(){
		
		return foodIncrease;
		
	}
	/**
	 * getFoodRadius() gives back the radius of the object food
	 * 
	 * @return foodRadius = 0.2 meters
	 */
	public double getFoodRadius(){
		
		return foodRadius;
		
	}
}
