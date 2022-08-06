package engine.math;

public abstract class Vector {
	
	protected float[] comp;
	
	public Vector(float[] comp) {
		this.comp = comp;
	}
	
	public void add(Vector v2) {
		throwCompatible(this, v2);
		for(int i = 0; i < comp.length; i++) {
			comp[i] += v2.comp[i];
		}
	}
	public void sub(Vector v2) {
		throwCompatible(this, v2);
		for(int i = 0; i < comp.length; i++) {
			comp[i] -= v2.comp[i];
		}
	}
	
	public void mult(float scalar) {
		for(int i = 0; i < comp.length; i++) {
			comp[i] *= scalar;
		}
	}
	
	public void mult(double scalar) {
		this.mult((float) scalar);
	}
	
	public void div(float scalar) {
		this.mult(1/scalar);
	}
	
	public void div(double scalar) {
		this.div((float) scalar);
	}
	
	public void limit(float limit) {
		float lenghtSq = 0;
		for(int i = 0; i < comp.length; i++) {
			lenghtSq += comp[i] * comp[i];
		}
		if(lenghtSq > limit * limit) {
			this.mult(1/Math.sqrt(lenghtSq) * limit);
		}
	}
	
	public void normaize() {
		float lenght = 0;
		for(int i = 0; i < comp.length; i++) {
			lenght += comp[i] * comp[i];
		}
		lenght = (float)Math.sqrt(lenght);
		this.div(lenght);
	}
	
	private static void throwCompatible(Vector v1, Vector v2) {
		if(v2.comp.length != v1.comp.length) {
			throw new IllegalArgumentException("the Vectors are not Compatible");
		}
	}
	
	public float getX() {
		return comp[0];
	}
	public float getY() {
		return comp[1];
	}
	public float[] getComp() {
		return comp;
	}
	
	
	
	
	
	
	
	
	
}
