package worms.model;



public class Food {

	private double X, Y;
	
	private static final double foodRadius = 0.2;
	private static final double foodIncrease = 1.1;
	
	public Food(double Xcoordinaat, double Ycoordinaat){
		
		this.X = Xcoordinaat;
		this.Y = Ycoordinaat;
		
	}
	
	public double getX(){
		
		return X;
	}
	public double getY(){
		
		return Y;
		
	}
	public double getFoodIncrease(){
		
		return foodIncrease;
		
	}
	public double getFoodRadius(){
		
		return foodRadius;
		
	}
}
