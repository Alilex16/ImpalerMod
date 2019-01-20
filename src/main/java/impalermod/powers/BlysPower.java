package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;
import impalermod.actions.BlysAction;

public class BlysPower extends AbstractImpalerPower {
    public static String POWER_ID = "BlysPower";
    public static String NAME = "Blys";
    public static final String[] DESCRIPTIONS = new String[] {"At the start of your turn, apply 1 #yBleed to #b", " random ", "enemy.", "enemies."};
    public static final String IMG = "powers/Placeholder.png";

    public BlysPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = true;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() // atEndOfTurn(boolean isPlayer)
    {
        AbstractDungeon.actionManager.addToBottom(new BlysAction(this.owner, this.amount));

        ////AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(true);

        //AbstractMonster randomMonster = AbstractDungeon.getRandomMonster();
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(randomMonster, AbstractDungeon.player, new BleedingPower(randomMonster, this.amount, false), this.amount));
    }

    @Override
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        }
        if (this.amount > 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        }
    }

}