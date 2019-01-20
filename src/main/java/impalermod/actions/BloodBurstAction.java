package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.powers.BleedingPower;

public class BloodBurstAction extends AbstractGameAction{
	private AbstractPlayer player;
	public int bleeding;
    private int selfDamage;
	public static final String TEXT = "exhaust.";
	public BloodBurstAction(AbstractCreature target, int bleeding, int selfDamage) {
	    this.bleeding = bleeding;
	    this.target = target;
		this.duration = Settings.ACTION_DUR_FAST;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.player = AbstractDungeon.player;
		this.selfDamage = selfDamage;
	}

	@Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (player.hand.size() == 0) {
                isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT, player.hand.size(), true, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
        	if (AbstractDungeon.handCardSelectScreen.selectedCards.group.size() > 0) {
                int count = 0;
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    player.hand.moveToDiscardPile(c);
                    count++;
                }

                if (count > 0) {
                    for (int i = 0; i < count; ++i) {
                        if (i == 0) {

                        }
                        //AbstractDungeon.actionManager.addToBottom(new LoseHPAction(player, player, 1));
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(player, new DamageInfo(player, selfDamage, damageType.HP_LOSS), AttackEffect.NONE, true)); // instead of LoseHPAction, because faster
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player, new BleedingPower(target, bleeding, true), bleeding, true, AbstractGameAction.AttackEffect.NONE));


                    }
                }

                //AbstractDungeon.actionManager.addToBottom(new LoseHPAction(player, player, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player, new BleedingPower(target, count, true), count, true, AbstractGameAction.AttackEffect.NONE));

                //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                // rite of summer
        	}
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}