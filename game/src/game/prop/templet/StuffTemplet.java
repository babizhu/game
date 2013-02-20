package game.prop.templet;

import game.prop.enums.PropType;

import org.jdom2.Element;

public class StuffTemplet extends PropTempletBase {

	@Override
	public void parse(Element element) {
		super.parse( element );
	}

	@Override
	public PropType getType() {
		return PropType.STUFF;
	}

}
