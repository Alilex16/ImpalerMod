package impalermod;

import java.nio.charset.StandardCharsets;

import impalermod.cards.attack.*;
import impalermod.cards.curse.*;
import impalermod.cards.power.NoPainNoGain;
import impalermod.cards.skill.*;
import impalermod.cards.power.*;
import impalermod.cards.attack.RampingPull;
import impalermod.potions.HastePotion;
import impalermod.potions.HolyWater;
import impalermod.potions.SpiritPotion;
import impalermod.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PreMonsterTurnSubscriber;
import impalermod.characters.ImpalerCharacter;
import impalermod.patches.AbstractCardEnum;
import impalermod.patches.ImpalerEnum;
import impalermod.powers.AbstractImpalerPower;


@SpireInitializer
public class ImpalerMod implements PostInitializeSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber,PostDrawSubscriber { // ,OnStartBattleSubscriber,PreMonsterTurnSubscriber

	public static final Logger logger = LogManager.getLogger(ImpalerMod.class);

	private static final String MODNAME = "Impaler Mod";
	private static final String AUTHOR = "Alilex16";
	private static final String DESCRIPTION = "v0.9\n Adds The Impaler character";

    private static final Color BLOOD_RED = CardHelper.getColor(137.0f, 15.0f, 15.0f);

	private static final String ASSETS_FOLDER = "images";

	private static final String ATTACK_CARD = "512/bg_attack_impaler.png";
	private static final String SKILL_CARD = "512/bg_skill_impaler.png";
	private static final String POWER_CARD = "512/bg_power_impaler.png";
	private static final String ENERGY_ORB = "512/card_impaler_orb.png";

	private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";

	private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack_impaler.png";
	private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill_impaler.png";
	private static final String POWER_CARD_PORTRAIT = "1024/bg_power_impaler.png";
	private static final String ENERGY_ORB_PORTRAIT = "1024/card_impaler_orb.png";

	private static final String CHAR_BUTTON = "charSelect/button.psd"; // .png
	private static final String CHAR_PORTRAIT = "charSelect/portrait.png";
	public static final String CHAR_SHOULDER_1 = "char/shoulder.png";
	public static final String CHAR_SHOULDER_2 = "char/shoulder2.png";
	public static final String CHAR_CORPSE = "char/corpse.png";
	public static final String CHAR_SKELETON_ATLAS = "char/skeleton.atlas";
    public static final String CHAR_SKELETON_JSON = "char/skeleton.json";

	private static final String BADGE_IMG = "badge.png";

	/*
	public static int cardsDrawnTotal = 0;
	public static int cardsDrawnThisTurn = 0;
	public static int cursesDrawnTotal = 0;
	*/

	public static final String getResourcePath(String resource) {
		return ASSETS_FOLDER + "/" + resource;
	} // "/"

	public ImpalerMod() {
		BaseMod.subscribe(this);
		receiveEditColors();
	}

	public void receiveEditColors()
	{
		logger.info("begin editing colors");

		BaseMod.addColor(AbstractCardEnum.IMPALER,
                BLOOD_RED, BLOOD_RED, BLOOD_RED, BLOOD_RED, BLOOD_RED, BLOOD_RED, BLOOD_RED,
				getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD), getResourcePath(POWER_CARD),
				getResourcePath(ENERGY_ORB),
				getResourcePath(ATTACK_CARD_PORTRAIT), getResourcePath(SKILL_CARD_PORTRAIT), getResourcePath(POWER_CARD_PORTRAIT), getResourcePath(ENERGY_ORB_PORTRAIT),
				getResourcePath(CARD_ENERGY_ORB));

