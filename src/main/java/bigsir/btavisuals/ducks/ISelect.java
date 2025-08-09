package bigsir.btavisuals.ducks;

public interface ISelect {
	default int[] getCoords(){
		return null;
	};
	default boolean isHovering(){
		return false;
	};

	default void setHovering(boolean hovering){
	};

	default float getSelectFlash(){
		return 0;
	}
}
