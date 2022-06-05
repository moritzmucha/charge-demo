public class RandomCharges {
	public static void fixedCount(int N, double area) {
		double x, y, q;
		double Q = 0.0;
		StdOut.println(N);
		for (int i = 0; i < N-1; i++) {
			x = area * Math.random() - (area - 1.0)/2;
			y = area * Math.random() - (area - 1.0)/2;
			q = 500/N * (2 * Math.random() - 1.0);
			Q += q;
			StdOut.println(x + " " + y + " " + q);
		}
		x = area * Math.random() - (area - 1.0)/2;
		y = area * Math.random() - (area - 1.0)/2;
		q = -Q;
		StdOut.println(x + " " + y + " " + q);
	}
	
	public static void fixedCharge(double Q0, double area) {
		double x, y, q;
		double Q1 = Q0;
		double Q = 0.0;
		int N = 0;
		String table = "";
		while (Q1 > 0.1 * Q0) {
			x = area * Math.random() - (area - 1.0)/2;
			y = area * Math.random() - (area - 1.0)/2;
			q = 0.2 * Q0 * (2 * Math.random() - 1.0);
			Q1 -= Math.abs(q)/2;
			Q += q;
			N++;
			table += x + " " + y + " " + q + "\n";
		}
		x = area * Math.random() - (area - 1.0)/2;
		y = area * Math.random() - (area - 1.0)/2;
		q = -Q;
		N++;
		table += x + " " + y + " " + q + "\n";
		StdOut.println(N);
		StdOut.print(table);
	}
	
	public static void main(String[] args) {
		int    N = 0;
		double Q = 0, area = 1.0;
		boolean countFix = false, chargeFix = false;
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-N") || args[i].equals("-n")) {
				N = Integer.parseInt(args[i+1]);
				countFix = true;
			} else if (args[i].equals("-Q") || args[i].equals("-q")) {
				Q = Double.parseDouble(args[i+1]);
				chargeFix = true;
			} else if (args[i].equals("-A") || args[i].equals("-is")) {
				area = Double.parseDouble(args[i+1]);
			}
		}
		
		if (countFix) fixedCount(N, area);
		if (chargeFix) fixedCharge(Q, area);
	}
}