package game.prop.templet;

import org.jdom2.Element;

public class StuffTemplet extends BasePropTemplet {

	/* (non-Javadoc)
	 * @see game.prop.cfg.BasePropTemplet#parse(org.jdom2.Element)
	 */
	@Override
	public void parse(Element element) {
		super.parse( element );
	}

	@Override
	public boolean isEquipment() {
		// TODO Auto-generated method stub
		return false;
	}


}
