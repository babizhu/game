package game.award;

import java.util.List;

public class Award {

	private final AwardType			type;
	private List<AwardInfo>			content;
	
	public Award( AwardType type, List<AwardInfo> content ) {
		super();
		this.type = type;
		this.setContent(content);
	}

	public AwardType getType() {
		return type;
	}

	public List<AwardInfo> getContent() {
		return content;
	}

	public void setContent(List<AwardInfo> content) {
		this.content = content;
	}
	
}
