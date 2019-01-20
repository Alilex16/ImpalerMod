package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class DoubleHealingPower extends AbstractImpalerPower {
    public static final String POWER_ID = "DoubleHealingPower";
    public static final String NAME = "Double Healing";
    public static final String[] DESCRIPTIONS = new String[]{ "Doubles #yHealing this turn."};
    public static final String IMG = "powers/DoubleHealSmall.png";
    private boolean justApplied = false;

    public DoubleHealingPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        this.justApplied = isSourceMonster;
        this.isTurnBased = true;
        this.priority = 999;
    }

    @Override
    public int onHeal(final int healAmount)
    {
        return healAmount * 2;
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "DoubleHealingPower"));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "DoubleHealingPower", 1));
            }

        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}