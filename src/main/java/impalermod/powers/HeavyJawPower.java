package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;
import impalermod.cards.attack.Bite_Impaler;

import java.util.Iterator;

public class HeavyJawPower extends AbstractImpalerPower {
    public static String POWER_ID = "HeavyJawPower";
    public static String NAME = "Heavy Jaw";
    public static final String[] DESCRIPTIONS = new String[] {"All #yBite cards are stronger. Damage is increased by #b", " and heal by #b", "."};
    public static final String IMG = "powers/Placeholder.png";
    private int amountDamage;
    private int amountHeal;

    public HeavyJawPower(AbstractCreature owner, int amountDamage, int amountHeal) {//, int amountHeal) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amountDamage = amountDamage;
        this.amountHeal = amountHeal;
        this.isTurnBased = false;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.updateBiteInHand();
        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amountDamage += 2;
        this.amountHeal += 1;
        this.updateBiteInHand();
    }

    private void updateBiteInHand() {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            if (c instanceof Bite_Impaler) {
                if (!c.upgraded) {
                    c.baseDamage = 5 + this.amountDamage;
                    c.baseMagicNumber = 1 + this.amountHeal;
                } else {
                    c.baseDamage = 7 + this.amountDamage;
                    c.baseMagicNumber = 2 + this.amountHeal;
                }
            }
        }

    }

    public void onDrawOrDiscard() {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            if (c instanceof Bite_Impaler) {
                if (!c.upgraded) {
                    c.baseDamage = this.amountDamage + 5;
                    c.baseMagicNumber = this.amountHeal + 1;
                } else {
                    c.baseDamage = this.amountDamage + 7;
                    c.baseMagicNumber = this.amountHeal + 2;
                }
            }
        }
    }


    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + this.amountDamage + DESCRIPTIONS[1] + this.amountHeal + DESCRIPTIONS[2];
    }
}