package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import impalermod.powers.BloodshieldPower;

public class Bloodshield extends AbstractImpalerRelic {
    public static final String ID = "Bloodshield";
    private static final RelicTier TIER = RelicTier.STARTER;
    private static final String IMG = "relics/BloodShield.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public Bloodshield() {
        super(ID, IMG, TIER, SOUND);
        updateDescription();
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, Bloodshield.this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BloodshieldPower (AbstractDungeon.player), 0));
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0]);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Bloodshield();
    }
}
