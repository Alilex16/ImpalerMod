package impalermod.patches;

import basemod.ReflectionHacks;
import impalermod.ui.CampfirePreachOption;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import impalermod.characters.ImpalerCharacter;

import java.util.ArrayList;

@SpirePatch(clz=CampfireUI.class,
            method="initializeButtons")

public class CampfireUIPatch {
    public static void Postfix(Object meObj) {
        CampfireUI campfire = (CampfireUI)meObj;
        try {
            @SuppressWarnings("unchecked")
            ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)
                    ReflectionHacks.getPrivate(campfire, CampfireUI.class, "buttons");

            int height = 180;

            if(AbstractDungeon.player instanceof ImpalerCharacter) {
                if(AbstractDungeon.player.hasRelic("PrayerBeads") && (AbstractDungeon.player.getRelic("PrayerBeads").counter < 3)) { // "PrayerBeads"
                    campfireButtons.add(new CampfirePreachOption(true));
                    float x = 950.f;
                    float y = 990.0f - (270.0f * (float)((campfireButtons.size() + 1) / 2));
                    if (campfireButtons.size() % 2 == 0) {
                        x = 1110.0f;
                        campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
                    }
                    campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
                } else {
                    campfireButtons.add(new CampfirePreachOption(false));
                }




                /*
                    campfireButtons.add(new CampfirePreachOption(true));
                    campfireButtons.get(campfireButtons.size() - 1).setPosition(950 * Settings.scale, height * Settings.scale); //
                } else {
                    campfireButtons.add(new CampfirePreachOption(false));
                }

                */


                  //campfireButtons.get(campfireButtons.size() - 1).setPosition(950 * Settings.scale, height * Settings.scale);
            }

        } catch (SecurityException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
