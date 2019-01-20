package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import impalermod.ImpalerMod;

public class ThroughPainPower extends AbstractImpalerPower {
	public static final String POWER_ID = "ThroughPainPower";
	public static final String NAME = "Through Pain";
	public static final String[] DESCRIPTIONS = new String[]{ "Start every turn with #b"," #yArtifact."};
	public static final String IMG = "powers/ThroughPainSmall.png";
	public ThroughPainPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.updateDescription();
		this.img = new Texture(ImpalerMod.getResourcePath(IMG));
		this.type = PowerType.BUFF;
	}

	@Override
	public void updateDescription() {
		description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
	}

	@Override
	public void atStartOfTurn(){
		if (!owner.hasPower(ArtifactPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new ArtifactPower(this.owner, this.amount), this.amount));
		}
	}
}