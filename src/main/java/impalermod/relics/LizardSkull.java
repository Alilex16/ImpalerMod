package impalermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LizardSkull extends AbstractImpalerRelic {
    public static final String ID = "LizardSkull";
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final String IMG = "relics/LizardSkull.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public LizardSkull() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new LizardSkull();
    }
}
