package micromechanica.events.back;

import micromechanica.api.ICraftProgress;
import micromechanica.common.capability.ProviderUniform;
import micromechanica.common.init.ModBlocks;
import micromechanica.common.init.ModItems;
import micromechanica.common.init.ModRecipes;
import micromechanica.common.init.ModSounds;
import micromechanica.common.templates.ModItemCapableBase;
import micromechanica.common.templates.ModMachineBase;
import micromechanica.root.Main;
import micromechanica.util.StorageTypes;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;

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

    @SubscribeEvent
    public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event)
    {

        ModRecipes.initRecipes(event.getRegistry());

    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {

        ItemStack stack = event.getObject();

        if (!(stack.getItem() instanceof ModItemCapableBase)) {
            return;
        }

        ModMachineBase item = (ModMachineBase) stack.getItem();

        ItemStackHandler items = item.hasExpectedStorage(item, StorageTypes.ITEMS)
                ? new ItemStackHandler()
                : null;

        EnergyStorage energy = item.hasExpectedStorage(item, StorageTypes.ENERGY)
                ? new EnergyStorage(item.getExpectedStorage(item).second())
                : null;

        FluidTank fluid = item.hasExpectedStorage(item, StorageTypes.FLUID)
                ? new FluidTank(item.getExpectedStorage(item).second())
                : null;

        ICraftProgress progress = item.getCraftProgress(stack);

        if (items == null && energy == null && fluid == null && progress == null) {
            return;
        }

        event.addCapability(
                new ResourceLocation(Main.MODID, "uniform"),
                new ProviderUniform(items, energy, fluid, progress)
        );
    }

}