package impalermod.characters;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import impalermod.ImpalerMod;
import impalermod.cards.attack.Impale;
import impalermod.cards.curse.UndeadCurse;
import impalermod.patches.AbstractCardEnum;
import impalermod.patches.ImpalerEnum;
import com.badlogic.gdx.graphics.Color;


public class ImpalerCharacter extends CustomPlayer{
	
    public static final int ENERGY_PER_TURN = 3;

    //private static final String SILENT_SKELETON_ATLAS_PATH = "images/characters/theSilent/idle/skeleton.atlas";
    //private static final String SILENT_SKELETON_JSON_PATH = "images/characters/theSilent/idle/skeleton.json";

	private static final Color BLOOD_RED = CardHelper.getColor(137.0f, 15.0f, 15.0f);

	public static final String[] orbTextures = {
			"images/char/orb/layer1.png",
			"images/char/orb/layer2.png",
			"images/char/orb/layer3.png",
			"images/char/orb/layer4.png",
			"images/char/orb/layer5.png",
			"images/char/orb/layer6.png",
			"images/char/orb/layer1d.png",
			"images/char/orb/layer2d.png",
			"images/char/orb/layer3d.png",
			"images/char/orb/layer4d.png",
			"images/char/orb/layer5d.png"
	};


	public ImpalerCharacter(String name)
	{
		super(name, ImpalerEnum.IMPALER, orbTextures, "images/char/orb/vfx.png",
				new SpriterAnimation(ImpalerMod.getResourcePath("char/spriter/impaler.scml")));
		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

		initializeClass(null, ImpalerMod.getResourcePath(ImpalerMod.CHAR_SHOULDER_2),
							ImpalerMod.getResourcePath(ImpalerMod.CHAR_SHOULDER_1),
							ImpalerMod.getResourcePath(ImpalerMod.CHAR_CORPSE),
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

		/*
        this.loadAnimation(SILENT_SKELETON_ATLAS_PATH, SILENT_SKELETON_JSON_PATH, 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        */
	}


	@Override
	public String getTitle(AbstractPlayer.PlayerClass playerClass){
		return "The Impaler";
	}

    @Override
    public String getLocalizedCharacterName() {
        return "The Impaler";
    }

	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.IMPALER;
	}

	@Override
	public Color getCardRenderColor() {
		return BLOOD_RED ; } // Color.BROWN

	@Override
	public Color getCardTrailColor() {
		return BLOOD_RED ; } //ImpalerMod.IMPALER_COLOR; // Color.BROWN

    @Override //
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //return new AbstractGameAction.AttackEffect[0];
		return new AbstractGameAction.AttackEffect[]{
				AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SMASH,
				AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY,
				AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override //
    public AbstractCard getStartCardForEvent() {
        return new Impale();
    }

    @Override //
    public String getSpireHeartText() {
        return "NL So much @Blood@ ...";
    }

    @Override //
    public Color getSlashAttackColor() {
        return BLOOD_RED;
    }

    @Override //
    public String getCustomModeCharacterButtonSoundKey() {
        return "POTION_1";
    }

    @Override
	public int getAscensionMaxHPLoss() {
		return 5;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("POTION_1", MathUtils.random(-0.2f, 0.2f));
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
	}

	@Override
	public AbstractPlayer newInstance() {
		return new ImpalerCharacter(this.name);
	}

	@Override
	public String getVampireText() {
		//return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~weeb,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
		return Vampires.DESCRIPTIONS[5];
	}

	@Override
	public void applyEndOfTurnTriggers() {
		for (AbstractPower p : powers) {
			p.atEndOfTurn(true);
		}
	}


	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Bite_Impaler");
		retVal.add("Bite_Impaler");
		retVal.add("Bite_Impaler");
		retVal.add("Bite_Impaler");
		retVal.add("Defend_Impaler");
		retVal.add("Defend_Impaler");
		retVal.add("Defend_Impaler");
		retVal.add("Defend_Impaler");
		retVal.add("Defend_Impaler");
		retVal.add("BloodPack");
		retVal.add("Impale");
        return retVal;
		//return getAllCards();
	}

	public  ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Bloodshield");
        UnlockTracker.markRelicAsSeen("Bloodshield");
		return retVal;
	}

	public CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Impaler", "The royal Count of Transylvania. NL Does enjoy a bloody dangerous game.",
				60, 60, 0, 99, 5,
			this, getStartingRelics(), getStartingDeck(), false);
	}

	//for debug
	private static ArrayList<String> getAllCards() {
		ArrayList<String> out = new ArrayList<String>();
		for (AbstractCard card : BaseMod.getCustomCardsToAdd()) {
			out.add(card.cardID);
		}
		return out;
	}

}
