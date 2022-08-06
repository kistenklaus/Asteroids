package engine.math;

public class Vector2D extends Vector{
	
	public Vector2D(float comX, float comY) {
		super(new float[] {comX, comY});
	}
	public Vector2D(double comX, double comY) {
		super(new float[] {(float)comX, (float)comY});
	}
	public Vector2D(Vector2D vec2D) {
		super(vec2D.getComp().clone());
	}
	
	public Vector2D() {
		super(new float[] {0, 0});
	}
	
	
	
	public static Vector2D MULT(Vector2D v1, float scalar) {
		return new Vector2D(v1.getX() * scalar,
				v1.getY() * scalar);
	}
	public static Vector2D MULT(Vector2D v1, double scalar) {
		return new Vector2D(v1.getX() * scalar,
				v1.getY() * scalar);
	}
	
}
