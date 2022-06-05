public class Charge {
	private final int sig;
	private final double q, m;
	private double rx, ry, vx, vy, Fx, Fy;
	
	public Charge(double x0, double y0, double q0, double m0, int sig0) {
		sig = sig0;
		rx = x0;
		ry = y0;
		q  = q0;
		m = m0;
	}
	
	public Charge(double x0, double y0, double vx0, double vy0,
				  double m0, int z, int sig0) {
		final double e = 1.602177e-19;
		final double u = 1.660539e-27;
		sig = sig0;
		rx = x0;
		ry = y0;
		vx = vx0;
		vy = vy0;
		q = z * e;
		m = m0 * u;
	}
	
	public double potentialAt(double x, double y) {
		final double k = 8.987552e9;
		double dx = x - rx;
		double dy = y - ry;
		return k * q / Math.sqrt(dx*dx + dy*dy);
	}
	
	public void stepForward(Charge[] qq, double dt, boolean spam) {
		final double  k = 8.987552e9;
		final double min = 1e-2;
		final int  multi = 1000;
		dt /= multi;
		
		for (int i = 0; i < multi; i++) {
			Fx = 0.0;
			Fy = 0.0;
			for (int j = 0; j < qq.length; j++) {
				if (sig != j) {
					double dx = rx - qq[j].rx;
					double dy = ry - qq[j].ry;
					double d = Math.sqrt(dx*dx + dy*dy);
					if (d < min) {
						dx *= min/d;
						dy *= min/d;
						d   = min;
					}
					double Fxt = k * q * qq[j].q * dx / Math.pow(d, 3);
					double Fyt = k * q * qq[j].q * dy / Math.pow(d, 3);
					if (spam && i == multi - 1) {
						double Ft  = Math.sqrt(Fxt*Fxt + Fyt*Fyt);
						StdOut.printf("      Force from charge %d:\t" +
									  "(%.2e, %.2e)\t|F| = %.2e N\n",
									  j+1, Fxt, Fyt, Ft);
					}
					Fx += Fxt;
					Fy += Fyt;
				}
			}
			rx += vx * dt;
			ry += vy * dt;
			
			vx += (Fx/m) * dt;
			vy += (Fy/m) * dt;
		}
	}
	
	public double getCharge() {
		return q;
	}
	
	public double getX() {
		return rx;
	}
	
	public double getY() {
		return ry;
	}
	
	public double getVx() {
		return vx;
	}
	
	public double getVy() {
		return vy;
	}
	
	public double getFx() {
		return Fx;
	}
	
	public double getFy() {
		return Fy;
	}
	
	public String toString() {
		return q + " C at (" + rx + ", " + ry + ")";
	}
	
	public static void main(String[] args) {
		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		Charge c1 = new Charge(.51, .63, 21.3, 21300.0, 1);
		Charge c2 = new Charge(.13, .94, -81.9, 81900.0, 2);
		double v1 = c1.potentialAt(x, y);
		double v2 = c2.potentialAt(x, y);
		StdOut.printf("  Potential at (" + x + ", " + y + "):"
									+ " %.2e V\n", (v1 + v2));
	}
}