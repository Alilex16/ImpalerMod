package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.ImpalerMod;

public class SeverancePower extends AbstractImpalerPower {
    public static final String POWER_ID = "SeverancePower";
    public static final String NAME = "Severance";
    public static final String[] DESCRIPTIONS = new String[]{ "Whenever you play a #yBloody card, gain #b", " [R]."}; // lose HP from a // Whenever you play a #yBloody card, gain #b", " #yBlock.
    public static final String IMG = "powers/Placeholder.png";
    //public static int cardPlayed;
    //public static int cardPlayedThisTurn;
    public static boolean bloodyCardPlayed;

    public SeverancePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        this.isPostActionPower = true;
        //this.loadRegion("rupture");
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        bloodyCardPlayed = false;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == this.owner && !bloodyCardPlayed) { //cardPlayedThisTurn == cardPlayed) {
            this.flash();
            //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));

            bloodyCardPlayed = true;
        }

        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


}
