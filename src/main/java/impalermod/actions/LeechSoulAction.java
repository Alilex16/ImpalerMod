package impalermod.actions;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import impalermod.cards.attack.LeechLife;
import impalermod.cards.skill.*;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class LeechSoulAction extends AbstractGameAction{
    private boolean retrieveCard = false;
    private static HashMap<String, AbstractCard> newCards = CardLibrary.cards;
    private boolean upgraded; // worked @ non-static


	public LeechSoulAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
	}

    public static void add(AbstractCard card) {
        if (!UnlockTracker.isCardSeen(card.cardID)) {
            card.isSeen = false;
        }

        newCards.put(card.cardID, card);
        card.initializeDescription();
    }

	public static AbstractCard returnLeechCard()
    {
        UnlockTracker.markCardAsSeen(getLeechCard().cardID);
        return getLeechCard().makeCopy();
    }


    private static AbstractCard getLeechCard()
    {
        ArrayList<String> tmp = new ArrayList();
        Iterator var1 = newCards.entrySet().iterator();

        while(var1.hasNext()) {
            Entry<String, AbstractCard> c = (Entry)var1.next();

            if ((c.getValue()).cardID.equals("LeechLife")
                    || (c.getValue()).cardID.equals("LeechDexterity")
                    || (c.getValue()).cardID.equals("LeechEnergy")
                    || (c.getValue()).cardID.equals("LeechPotency")
                    || (c.getValue()).cardID.equals("LeechSpeed")
                    || (c.getValue()).cardID.equals("LeechStrength"))
            {
                tmp.add(c.getKey());
            }
        }
        return (AbstractCard)newCards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
    }




    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
        	AbstractDungeon.cardRewardScreen.reset();

        	AbstractDungeon.cardRewardScreen.rItem = null;
        	AbstractDungeon.cardRewardScreen.discoveryCard = null;
            AbstractDungeon.cardRewardScreen.codexCard = null;
            ReflectionHacks.setPrivate(AbstractDungeon.cardRewardScreen, CardRewardScreen.class, "discovery", true);
            ((SingingBowlButton) ReflectionHacks.getPrivate(AbstractDungeon.cardRewardScreen, CardRewardScreen.class, "bowlButton")).hide();
            ((SkipCardButton) ReflectionHacks.getPrivate(AbstractDungeon.cardRewardScreen, CardRewardScreen.class, "skipButton")).show();
            AbstractDungeon.cardRewardScreen.onCardSelect = true;
            AbstractDungeon.topPanel.unhoverHitboxes();
            ArrayList<AbstractCard> derp = new ArrayList<AbstractCard>();
            while (derp.size() != 2) {
                boolean dupe = false;
                AbstractCard tmp = returnLeechCard();
                for (AbstractCard c : derp) {
                    if (!c.cardID.equals(tmp.cardID)) continue;
                    dupe = true;
                    break;
                }
                if (dupe) continue;
                derp.add(tmp.makeCopy());
            }
            AbstractDungeon.cardRewardScreen.rewardGroup = derp;
            AbstractDungeon.isScreenUp = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
            AbstractDungeon.dynamicBanner.appear("Add a Leech card to your hand");
            AbstractDungeon.overlayMenu.showBlackScreen();
            placeCards((float)Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.45f);
            for (AbstractCard c : AbstractDungeon.cardRewardScreen.rewardGroup) {
                UnlockTracker.markCardAsSeen(c.cardID);
            }
            tickDuration();
            return;
        }
        if (!retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard leechCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(leechCard, Settings.WIDTH/2, Settings.HEIGHT/2));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }

    private void placeCards(float x, float y) {
    	//AbstractDungeon.cardRewardScreen.rewardGroup.get((int)0).target_x = (float)Settings.WIDTH / 2.0f - AbstractCard.IMG_WIDTH - 40.0f * Settings.scale;
    	//AbstractDungeon.cardRewardScreen.rewardGroup.get((int)1).target_x = (float)Settings.WIDTH / 2.0f;
    	//AbstractDungeon.cardRewardScreen.rewardGroup.get((int)2).target_x = (float)Settings.WIDTH / 2.0f + AbstractCard.IMG_WIDTH + 40.0f * Settings.scale;
        AbstractDungeon.cardRewardScreen.rewardGroup.get((int)0).target_x = (float)Settings.WIDTH / 2f - AbstractCard.IMG_WIDTH / 2 - 40.0f * Settings.scale;
        AbstractDungeon.cardRewardScreen.rewardGroup.get((int)1).target_x = (float)Settings.WIDTH / 2f + AbstractCard.IMG_WIDTH / 2 + 40.0f * Settings.scale;
    	AbstractDungeon.cardRewardScreen.rewardGroup.get((int)0).target_y = y;
        AbstractDungeon.cardRewardScreen.rewardGroup.get((int)1).target_y = y;
        //AbstractDungeon.cardRewardScreen.rewardGroup.get((int)2).target_y = y;

        for (AbstractCard c : AbstractDungeon.cardRewardScreen.rewardGroup) {
            c.drawScale = 0.75f;
            c.targetDrawScale = 0.75f;
            c.current_x = x;
            c.current_y = y;

            if (upgraded)
            {
                c.upgrade();
            }
        }
    }
    
}
