package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class BForBloodPower extends AbstractImpalerPower {
	public static final String POWER_ID = "BForBloodPower";
	public static final String NAME = "B For Blood";
	public static final String DESCRIPTION = "Attacks Heal for 50% of [unblocked] damage done.";
	public static final String IMG = "powers/Placeholder.png";
	public AbstractCard card;

	public BForBloodPower(int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = AbstractDungeon.player;
		this.amount = amount;
		this.updateDescription();
		this.img = new Texture(ImpalerMod.getResourcePath(IMG));
		this.type = PowerType.BUFF;
	}

	@Override
	public void updateDescription() {
        description = DESCRIPTION;
	}

	@Override
	public void onAfterCardPlayed(AbstractCard usedCard) {
		card = usedCard;

		int damageDone;
        damageDone = card.damage;
        int healingGained = damageDone / 2; // = 50%

        if (healingGained > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, healingGained));
        }
	}
}

