package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class BloodBrewingPower extends AbstractImpalerPower {
    public static final String POWER_ID = "BloodBrewingPower";
    public static final String NAME = "Blood Brewing";
    public static final String[] DESCRIPTIONS = new String[] { "Gain #b"," random #yPotion", "s", " next turn. NL Can only brew #b1 #yPotion per turn."};
    public static final String IMG = "powers/BloodBrewingSmall.png";

    public BloodBrewingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];

        if (amount >= 2)
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));

        if (amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, BloodBrewingPower.POWER_ID, 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}

