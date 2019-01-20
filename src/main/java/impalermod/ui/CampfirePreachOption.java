package impalermod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import impalermod.effects.CampfirePrayerEffect;

public class CampfirePreachOption extends AbstractCampfireOption {

    public static final String LABEL = "Preach";
    public static final String DESCRIPTION = "Perform a ritual to obtain the blessing of the Gods.";

    public CampfirePreachOption(boolean active) {
        this.label = LABEL;
        this.usable = active;
        if(active) {
            this.description = DESCRIPTION;
            this.img = new Texture("images/ui/campfire/preach.png");
        } else {
            this.description = DESCRIPTION;
            this.img = new Texture("images/ui/campfire/preach_disabled.png");
        }
    }

    @Override
    public void useOption() {
        if(this.usable) {
            AbstractDungeon.effectList.add(new CampfirePrayerEffect());
        }
    }
}