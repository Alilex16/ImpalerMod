package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class NegateBloodyDamagePower extends AbstractImpalerPower {
    public static final String POWER_ID = "NegateBloodyDamagePower";
    public static final String NAME = "Ignore HP cost";
    public static final String[] DESCRIPTIONS = new String[]{ "Negates #yBloody damage for #b", " card.", " cards."}; // Negates #yBloody damage for #b", "card.", "cards."; // "Negates #yBloody damage #b", "once.", " times."
    public static final String IMG = "powers/RedemptionSmall.png";
    private boolean justApplied = false;

    public NegateBloodyDamagePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        this.isTurnBased = false;
        this.priority = 999;
    }

    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }

        return 0;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void atEndOfTurn(boolean player) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "NegateBloodyDamagePower"));
    }

    public void updateDescription()
    {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}