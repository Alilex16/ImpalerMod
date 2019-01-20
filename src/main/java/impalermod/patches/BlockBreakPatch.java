package impalermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import impalermod.powers.AbstractImpalerPower;

@SpirePatch(
		cls="com.megacrit.cardcrawl.core.AbstractCreature",
		method="brokeBlock",
		paramtypez={}
)
public class BlockBreakPatch {
	public static void Prefix(AbstractCreature __instance) {
        for (AbstractPower p : __instance.powers) {
        	if (p instanceof AbstractImpalerPower) {
        		((AbstractImpalerPower) p).onBlockBreak();
        	}
        }
	}
}
