package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KillMonsterAction extends AbstractGameAction {
	AbstractMonster monster;
	public KillMonsterAction(AbstractMonster target) {
		this.monster = target;
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duration = 0;
	}

	@Override
	public void update() {
		monster.damage(new DamageInfo(monster, monster.currentHealth,DamageType.HP_LOSS));
        isDone = true;
	}
	

}

