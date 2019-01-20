package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BattleSpiritPower;

public class BattleSpirit extends AbstractImpalerCard {
    public static final String ID = "BattleSpirit";
    public static final String NAME = "Battle Spirit";
    public static final	String IMG = "cards/power/BattleSpirit.png";
    public static final	String DESCRIPTION = "Ethereal. NL At the start of your turn, if your HP is at or below 50%, gain !M! Strength, else gain !M! Dexterity.";
    //public static final String DESCRIPTIONS [] = new String[] {"At the start of your turn, if your HP is at or below #b50%, gain !M! Strength, else gain !M! Spirit."};

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 3;

    private static final int POWER = 1;
    private static final int UPGRADE_POWER = 1;


    public BattleSpirit() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
        this.isEthereal = true;
        //this.rawDescription = DESCRIPTIONS[0];
        //this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BattleSpiritPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new BattleSpirit();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER);
            this.initializeDescription();
        }
    }
}