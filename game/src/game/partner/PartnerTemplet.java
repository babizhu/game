package game.partner;

import game.battle.skill.SkillTemplet;

/**
 * 伙伴 模板
 * @author Administrator
 * 2013-2-5 上午11:52:49
 */
public class PartnerTemplet {
	String				name;
	short				templetId;

	/**
	 * 血槽最大值
	 */
	int							hpMax;
	
	/**
	 * sp最大值
	 */
	int							spMax;
	
	/**
	 * 速度
	 */
	int							speed;
	
	/**
	 * 物理攻击力
	 */
	int							phyAttack;
	
	/**
	 * 物理防御力
	 */
	int							phyDefend;
	
	/**
	 * 命中率
	 */
	int							hitRate;			
	
	/**
	 * 闪避率
	 */
	int							dodgeRate;	
	
	/**
	 * 暴击
	 */	
	int							crit;
	
	/**
	 * 反暴击
	 */	
	int							unCrit;
	
	/**
	 * 格挡
	 */
	int							block;
	
	/**
	 * 反格挡
	 */
	int							unBlock;
	
	/**
	 * 技能模板
	 */
	SkillTemplet				skillTemplet;
	
	

}
