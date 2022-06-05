import java.awt.Color;

public class Potential {
	public static void drawPic(Charge[] q, Picture pic, int sizeX, int sizeY,
							   double scale, double luminosity) {
		double ratio  = (double) sizeX / sizeY;
		double offset = (1/scale - 1.0)/2;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				double x = (double) i / sizeX;
				double y = (double) j / sizeY;
				if (ratio > 1.0) {
					x = x*ratio/scale - (ratio - 1.0)/2 - offset*ratio;
					y = y/scale - offset;
				} else {
					x = x/scale - offset;
					y = y/ratio/scale - (1/ratio - 1.0)/2 - offset/ratio;
				}
				double phi = 0.0;
				for (int k = 0; k < q.length; k++) {
					phi += q[k].potentialAt(x,y);
				}
				int col = (int) (phi * luminosity);
				if (col < -255) col = -255;
				if (col > 255)  col = 255;
				int r = 0, b = 0;
				if (col > 0) r = col; else b = -col;
				Color c = new Color(r, 0, b);
				pic.set(i, sizeY-1-j, c);
			}
		}
		pic.show();
	}
	
	public static void update(Charge[] q, double dt, boolean spam) {
		if (spam) StdOut.println();
		for (int i = 0; i < q.length; i++) {
			if (spam) {
				StdOut.printf("  State of charge %d (Q = %d C): " + 
					"position = (%.3f, %.3f), velocity = (%d, %d)\n",
					i+1, (int) q[i].getCharge(), q[i].getX(), q[i].getY(),
					(int) q[i].getVx(), (int) q[i].getVy());
			}
			q[i].stepForward(q, dt, spam);
			if (spam) {
				double Fx = q[i].getFx();
				double Fy = q[i].getFy();
				double F  = Math.sqrt(Fx*Fx + Fy*Fy);
				StdOut.printf("      Overall force:\t\t(%.2e, %.2e)\t" +
							  "|F| = %.2e N\n", Fx, Fy, F);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		int N = StdIn.readInt();
		Charge[] q = new Charge[N];
		for (int k = 0; k < N; k++) {
			double x0 = StdIn.readDouble();
			double y0 = StdIn.readDouble();
			double q0 = StdIn.readDouble();
			double m0 = Math.abs(1000 * q0);
			// double m0 = StdIn.readDouble();
			q[k] = new Charge(x0, y0, q0, m0, k);
		}
		
		PotentialParams params = new PotentialParams(args);
		int         sizeX = params.getSizeX();
		int         sizeY = params.getSizeY();
		double         dt = params.getDt();
		double      scale = params.getScale();
		double luminosity = params.getLuminosity();
		boolean   animate = params.getAnimate();
		boolean      spam = params.getSpam();
		
		Picture pic = new Picture(sizeX, sizeY);
		double t  = 0.0;
		do {
			update(q, dt, spam);
			if (spam) {
				t += dt;
				StdOut.printf("  Elapsed time: %.2e seconds\n", t);
			}
			drawPic(q, pic, sizeX, sizeY, scale, luminosity);
			Thread.sleep(10);
		} while (animate);
	}
}