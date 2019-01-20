package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;

public class HealthyWinnerPower extends AbstractImpalerPower {
    public static final String POWER_ID = "HealthyWinnerPower";
    public static final String NAME = "Healthy Winner";
    public static final String[] DESCRIPTIONS = new String[]{ "At the end of combat, gain #b", " Max HP."};
    public static final String IMG = "powers/HealthyWinnerSmall.png";

    public HealthyWinnerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }


    @Override
    public void onVictory(){
        AbstractPlayer p = AbstractDungeon.player;
        p.maxHealth += this.amount;
        p.currentHealth += this.amount;
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}