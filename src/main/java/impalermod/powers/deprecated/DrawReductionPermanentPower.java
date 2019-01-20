package impalermod.powers.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;
import impalermod.powers.AbstractImpalerPower;

public class DrawReductionPermanentPower extends AbstractImpalerPower {
    public static final String POWER_ID = "DrawReductionPermanentPower";
    public static final String NAME = "Draw Reduction";
    public static final String[] DESCRIPTIONS = new String[]{ "Draw #b", " less card", "s", " next turn."};
    public static final String IMG = "powers/Placeholder.png";

    public DrawReductionPermanentPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
    }

    public void onInitialApplication()
    {
        //int debuffStacks = AbstractDungeon.player.getPower(DrawReductionPermanentPower.POWER_ID).amount;

        //AbstractDungeon.player.gameHandSize -= debuffStacks;
    }

    public void onRemove()
    {
        if (AbstractDungeon.player.getPower(DrawReductionPermanentPower.POWER_ID).amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "DrawReductionPermanentPower", 1));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "DrawReductionPermanentPower"));
        }

        ++AbstractDungeon.player.gameHandSize;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }
}
