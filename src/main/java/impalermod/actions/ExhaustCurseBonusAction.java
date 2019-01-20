package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import impalermod.powers.SpiritPower;

import java.util.Iterator;

public class ExhaustCurseBonusAction extends AbstractGameAction { // Atonement Smite
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;
    private int spiritGain;

    public ExhaustCurseBonusAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, int spiritGain) {
        this(target, source, amount, isRandom, spiritGain, false, false);
    }

    public ExhaustCurseBonusAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, int spiritGain, boolean anyNumber, boolean canPickZero) {
        this.canPickZero = false;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.spiritGain = spiritGain;
        this.setValues(target, source, amount);
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    public ExhaustCurseBonusAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, int spiritGain, boolean anyNumber) {
        this(target, source, amount, isRandom, spiritGain, anyNumber, false);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();
                this.p.hand.moveToExhaustPile(c);
                this.isDone = true;
                return;
            }

            int i;
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                i = this.p.hand.size();

                for(i = 0; i < i; ++i) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }

            if (!this.isRandom) {
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for(i = 0; i < this.amount; ++i) {
                this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(true));
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                this.p.hand.moveToExhaustPile(c);
                if (c.type == AbstractCard.CardType.CURSE)
                {
                    c.current_y = -200.0f * Settings.scale;
                    c.target_x = Settings.WIDTH / 2.0f + 200;
                    c.target_y = Settings.HEIGHT / 2.0f;
                    c.targetAngle = 0.0f;
                    c.lighten(false);
                    c.drawScale = 0.12f;
                    c.targetDrawScale = 0.75f;
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));


                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiritPower(p, this.spiritGain), this.spiritGain));

                    //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse(),1)); // will be returnRandomBlessing
                }
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
