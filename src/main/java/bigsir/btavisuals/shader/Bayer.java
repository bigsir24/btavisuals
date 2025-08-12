package bigsir.btavisuals.shader;

public class Bayer {
	public static double max = 0;
	public static float[] gen1DBayerF(int level) {
		double[][] bayer = genBayer(level);
		float[] bayerF = new float[bayer.length * bayer.length];

		for (int i = 0; i < bayer.length; i++) {
			for (int j = 0; j < bayer.length; j++) {
				bayerF[j * bayer.length + i] = (float) bayer[j][i];
			}
		}
		return bayerF;
	}

	public static float[][] genBayerF(int level) {
		double[][] bayer = genBayer(level);
		float[][] bayerF = new float[bayer.length][bayer.length];

		for (int i = 0; i < bayer.length; i++) {
			for (int j = 0; j < bayer.length; j++) {
				bayerF[i][j] = (float) bayer[i][j];
			}
		}
		return bayerF;
	}

	public static double[][] genBayer(int level) {
		return genBayer(level, Math.max(level, 0));
	}

	private static double[][] genBayer(int topLevel, int level) {
		double[][] bayer0 = new double[][]{{0, 2}, {3, 1}};
		if (level == 0) {
			if (topLevel == level) for (int i = 0; i < 4; i++) bayer0[i%2][i/2] /= 4;
			return bayer0;
		}

		int dim = (int) Math.pow(2, level+1);
		int mod = (int) Math.pow(2, level);
		double[][] bayerN = new double[dim][dim];
		double[][] prevBayer = genBayer(topLevel, level - 1);

		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				bayerN[i][j] = 4 * prevBayer[i % mod][j % mod] + bayer0[i / mod][j / mod];
				if (level == topLevel) {
					bayerN[i][j] /= dim*dim;
					max = Math.max(max, bayerN[i][j]);
				}
			}
		}
		return bayerN;
	}
}
