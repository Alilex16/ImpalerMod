package impalermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Haemophilia extends AbstractImpalerRelic {
    public static final String ID = "Haemophilia";
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/Haemophilia.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public Haemophilia() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    public AbstractRelic makeCopy() {
        return new Haemophilia();
    }
}
