package game.prop.templet;

import org.jdom2.Element;

public class StuffTemplet extends BasePropTemplet {

	@Override
	public void parse(Element element) {
		super.parse( element );
	}

	@Override
	public boolean isEquipment() {
		return false;
	}


}
