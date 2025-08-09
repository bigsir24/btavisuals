package bigsir.btavisuals.util;

import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public class Lmh {
	public static int avg(int lmc1, int lmc2, int lmc3, int lmc4) {
		int skyTotal = ((lmc1 >> 20) + (lmc2 >> 20) + (lmc3 >> 20) + (lmc4 >> 20)) >> 2;
		int blockTotal = (((lmc1 & 0xffff) + (lmc2 & 0xffff) + (lmc3 & 0xffff) + (lmc4 & 0xffff)) >> 6);

		return (skyTotal << 20) | blockTotal << 4;
	}

	public static int avg(int centerLmc, int lmc1, int lmc2) {
		return Lmh.avg(Lmh.avg(lmc1, lmc2), centerLmc);
	}

	public static int avg(int lmc1, int lmc2) {
		int skyTotal = ((lmc1 >> 20) + (lmc2 >> 20)) >> 1;
		int blockTotal = (((lmc1 & 0xffff) + (lmc2 & 0xffff)) >> 5);

		return skyTotal << 20 | blockTotal << 4;
	}
}
