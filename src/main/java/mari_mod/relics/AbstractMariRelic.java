package mari_mod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import mari_mod.MariMod;

public abstract class AbstractMariRelic extends CustomRelic {


    public AbstractMariRelic(String id, RelicTier tier, LandingSound landingSound) {
        super(id, "", tier, landingSound);
        img = ImageMaster.loadImage(MariMod.relicImage(id));
        largeImg = ImageMaster.loadImage(MariMod.relicLargeImage(id));
        outlineImg = ImageMaster.loadImage(MariMod.relicOutlineImage(id));
    }
}