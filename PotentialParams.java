public class PotentialParams {
	private int sizeX = 512, sizeY = 512;
	private double dt = 1e-8, scale = 1.0, luminosity = 0.5e-10;
	private boolean animate, spam;
	
	public PotentialParams (String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-res") || args[i].equals("-r")) {
				String sX = args[i+1].substring(0, args[i+1].indexOf("x"));
				sizeX = Integer.parseInt(sX);
				String sY = args[i+1].substring(args[i+1].indexOf("x")+1, args[i+1].length());
				sizeY = Integer.parseInt(sY);
			}
			
			else if (args[i].equals("-scale") || args[i].equals("-s")) {
				scale = Double.parseDouble(args[i+1]);
			} else if (args[i].equals("-invscale") || args[i].equals("-is")) {
				scale = 1.0 / Double.parseDouble(args[i+1]);
			}
			
			else if (args[i].equals("-luminosity") || args[i].equals("-l")) {
				luminosity = Double.parseDouble(args[i+1]) * 0.5e-10;
			}
			
			else if (args[i].equals("-animate") || args[i].equals("-a")) {
				animate = true;
				try {
					dt = Double.parseDouble(args[i+1]);
				} catch (ArrayIndexOutOfBoundsException e) {
				} catch (NumberFormatException e) {}
			}
			
			else if (args[i].equals("-spam") || args[i].equals("-s")) {
				spam = true;
			}
		}
	}
	
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public double getScale() {
		return scale;
	}
	public double getLuminosity() {
		return luminosity;
	}
	public double getDt() {
		return dt;
	}
	public boolean getAnimate() {
		return animate;
	}
	public boolean getSpam() {
		return spam;
	}
}