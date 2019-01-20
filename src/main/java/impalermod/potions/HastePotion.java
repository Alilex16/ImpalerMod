package impalermod.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.powers.HastePower;

public class HastePotion
  extends AbstractPotion
{
  public static final String POTION_ID = "HastePotion";
  private static final String NAME = "Haste Potion";
  public static final String[] DESCRIPTIONS = new String[]{ "Gain #yHaste." };
  //public static final String[] DESCRIPTIONS = new String[]{ "Gain #b", " #yHaste." };

  private static final PotionRarity RARITY = AbstractPotion.PotionRarity.UNCOMMON;
  private static final PotionSize SIZE = AbstractPotion.PotionSize.T;
  private static final PotionColor COLOR = PotionColor.ELIXIR;
  
  public HastePotion()
  {
    super(POTION_ID,NAME,RARITY,SIZE,COLOR);
    this.name = NAME;
    this.potency = getPotency();
    this.description = DESCRIPTIONS[0];
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
    // this.tips.add(new PowerTip(
    
    //   TipHelper.capitalize(GameDictionary.HASTE.NAMES[0]), 
    //   (String)GameDictionary.keywords.get(GameDictionary.HASTE.NAMES[0])));
  }
  
  public void use(AbstractCreature target)
  {
    target = AbstractDungeon.player;
    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
      AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new HastePower(target, this.potency), this.potency));
    }
  }
  
  public AbstractPotion makeCopy()
  {
    return new HastePotion();
  }
  
  public int getPotency(int ascensionLevel)
  {
    return 0;
  }
}
