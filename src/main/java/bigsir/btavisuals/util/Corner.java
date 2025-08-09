package bigsir.btavisuals.util;

import net.minecraft.core.util.helper.Side;

public enum Corner {
	MIN_MIN,
	MIN_MAX,
	MAX_MAX,
	MAX_MIN;

	private final Side[] forSide = new Side[12];

	Corner() {

	}

	public Side getCorner1(Side side) {
		return forSide[side.getId() * 2];
	}

	public Side getCorner2(Side side) {
		return forSide[side.getId() * 2 + 1];
	}

	private void setCorner(Side of, Side c1, Side c2) {
		int id = of.getId() * 2;
		forSide[id] = c1;
		forSide[id + 1] = c2;
	}

	static {
		MIN_MIN.setCorner(Side.TOP, Side.NORTH, Side.WEST);
		MIN_MAX.setCorner(Side.TOP, Side.SOUTH, Side.WEST);
		MAX_MAX.setCorner(Side.TOP, Side.SOUTH, Side.EAST);
		MAX_MIN.setCorner(Side.TOP, Side.NORTH, Side.EAST);

		MIN_MIN.setCorner(Side.BOTTOM, Side.NORTH, Side.WEST);
		MIN_MAX.setCorner(Side.BOTTOM, Side.SOUTH, Side.WEST);
		MAX_MAX.setCorner(Side.BOTTOM, Side.SOUTH, Side.EAST);
		MAX_MIN.setCorner(Side.BOTTOM, Side.NORTH, Side.EAST);

		MIN_MIN.setCorner(Side.NORTH, Side.WEST, Side.BOTTOM);
		MIN_MAX.setCorner(Side.NORTH, Side.WEST, Side.TOP);
		MAX_MAX.setCorner(Side.NORTH, Side.EAST, Side.TOP);
		MAX_MIN.setCorner(Side.NORTH, Side.EAST, Side.BOTTOM);

		MIN_MIN.setCorner(Side.SOUTH, Side.WEST, Side.BOTTOM);
		MIN_MAX.setCorner(Side.SOUTH, Side.WEST, Side.TOP);
		MAX_MAX.setCorner(Side.SOUTH, Side.EAST, Side.TOP);
		MAX_MIN.setCorner(Side.SOUTH, Side.EAST, Side.BOTTOM);

		MIN_MIN.setCorner(Side.EAST, Side.NORTH, Side.BOTTOM);
		MIN_MAX.setCorner(Side.EAST, Side.NORTH, Side.TOP);
		MAX_MAX.setCorner(Side.EAST, Side.SOUTH, Side.TOP);
		MAX_MIN.setCorner(Side.EAST, Side.SOUTH, Side.BOTTOM);

		MIN_MIN.setCorner(Side.WEST, Side.NORTH, Side.BOTTOM);
		MIN_MAX.setCorner(Side.WEST, Side.NORTH, Side.TOP);
		MAX_MAX.setCorner(Side.WEST, Side.SOUTH, Side.TOP);
		MAX_MIN.setCorner(Side.WEST, Side.SOUTH, Side.BOTTOM);
	}
}
