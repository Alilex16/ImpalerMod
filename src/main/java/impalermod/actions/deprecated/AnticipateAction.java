package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;


public class AnticipateAction extends AbstractGameAction {
    private static final String[] TEXT = new String[] { "Select a card to draw"};
    private int damageIncrease;
    private AbstractMonster targetMonster;

    public AnticipateAction(int damageIncrease, AbstractMonster m) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damageIncrease = damageIncrease;
        this.targetMonster = m;
    }

    public void update() {
        //if (this.targetMonster == null || this.targetMonster.intent != AbstractMonster.Intent.ATTACK && this.targetMonster.intent != AbstractMonster.Intent.ATTACK_BUFF && this.targetMonster.intent != AbstractMonster.Intent.ATTACK_DEBUFF && this.targetMonster.intent != AbstractMonster.Intent.ATTACK_DEFEND) {
        //    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        //} else {
        //    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.damageIncrease), this.damageIncrease));
        //}

        if (this.targetMonster != null && (this.targetMonster.intent == Intent.ATTACK) || this.targetMonster.intent == Intent.ATTACK_BUFF || this.targetMonster.intent == Intent.ATTACK_DEBUFF || this.targetMonster.intent == Intent.ATTACK_DEFEND)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.damageIncrease), this.damageIncrease));
        }


        this.isDone = true;
    }
}
