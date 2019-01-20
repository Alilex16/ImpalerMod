package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class EnervatedPower extends AbstractImpalerPower {
    public static final String POWER_ID = "EnervatedPower";
    public static final String NAME = "Enervated";
    public static final String[] DESCRIPTIONS = new String[]{ "Lose #b", " [R] next turn."};
    public static final String IMG = "powers/EnervatedSmall.png";

    public EnervatedPower(AbstractCreature owner, int energyAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.DEBUFF;
        this.amount = energyAmt;
        this.updateDescription();
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }


    public void onEnergyRecharge() {
        this.flash();
        AbstractDungeon.player.loseEnergy(this.amount);
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "EnervatedPower"));
    }

}


