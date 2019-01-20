package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import impalermod.ImpalerMod;

public class BattleSpiritPower extends AbstractImpalerPower {
    public static String POWER_ID = "BattleSpiritPower";
    public static String NAME = "Battle Spirit";
    public static final String[] DESCRIPTIONS = new String[]{ "At the start of your turn, if your HP is at or below 50%, gain #b",  " #yStrength, else gain #b", " #yDexterity."};
    public static final String IMG = "powers/Placeholder.png";

    public BattleSpiritPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.player.isBloodied)
        {
            //AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, this.amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount), this.amount));
        }
    }

    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}