package impalermod.cards.deprecated;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import impalermod.actions.deprecated.DraggingTantrumAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class DraggingTantrum extends AbstractImpalerCard {
	public static final String ID = "DraggingTantrum";
	public static final	String NAME = "Dragging Tantrum ";
	public static final	String IMG = "cards/attack/DraggingTantrum.png";
    public static final	String DESCRIPTION = "Lose X HP. NL Deal !D! damage to all enemies X times. NL Draw X more cards next turn."; // shown in deck
    public static final	String DESCRIPTION_UPGRADED = "Lose X+1 HP. NL Deal !D! damage to all enemies X times. NL Draw X+1 more cards next turn."; // shown in deck - upgraded
	//public static final String DESCRIPTION_BASE = "Lose !M! HP. NL Deal !D! damage to all enemies. NL Draw 1 more card next turn."; // shown in your hand
	//public static final String DESCRIPTION_EXTENDED = "Lose 1 HP !M! times. NL Deal !D! damage to all enemies X times. NL Draw !M! more cards next turn."; // shown in your hand - upgraded
    //public static final String DESCRIPTION_EXTENDED_BLEEDING_ENERGY = "Lose 2 HP !M! times. NL Deal !D! damage to all enemies X times. NL Draw !M! more cards next turn."; // shown in your hand - upgraded
    public static final String DESCRIPTION_NO_ENERGY [] = new String[] {"Lose ", " HP. NL Draw 1 more card next turn."}; // only if upgraded
    public static final String DESCRIPTION_ONE_ENERGY [] = new String[] {"Lose ", " HP", " 2 times", ". NL Deal !D! damage to all enemies. NL Draw ", " more card", "s", " next turn."};
    public static final String DESCRIPTION_MORE_ENERGY [] = new String[] {"Lose ", " HP ", " times. NL Deal !D! damage to all enemies ", " times. NL Draw ", " more cards next turn."};

    private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = -1;

	private static int SELF_DMG = 1;
	private static final int POWER = 2;
	private static final int UPGRADE_POWER = 2;
	private static final int CARD_DRAW = 1;
	private static final int CARD_DRAW_BONUS = 1;


	public DraggingTantrum() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		//this.baseMagicNumber = CARD_DRAW;
		this.magicNumber = this.baseMagicNumber;
		this.isMultiDamage = true;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 2;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
		if (this.energyOnUse < EnergyPanel.totalCount) {
			this.energyOnUse = EnergyPanel.totalCount;
		}

		AbstractDungeon.actionManager.addToBottom(new DraggingTantrumAction(p, m, this.upgraded, this.multiDamage, this.damageTypeForTurn ,this.freeToPlayOnce, this.energyOnUse, SELF_DMG));

		if (!upgraded) // resets description, for in the deck
        {
            this.rawDescription = DESCRIPTION;
        }
        else
        {
            this.rawDescription = DESCRIPTION_UPGRADED;
        }
	}


	private int energyAmount; // X
	private int extraCosts;
	private int cardDraw;
	private int loseHPTimes;

    @Override
    public void applyPowers(){
		energyAmount = EnergyPanel.totalCount;
        extraCosts = 1;
        cardDraw = 1;
        loseHPTimes = energyAmount;
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            extraCosts = 2;
        }
        if (upgraded)
        {
            cardDraw += 1;
            loseHPTimes += 1;
        }

        super.applyPowers();
        this.setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        if (EnergyPanel.totalCount == 0)
        {
			this.rawDescription = DESCRIPTION;

        	if (upgraded)
			{
				this.rawDescription = DESCRIPTION_NO_ENERGY[0] + extraCosts + DESCRIPTION_NO_ENERGY[1]; // DESCRIPTION_BASE;
			}
            // "Lose 1 HP. NL Deal !D! damage to all enemies 1 times. NL Draw 1 more cards next turn."
        }
        if (EnergyPanel.totalCount == 1)
        {
			this.rawDescription = DESCRIPTION_ONE_ENERGY[0] + extraCosts + DESCRIPTION_ONE_ENERGY[1] + DESCRIPTION_ONE_ENERGY[3] + cardDraw + DESCRIPTION_ONE_ENERGY[4] + DESCRIPTION_ONE_ENERGY[6]; // DESCRIPTION_BASE;
            // "Lose ", " HP"," 2 times", ". NL Deal !D! damage to all enemies. NL Draw ", " more card", "s", " next turn."

            if (upgraded)
            {
                this.rawDescription = DESCRIPTION_ONE_ENERGY[0] + extraCosts + DESCRIPTION_ONE_ENERGY[1] + DESCRIPTION_ONE_ENERGY[2] + DESCRIPTION_ONE_ENERGY[3] + cardDraw + DESCRIPTION_ONE_ENERGY[4] + DESCRIPTION_ONE_ENERGY[5] + DESCRIPTION_ONE_ENERGY[6]; // DESCRIPTION_BASE;
            }

		}
        if (EnergyPanel.totalCount >= 2)
        {
			this.rawDescription = DESCRIPTION_MORE_ENERGY[0] + extraCosts + DESCRIPTION_MORE_ENERGY[1] + loseHPTimes + DESCRIPTION_MORE_ENERGY[2] + energyAmount + DESCRIPTION_MORE_ENERGY[3] + loseHPTimes + DESCRIPTION_MORE_ENERGY[4]; // DESCRIPTION_EXTENDED;
            // "Lose ", " HP ", " times. NL Deal !D! damage to all enemies ", " times. NL Draw ", " more cards next turn."
        }

        this.initializeDescription();
    }


	public AbstractCard makeCopy() {
		return new DraggingTantrum();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
		}
	}
	
}
