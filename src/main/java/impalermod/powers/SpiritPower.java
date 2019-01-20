package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class SpiritPower extends AbstractImpalerPower {
    public static final String POWER_ID = "Spirit";
    public static final String NAME = "Spirit";
    public static final String IMG = "powers/SpiritSmall.png";
    public boolean MagicNumberModified;

    //public static final Logger logger = LogManager.getLogger(Fudgesickle.class.getName());

    public SpiritPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Spirit";
        this.owner = owner;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.updateDescription();
        this.canGoNegative = true;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Spirit"));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    /*
    @Override
    public int onHeal(final int healAmount) {
        if (!AbstractDungeon.player.hasPower(NoHealPower.POWER_ID)){
            this.flash();
        }
        return healAmount + amount;
    }
    */

    @Override
    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, NAME));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    @Override
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = "Increases #yHP gained from cards by #b" + this.amount + "."; // Increases healing skills amount by
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = "Decreases #yHP gained from cards by #b" + this.amount + "."; // Decreases healing skills amount by
            this.type = PowerType.DEBUFF;
        }

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL)
        {
        //logger.info("card name: " + card.name);
            action.amount += this.amount;

            //card.magicNumber += this.amount;
            card.isMagicNumberModified = true;
        }
        else if(card.name == new BandageUp().name)
        {
            card.magicNumber += amount;
        }
    }

    //@Override
    //public int onHeal(int healAmount) {
    //    return healAmount + amount;
    //}





   /*
    public float atDamageGive(float damage, DamageType type) {
        return type == DamageType.NORMAL ? damage + (float)this.amount : damage;
    }
    */


}
