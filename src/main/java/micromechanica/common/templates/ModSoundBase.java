package micromechanica.common.templates;

import micromechanica.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSoundBase extends SoundEvent{

    public ModSoundBase(String name) {
        super(new ResourceLocation(Main.MODID, name));
        this.setRegistryName(Main.MODID, name);
    }

}
