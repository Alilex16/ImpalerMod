package impalermod.relics;

import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;
import impalermod.ImpalerMod;

public abstract class AbstractImpalerRelic extends CustomRelic{

	public AbstractImpalerRelic(String id, String img, RelicTier tier, LandingSound sfx) {
		super(id, new Texture(ImpalerMod.getResourcePath(img)), tier, sfx);
	}


}
