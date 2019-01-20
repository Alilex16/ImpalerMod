package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class PrepPower extends AbstractImpalerPower {
    public static final String POWER_ID = "PrepPower";
    public static final String NAME = "Prep";
    public static final String[] DESCRIPTIONS = new String[]{"On your next turn, your Healing is doubled."};
    public static final String IMG = "powers/DoubleHealSmall.png";

    public PrepPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        //this.loadRegion("brutality");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atStartOfTurn() { // atStartOfTurnPostDraw
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DoubleHealingPower(this.owner, 1, false), this.amount));
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "PrepPower", 1));
    }
}