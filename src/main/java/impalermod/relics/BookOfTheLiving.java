package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class BookOfTheLiving extends AbstractImpalerRelic {
    public static final String ID = "BookOfTheLiving";
    private static final RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/BookOfTheLiving.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    private int AMT;

    public static final Logger logger = LogManager.getLogger(BookOfTheLiving.class.getName());

    public BookOfTheLiving() {
        super(ID, IMG, TIER, SOUND);
        AMT = 0;
    }

    public void onEquip() {
        this.counter = 0;
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
        AMT = 0;
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (isCardCostsHP(c)) {
                ++this.AMT;
            }
        }
        this.counter = AMT / 2;

        if (this.counter == 0) {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
        } else {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3].replace("#g" , "" + AMT) + this.DESCRIPTIONS[4];
        }

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void setCounter(int c) {
        this.counter = c;
        if (this.AMT == 0) {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
        } else {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3].replace("#g" , "" + AMT) + this.DESCRIPTIONS[4];
        }

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void onMasterDeckChange() {
        this.counter = 0;
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
        AMT = 0;
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (isCardCostsHP(c)) {
                ++this.AMT;
            }
        }
        this.counter = AMT / 2;

        if (this.AMT == 0) {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
        } else {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3].replace("#g" , "" + AMT) + this.DESCRIPTIONS[4];
        }

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    public void atBattleStart() {
        if (this.counter > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, this.counter), this.counter));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }


    public boolean isCardCostsHP (AbstractCard c)
    {
        logger.info(c.name + " "+ c.type + " " + c.rawDescription);
        if ((c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE) &&
                (c.rawDescription.contains("Lose") || c.rawDescription.contains("lose")) &&
                (c.rawDescription.contains("HP")))
        {
            return true;
        }
        return false;
    }


    //public static boolean isCardCostsHP(AbstractCard c) {
    //    return c instanceof PainfulWounds || c instanceof Rend;
    //}

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BookOfTheLiving();
    }

}
