package impalermod.relics.deprecated;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import impalermod.relics.AbstractImpalerRelic;

public class Condenser extends AbstractImpalerRelic {
    public static final String ID = "Condenser";
    private static final RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/Sending.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public Condenser() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }


    public AbstractRelic makeCopy() {
        return new Condenser();
    }
}
