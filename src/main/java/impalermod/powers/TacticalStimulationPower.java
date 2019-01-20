package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class TacticalStimulationPower extends AbstractImpalerPower {
    public static final String POWER_ID = "TacticalStimulationPower";
    public static final String NAME = "Tactical Stimulation";
    public static final String[] DESCRIPTIONS = new String[]{ "Whenever a card is #yExhausted, gain #b", " HP."};
    public static final String IMG = "powers/Placeholder.png";

    public TacticalStimulationPower(AbstractCreature owner, int healAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = healAmount;
        this.updateDescription();
        this.isPostActionPower = true;
    }

    public void onExhaust(AbstractCard card) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


}
