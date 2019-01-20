package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class BiasedFaithPower extends AbstractImpalerPower {
    public static String POWER_ID = "BiasedFaithPower";
    public static String NAME = "Biased Faith";
    public static final String[] DESCRIPTIONS = new String[] {"Whenever you take damage from an enemy, lose #b", " #ySpirit."};
    public static final String IMG = "powers/BiasedFaithSmall.png";

    public BiasedFaithPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = true;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.DEBUFF;
        this.updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.type != DamageInfo.DamageType.HP_LOSS) {
            this.flash();

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new SpiritPower(owner, -1), -1));
        }

        return damageAmount;
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}