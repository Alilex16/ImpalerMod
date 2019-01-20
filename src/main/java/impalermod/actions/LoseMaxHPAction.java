package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class LoseMaxHPAction extends AbstractGameAction{
	private int LoseMAXHP;

	public LoseMaxHPAction(AbstractCreature target, int LoseMAXHP) {
	    this.target = target;
		this.duration = Settings.ACTION_DUR_FAST;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.LoseMAXHP = LoseMAXHP;
	}

	@Override
    public void update() {
	    target.decreaseMaxHealth(LoseMAXHP);
        isDone = true;
    }
}