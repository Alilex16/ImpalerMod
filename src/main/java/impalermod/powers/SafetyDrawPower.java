package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import impalermod.ImpalerMod;

public class SafetyDrawPower extends AbstractImpalerPower {
    public static String POWER_ID = "SafetyDrawPower";
    public static String NAME = "Safety Draw";
    public static final String[] DESCRIPTIONS = new String[] {"If not #yBloodshielded, draw #b", " extra card next turn.", " extra cards next turn."};
    public static final String IMG = "powers/SafetyDrawSmall.png";

    public SafetyDrawPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = false;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.priority = 1;
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        if(!BloodshieldPower.isBloodshielded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DrawCardNextTurnPower(this.owner, this.amount), this.amount));
            //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
        }
    }

    @Override
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        if (this.amount > 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

}