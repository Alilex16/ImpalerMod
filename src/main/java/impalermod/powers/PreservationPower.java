package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class PreservationPower extends AbstractImpalerPower {
    public static final String POWER_ID = "PreservationPower";
    public static final String NAME = "Preservation";
    public static final String[] DESCRIPTIONS = new String[]{ "Whenever you Heal this turn, gain #b", " #yBlock."};
    public static final String IMG = "powers/PreservationSmall.png";

    public PreservationPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public int onHeal(final int healAmount) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, amount)); // affected by dexterity? It shouldn't. TEST!!

        return healAmount;
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "PreservationPower"));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

}


