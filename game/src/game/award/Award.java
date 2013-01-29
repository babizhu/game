package game.award;

import java.util.List;

/**
 * 一个完整的奖品包
 * @author liukun
 *
 */
public class Award {

	private  long					id;
	private final AwardType			type;
	private List<AwardInfo>			content;
	
	public Award( AwardType type, List<AwardInfo> content ) {
		super();
		this.type = type;
		this.setContent(content);
	}
	
	/**
	 * 通过规范的字符串来生成，格式如下
	 * @param type
	 * @param str
	 */
	public Award( AwardType type, String str ){
		this.type = type;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
