package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class BonedActuationExhaustAction extends AbstractGameAction {
    public static final	String DESCRIPTION_BLOODY = "Exhaust a card. NL Heal !M! HP twice.";
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    //public static boolean isBloody;
    private boolean canPickZero;
    public static int numExhausted;
    private AbstractCard card;

    public BonedActuationExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, AbstractCard card) {
        this(target, source, amount, isRandom, card, false, false);
    }

    public BonedActuationExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, AbstractCard card, boolean anyNumber, boolean canPickZero) {

        this.card = card;
        this.canPickZero = false;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    public BonedActuationExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, AbstractCard card, boolean anyNumber) {
        this(target, source, amount, isRandom, card, anyNumber, false);
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

                for(i = 0; i < i; ++i) { // for(int i = 0 .. etc)
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
                if ((c.rawDescription.contains("Lose") || c.rawDescription.contains("lose")) && (c.rawDescription.contains("HP")))
                {
                    this.card.isEthereal = false;
                    this.card.rawDescription = DESCRIPTION_BLOODY;
                    this.card.initializeDescription();
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
