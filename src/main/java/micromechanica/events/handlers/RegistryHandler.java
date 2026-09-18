package micromechanica.events.handlers;

import micromechanica.common.init.ModBlocks;
import micromechanica.common.init.ModItems;
import micromechanica.common.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {

        ModBlocks.initBlocks(event.getRegistry());

    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {

        ModItems.initItems(event.getRegistry());

        ModBlocks.initItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event)
    {

        ModSounds.initSounds(event.getRegistry());
    }

    @SubscribeEvent
    public static void onPotionRegister(RegistryEvent.Register<Potion> event)
    {

//        PotionInit.initPotions(event.getRegistry());
//        PotionInit.initPotionsRemap();

    }

    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
    {

//        EnchantmentInit.initEnchantments(event.getRegistry());
//        EnchantmentInit.initEnchantmentsRemap();

    }

}