package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import impalermod.ImpalerMod;

public class NoPainNoGainPower extends AbstractImpalerPower {
    public static final String POWER_ID = "NoPainNoGainPower";
    public static final String NAME = "No Pain No Gain";
    public static final String[] DESCRIPTIONS = new String[]{ "Whenever you apply #yBleeding, heal #b", " #yHP."};
    public static final String IMG = "powers/NoPainNoGainSmall.png";
    public AbstractCard card;

    public NoPainNoGainPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && source == owner && target != owner && !target.hasPower("Artifact") && power.ID.equals("Bleeding")) {
            flash();
            //AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, target, amount));

            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.amount));
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new RegenPower(owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}