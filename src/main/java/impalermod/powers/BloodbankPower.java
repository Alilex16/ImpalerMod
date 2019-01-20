package impalermod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.ImpalerMod;
import impalermod.actions.MakeTempCardInHandUpgradedAction;
import impalermod.cards.skill.BloodPack;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;

import static impalermod.actions.LivingOffScrapsAction.usedLivingOffScraps;

public class BloodbankPower extends AbstractImpalerPower implements PostDrawSubscriber, PostBattleSubscriber,
        PostDungeonInitializeSubscriber {
    public static String POWER_ID = "BloodbankPower";
    public static String NAME = "Bloodbank";
    //public static final String[] DESCRIPTIONS = new String[]{ "Whenever you draw a #yStatus card, shuffle ", " #yBlood #yPack into your draw pile."};
    public static final String[] DESCRIPTIONS = new String[]{ "Whenever you draw a #yStatus card, replace it with ", " #yBlood #yPack."};
    public static final String IMG = "powers/BloodBankSmall.png";
    private boolean upgraded;

    public BloodbankPower(AbstractCreature owner, int amount, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.upgraded = upgraded;
        this.updateDescription();
    }


    @Override
    public void onInitialApplication() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDungeonInitialize() {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDraw(AbstractCard c)
    {
        /*
        if (c.type == AbstractCard.CardType.STATUS)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            //AbstractDungeon.player.hand.moveToExhaustPile(c);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandUpgradedAction(new BloodPack(), this.amount, this.upgraded));
        }
        */
    }


    @Override
    public void onCardDraw(AbstractCard c)
    {
        if (c.type == AbstractCard.CardType.STATUS && !usedLivingOffScraps)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            //AbstractDungeon.player.hand.moveToExhaustPile(c);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandUpgradedAction(new BloodPack(), this.amount, this.upgraded));
        }
    }

    @Override // does this even work?
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        updateDescription();
    }


    @Override
    public void updateDescription() {


        if (!upgraded)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else
        { // will this make it count as the same Power, or no? NO! GREAT! But it does not update properly when upgrading in the same turn
            POWER_ID = "BloodbankPowerUpgraded";
            //NAME = "BloodbankUpgraded";
            description = DESCRIPTIONS[0] + this.amount + " Upgraded" + DESCRIPTIONS[1];
        }
    }

    // if (card.type == AbstractCard.CardType.SKILL)

    //public void triggerWhenDrawn() {

    //public void triggerWhenDrawn(AbstractCard c) {
    //    if (c.type == AbstractCard.CardType.STATUS) {
    //        this.flash();
    //        AbstractDungeon.actionManager.addToBottom(new CodexAction());
    //    }
    //}



    //@Override
    //public void receivePostDraw(AbstractCard c) {
    //    AbstractPlayer player = (AbstractPlayer) owner;
    //    if (c.isEthereal) {
    //        this.flash();

    //        AbstractDungeon.actionManager.addToBottom(new DamageAction(
     //               AbstractDungeon.getMonsters().getRandomMonster(true),
     //               new DamageInfo(player, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    //    }
    //}





}