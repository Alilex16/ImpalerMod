package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.powers.BleedingPower;

public class DoubleBleedingAction extends AbstractGameAction {
    private float startingDuration;
    private int bleedingAmount;

    public DoubleBleedingAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration && this.target != null && this.target.hasPower("Bleeding")) {
            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new BleedingPower(this.target, this.source, this.target.getPower("Bleeding").amount), this.target.getPower("Bleeding").amount));

            bleedingAmount = this.target.getPower("Bleeding").amount;

            if (AbstractDungeon.player.hasRelic("LizardSkull"))
            {
                AbstractDungeon.player.getRelic("LizardSkull").flash();
                ++this.bleedingAmount;
            }

            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new BleedingPower(this.target, this.target.getPower("Bleeding").amount, true), this.target.getPower("Bleeding").amount));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new BleedingPower(this.target, bleedingAmount, true), bleedingAmount));

        }

        this.tickDuration();
    }
}
