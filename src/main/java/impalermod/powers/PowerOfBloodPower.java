package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import impalermod.ImpalerMod;

public class PowerOfBloodPower extends AbstractImpalerPower {
    public static String POWER_ID = "PowerOfBloodPower";
    public static String NAME = "Power Of Blood";
    public static final String[] DESCRIPTIONS = new String[]{ "If you only played Attacks this turn, gain #b", " #yStrength at the end of your turn."};
    public static final String IMG = "powers/Placeholder.png";

    public PowerOfBloodPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean onlyAttack) { // not working properly??
        for (AbstractCard playedCard : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (playedCard.type != AbstractCard.CardType.ATTACK)
            {
                return;
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
            }
        }
    }

    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
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