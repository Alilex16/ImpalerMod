package impalermod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.ImpalerMod;
import impalermod.cards.curse.UndeadCurse;

public class UndeadCursePower extends AbstractImpalerPower implements PostDrawSubscriber, PostBattleSubscriber,
        PostDungeonInitializeSubscriber {
    public static String POWER_ID = "UndeadCursePower";
    public static String NAME = "Undead";
    public static final String DESCRIPTION = "Whenever you heal this turn, lose HP instead.";
    public static final String[] DESCRIPTIONS = new String[]{ "Whenever you heal this turn, lose HP instead."};
    public static final String IMG = "powers/UndeadCurseSmall.png";
    private static int undeadHealing;

    public UndeadCursePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isTurnBased = false;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        //this.type = PowerType.BUFF;
        this.priority = -999;
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
    public void receivePostDraw(AbstractCard c) {    }

    @Override
    public int onHeal(final int healAmount)
    {
        undeadHealing = healAmount;
        actionAfterHeal();
        return 0;
    }

    private void actionAfterHeal()
    {
        AbstractDungeon.actionManager.addToTop(new LoseHPAction(this.owner, this.owner, undeadHealing));
    }


    @Override
    public void atStartOfTurn() {
        UndeadCurse.undeadCursed = false;
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "UndeadCursePower"));
    }

    /*
    @Override
    public void atEndOfRound() // this seemed to work fine
    {
        UndeadCurse.undeadCursed = false;
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "UndeadCursePower"));
    }
    */

    /*
    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        UndeadCurse.undeadCursed = false;
    }
    */

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}