		logger.info("done editing colors");
	}

	public static void initialize() {
		logger.info("Initializing Impaler Mod");

        new ImpalerMod();
	}

	//@Override
	public void receivePostInitialize() {
		Texture badgeTexture = new Texture(getResourcePath(BADGE_IMG));
		ModPanel settingsPanel = new ModPanel();
		settingsPanel.addUIElement(new ModLabel("No settings", 400.0f, 700.0f, settingsPanel, (me) -> {}));
		BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

		Settings.isDailyRun = false;
		Settings.isTrial = false;
		Settings.isDemo = false;

		//receiveEditEvents();
	}

	public void receiveEditEvents() {
		//BaseMod.addEvent(conspire.events.Investor.ID, conspire.events.Investor.class);
		//BaseMod.addEvent(conspire.events.MimicChestEvent.ID, conspire.events.MimicChestEvent.class);
	}


    @Override
	public void receiveEditCharacters() {
        logger.info("begin editing characters");

        BaseMod.addCharacter(new ImpalerCharacter("The Impaler"), getResourcePath(CHAR_BUTTON), getResourcePath(CHAR_PORTRAIT),ImpalerEnum.IMPALER);

        BaseMod.addPotion(HastePotion.class, new Color(1.0f, 1.0f, 1.0f, 0.75f), new Color(0.875f, 0.875f, 0.875f, 0.75f), null, HastePotion.POTION_ID, ImpalerEnum.IMPALER);
        BaseMod.addPotion(SpiritPotion.class, new Color(1.0f, 1.0f, 1.0f, 0.75f), new Color(0.875f, 0.875f, 0.875f, 0.75f), null, SpiritPotion.POTION_ID, ImpalerEnum.IMPALER);
        BaseMod.addPotion(HolyWater.class, new Color(1.0f, 1.0f, 1.0f, 0.75f), new Color(0.875f, 0.875f, 0.875f, 0.75f), null, HolyWater.POTION_ID, ImpalerEnum.IMPALER);

        logger.info("done editing characters");
	}


	public void receiveEditRelics() {
        RelicLibrary.add(new Bloodshield());
		BaseMod.addRelicToCustomPool(new Sending(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new BookOfTheLiving(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new TheBloodborne(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new LizardSkull(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new BigNose(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new RecycleBin(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new BleedingEnergy(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new Haemophilia(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new PrayerBeads(),AbstractCardEnum.IMPALER);
		BaseMod.addRelicToCustomPool(new CrimsonAmulet(),AbstractCardEnum.IMPALER);
	}

	public void receiveEditCards() {
		//BASIC
		BaseMod.addCard(new Bite_Impaler());
		BaseMod.addCard(new Defend_Impaler());
        BaseMod.addCard(new Impale());

		//TESTING


		//COMMON (25) ++
		//Attacks (9) ++
		BaseMod.addCard(new PrayOnTheWeak());
		BaseMod.addCard(new BeatFreshMeat());
		BaseMod.addCard(new Transfusion());
		BaseMod.addCard(new VitalGash());
		BaseMod.addCard(new Rend());
		BaseMod.addCard(new RampingPull());
		BaseMod.addCard(new Splatter());
		BaseMod.addCard(new SpiritualSmash());
		BaseMod.addCard(new MenacingCrunch());

		//Skills (10) ++
		BaseMod.addCard(new Aspirations());
		BaseMod.addCard(new Intrepidity());
		BaseMod.addCard(new JustADrop());
		BaseMod.addCard(new CrimsonPact());
		BaseMod.addCard(new Genuflect());
        BaseMod.addCard(new BloodBurst());
		BaseMod.addCard(new DrenchedShield());
		BaseMod.addCard(new HaemoBarrier());
		BaseMod.addCard(new BonedActuation());
		BaseMod.addCard(new PotentialTreatment());

		//Powers (5) ++
		BaseMod.addCard(new PowerOfBlood());
		BaseMod.addCard(new PleasureForPain());
		BaseMod.addCard(new LickWounds());
		BaseMod.addCard(new PainfulWounds());
        BaseMod.addCard(new NoPainNoGain());



		//UNCOMMON (25)
		//Attacks (9)
		BaseMod.addCard(new TheThirst());
		BaseMod.addCard(new VitalityStrike());
		BaseMod.addCard(new PowerCarnage());
		BaseMod.addCard(new PainForPleasure());
		BaseMod.addCard(new ChargingRush());
		BaseMod.addCard(new RigidRupture());
		BaseMod.addCard(new DinnerTime());
		BaseMod.addCard(new AtonementSmite());
        BaseMod.addCard(new Exsanguinate());

		//Skills (10) ++
		BaseMod.addCard(new Contaminate());
		BaseMod.addCard(new IchorExtraction());
		BaseMod.addCard(new Goreblock());
		BaseMod.addCard(new Prickle());
		BaseMod.addCard(new Pray());
		BaseMod.addCard(new Bloodkinesis());
		BaseMod.addCard(new Preservation());
		BaseMod.addCard(new Suck());
		BaseMod.addCard(new LivingOffScraps());
        BaseMod.addCard(new BioticBarrier());

		//Powers (7) ++
		BaseMod.addCard(new Bloodbank());
		BaseMod.addCard(new Severance());
		BaseMod.addCard(new SafetyDraw());
		BaseMod.addCard(new Blys());
		BaseMod.addCard(new HeavyJaw());
        BaseMod.addCard(new InnerPower());
        BaseMod.addCard(new TacticalStimulation());



		//RARE (24) ++
		//Attacks (7) ++
		BaseMod.addCard(new Frenzy());
		BaseMod.addCard(new Revange());
		BaseMod.addCard(new Haemorrhage());
		BaseMod.addCard(new CrimsonDash());
		BaseMod.addCard(new NeedForBlood());
		BaseMod.addCard(new LeechLife());
		BaseMod.addCard(new Bloodsplosion());

		//Skills (10) ++
		BaseMod.addCard(new Coagulation());
		BaseMod.addCard(new BloodBrewing());
		BaseMod.addCard(new LeechDexterity());
		BaseMod.addCard(new LeechEnergy());
		BaseMod.addCard(new LeechSpeed());
		BaseMod.addCard(new LeechStrength());
		BaseMod.addCard(new LeechPotency());
		BaseMod.addCard(new LeechSoul());
		BaseMod.addCard(new Confess());
		BaseMod.addCard(new Prep());

		//Powers (7) ++
		BaseMod.addCard(new BraveSoul());
		BaseMod.addCard(new HealthyWinner());
		BaseMod.addCard(new PumpItUp());
		BaseMod.addCard(new BattleSpirit());
		BaseMod.addCard(new Redemption());
		BaseMod.addCard(new ThroughPain());
        BaseMod.addCard(new BiasedFaith());


		//SPECIAL ()
		//Attacks ()

		//Skills (1) ++
		BaseMod.addCard(new BloodPack());

		//Powers (4) ++
		//BaseMod.addCard(new Defiance());
		//BaseMod.addCard(new Spiritual());
		//BaseMod.addCard(new Grace());
		BaseMod.addCard(new BForBlood());


		//CURSES (6)
        BaseMod.addCard(new ComplacentCurse());
        BaseMod.addCard(new EnvyCurse());
        BaseMod.addCard(new HeathenCurse());
        BaseMod.addCard(new VertcruxCurse());
        BaseMod.addCard(new UndeadCurse());
        BaseMod.addCard(new WrathCurse());
	}


	public void receiveEditStrings() {
		String relicStrings = Gdx.files.internal("strings/relic-strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	}


	public void receiveEditKeywords() {
		//String[] persistent = {"persistent"};
		//BaseMod.addKeyword(persistent, "Doesn't get discarded automatically at the end of the turn.");

        String[] blessing = {"blessing"};
        BaseMod.addKeyword(blessing, "Blessing cards are positive cards that stay in your deck."); //"Healing with full HP builds up your Bloodshield."

		String[] bloodshield = {"bloodshield", "Bloodshield", "bloodshielded", "Bloodshielded"};
		BaseMod.addKeyword(bloodshield, "Excess HP restoration provides #yBlock."); //"Healing with full HP builds up your Bloodshield."

		String[] bloody = {"bloody", "non-bloody"};
        BaseMod.addKeyword(bloody, "Bloody cards are cards that cost HP to play.");

		String[] heal = {"heal", "healing"};
		BaseMod.addKeyword(heal, "Restore HP.");

        String[] regeneration = {"regeneration"};
        BaseMod.addKeyword(regeneration, "Restores HP at the start of your turn. Each turn, Regeneration is reduced by #b1."); // Copy this from original??

        String[] enervated = {"enervated"};
        BaseMod.addKeyword(enervated, "Next turn, less #yEnergy.");

        String[] bleeding = {"bleeding"};
        BaseMod.addKeyword(bleeding, "Bleeding creatures lose HP at the start of their turn. NL Each turn, Bleeding is reduced by #b1.");

        String[] spirit = {"spirit"};
        BaseMod.addKeyword(spirit, "Spirit increases HP gained from cards."); // "Increases the Healing done from skills by #b1 for each stack of Spirit gained."

		String[] drawReduction = {"draw-reduction", "Draw-Reduction", "Draw-reduction"};
		BaseMod.addKeyword(drawReduction, "Draw #b1 less card at the start of your turn.");

	}


	public void receivePostDraw(AbstractCard c) {
		AbstractPlayer player = AbstractDungeon.player;
		//custom callback for card draw on powers
        for (AbstractPower p : player.powers) {
        	if (p instanceof AbstractImpalerPower) {
        		((AbstractImpalerPower)p).onCardDraw(c);
        	}
        }
        /*
        cardsDrawnThisTurn++;
        cardsDrawnTotal++;
        if (c.type == CardType.CURSE) {
        	cursesDrawnTotal++;
        }
        */
	}



	/*
	@Override
	public boolean receivePreMonsterTurn(AbstractMonster m) {
		cardsDrawnThisTurn = 0;
		return true;
	}

	@Override
	public void receiveOnBattleStart(AbstractRoom room) {
        cardsDrawnTotal = 0;
		cursesDrawnTotal = 0;
	}
	*/
}