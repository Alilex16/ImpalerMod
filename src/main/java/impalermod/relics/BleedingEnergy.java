package impalermod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BleedingEnergy extends AbstractImpalerRelic {
    public static final String ID = "BleedingEnergy";
    private static final RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/BleedingEnergy.png";
    private static final LandingSound SOUND = LandingSound.CLINK;
    public static boolean bleedingEnergyEquipped;

    public BleedingEnergy() {
        super(ID, IMG, TIER, SOUND);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        bleedingEnergyEquipped = true;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
        bleedingEnergyEquipped = false;
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
    }


    public AbstractRelic makeCopy() {
        return new BleedingEnergy();
    }
}
