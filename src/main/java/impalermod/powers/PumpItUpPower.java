package impalermod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.ImpalerMod;

public class PumpItUpPower extends AbstractImpalerPower implements PostDrawSubscriber, PostBattleSubscriber,
        PostDungeonInitializeSubscriber {
    public static String POWER_ID = "PumpItUpPower";
    public static String NAME = "Pump It Up";
    public static final String DESCRIPTION = "While your HP is at or below 25%, your Healing is increased.";
    public static final String[] DESCRIPTIONS = new String[]{ "While your HP is at or below 25%, your Healing is increased by #b",  "%."};
    public static final String IMG = "powers/PumpItUpSmall.png";

    public PumpItUpPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = false;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
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
        int currentHealth = AbstractDungeon.player.currentHealth;
        int maxHealth = AbstractDungeon.player.maxHealth;

        if (currentHealth <= (maxHealth / 4))
        {
            return healAmount * 3 / 2;
        }
        else
        {
            return healAmount;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+amount*50+DESCRIPTIONS[1];
    }
}