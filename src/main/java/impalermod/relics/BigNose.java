package impalermod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import impalermod.actions.BigNoseAction;

public class BigNose extends AbstractImpalerRelic {
    public static final String ID = "BigNose";
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final String IMG = "relics/BigNose.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public BigNose() {
        super(ID, IMG, TIER, SOUND);
    }


    public void atTurnStartPostDraw()
    {
        BigNose.this.pulse = true;
        BigNose.this.stopPulse();
        //AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, BigNose.this));
        AbstractDungeon.actionManager.addToTop(new BigNoseAction(AbstractDungeon.player, BigNose.this));
    }

    public void onVictory() {
        BigNose.this.pulse = false;
        //this.isActive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }


    public AbstractRelic makeCopy() {
        return new BigNose();
    }
}
