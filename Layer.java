package Sketchy;

/**
 * Here in the Layer class, I keep track of all of the indicies and create getter 
 * and setter methods.
 */
public class Layer {
	private int _paneIndex;
	private int _saveablesIndex;
	private int _shapesIndex;
	
	public Layer(int paneIndex, int saveablesIndex, int shapesIndex) {
		this._paneIndex = paneIndex;
		this._saveablesIndex = saveablesIndex;
		this._shapesIndex = shapesIndex;
	}

	public int getPaneIndex() {
		//gets index of child
		return this._paneIndex;
	}

	public int getSaveablesIndex() {
		//gets index of object in saveable arraylist
		return this._saveablesIndex;
	}

	public int getShapesIndex() {
		//gets index of object in sketchyshape arraylist
		return this._shapesIndex;
	}

	public void setPaneIndex(int _paneIndex) {
		//sets index of child
		this._paneIndex = _paneIndex;
	}

	public void setSaveablesIndex(int _saveablesIndex) {
		//sets index of object in saveable arraylist
		this._saveablesIndex = _saveablesIndex;
	}

	public void setShapesIndex(int _shapesIndex) {
		//sets index of object in sketchyshape arraylist
		this._shapesIndex = _shapesIndex;
	}
	

}
