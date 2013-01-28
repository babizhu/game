package game.award;

import java.util.Map;

import user.UserInfo;
import util.ErrorCode;

/**
 * 奖品中心
 * @author liukun
 * 2013-1-28 下午2:16:21
 */
public class AwardManager {
	
	private final UserInfo				user;
	private Map<Long,Award>				awards;
	
	

	public AwardManager(UserInfo user) {
		super();
		this.user = user;
	}

	public Map<Long,Award> getAwards() {
		return awards;
	}

	public void setAwards(Map<Long,Award> awards ) {
		this.awards = awards;
	}
	
	/**
	 * 通过id获取奖品
	 * @param id
	 * @return
	 */
	public ErrorCode getAward( long id ){
		Award award = awards.get( id );
		if( award == null ){
			return ErrorCode.AWARD_NOT_FOUND;
		}
		for( AwardInfo info : award.getContent() ){
			getAwardContent(info);
		}
		
		return ErrorCode.SUCCESS;
	}
	
	private ErrorCode getAwardContent( AwardInfo info ){
		return user.getAward(info, "getAwardContent" );
	}
	
	public void add( Award award ){
		

		
	}
	
	public void remove( long id ){
		
	}
	
	

}
