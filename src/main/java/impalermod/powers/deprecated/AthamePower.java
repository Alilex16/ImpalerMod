package impalermod.powers.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;

import impalermod.ImpalerMod;
import impalermod.powers.AbstractImpalerPower;

public class AthamePower extends AbstractImpalerPower {
    public static final String POWER_ID = "AthamesOffering";
    public static final String NAME = "Athame's Offering";
    public static final String[] DESCRIPTIONS = new String[]{ "Chance to obtain a rare card increased by #b", "%." };
    public static final String IMG = "powers/Placeholder.png";
    public AthamePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }
}

