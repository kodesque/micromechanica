package micromechanica.util.recipes;

import akka.japi.Pair;
import micromechanica.api.ICraftProgress;
import micromechanica.common.templates.ModMachineBase;
import micromechanica.util.StorageTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class MachineRecipeManager {

    private static final ArrayList<MachineRecipe> modRecipes = new ArrayList<>();

    public static void addRecipe(MachineRecipe recipe) {
        modRecipes.add(recipe);
    }

    /*
    Logic for handleRightClick: if tryCraft return != null, delete item under mouse (handled through network)
     */

    public static ItemStack[] tryCraft(ItemStack machine, @Nullable ItemStack specialInput) {

        Item item = machine.getItem();

        if (!(item instanceof ModMachineBase)) {
            return null;
        }

        ModMachineBase base = (ModMachineBase) item;

        IItemHandler itemHandler = base.getRealStorage(machine, StorageTypes.ITEMS);
        IEnergyStorage energyStorage = base.getRealStorage(machine, StorageTypes.ENERGY);
        IFluidHandler fluidHandler = base.getRealStorage(machine, StorageTypes.FLUID);

        ItemStack[] items = getItems(itemHandler);
        int energy = getEnergy(energyStorage);
        Pair[] fluids = getFluids(fluidHandler);

        MachineRecipe recipe = findRecipe(base, items, fluids, energy, specialInput);

        if (recipe == null) {
            return null;
        }

        if ((specialInput != null && recipe.specialInput.equals(Ingredient.fromStacks(specialInput)) || recipe.specialInput == null)) {
            if (base.hasCraftProgress(machine)) {

                ICraftProgress progress = base.getCraftProgress(machine);

                if (progress.getValue() < recipe.requiredProgress) {
                    progress.setValue(progress.getValue() + 1);
                } else {
                    progress.setValue(0);
                    return conductCraft(recipe, itemHandler, energyStorage, fluidHandler, specialInput);
                }

            }
        }

        return null;
    }

    private static ItemStack[] getItems(IItemHandler itemHandler) {

        ItemStack[] items = new ItemStack[0];

        if (itemHandler != null) {
            items = new ItemStack[itemHandler.getSlots()];

            for (int i = 0; i < itemHandler.getSlots(); i++) {
                items[i] = itemHandler.getStackInSlot(i);
            }
        }

        return items;
    }

    private static int getEnergy(IEnergyStorage energyStorage) {

        int energy = 0;

        if (energyStorage != null) {
            energy = energyStorage.getEnergyStored();
        }

        return energy;
    }

    private static Pair[] getFluids(IFluidHandler fluidHandler) {

        Pair[] fluids = new Pair[0];

        if (fluidHandler != null) {
            IFluidTankProperties[] properties = fluidHandler.getTankProperties();

            if (properties != null) {
                ArrayList<Pair<Fluid, Integer>> storedFluids = new ArrayList<>();

                for (IFluidTankProperties property : properties) {
                    FluidStack contents = property.getContents();

                    if (contents != null && contents.getFluid() != null) {
                        storedFluids.add(new Pair<>(contents.getFluid(), contents.amount)
                        );
                    }
                }

                fluids = storedFluids.toArray(new Pair[0]);
            }
        }

        return fluids;
    }

    private static ItemStack[] conductCraft(MachineRecipe recipe, IItemHandler itemHandler, IEnergyStorage energyStorage, IFluidHandler fluidHandler, @Nullable ItemStack specialInput) {

        if (itemHandler != null) {

            for (Ingredient ingredient : recipe.itemsIn) {

                ItemStack required = ingredient.getMatchingStacks()[0];

                int amount = required.getCount();

                for (int slot = 0; slot < itemHandler.getSlots() && amount > 0; slot++) {

                    ItemStack stored = itemHandler.getStackInSlot(slot);

                    if (stored.isEmpty()) {
                        continue;
                    }

                    if (!ingredient.apply(stored) || oreDictEqual(ingredient, Ingredient.fromStacks(stored))) {
                        continue;
                    }

                    int extracted = Math.min(amount, stored.getCount());

                    itemHandler.extractItem(slot, extracted, false);

                    amount -= extracted;
                }
            }
        }

        if (fluidHandler != null && recipe.fluidsIn != null) {

            for (Pair<Fluid, Integer> requiredFluid : recipe.fluidsIn) {

                fluidHandler.drain(new FluidStack(requiredFluid.first(), requiredFluid.second()), true);
            }
        }

        if (energyStorage != null) {
            energyStorage.extractEnergy(recipe.energyIn, false);
        }

        if (itemHandler != null && recipe.itemsOut != null) {

            return recipe.itemsOut;
        }

         /*
         This is something to pay attention two: machines which are being
         activated by pressing on external items can ONLY return ONE ITEM

         (not that currently there are any machines that output more than one item... or are supposed to have more than
         one fluid tank... but I'm always trying to think big
         */

        if (specialInput != null && recipe.specialInput.equals(Ingredient.fromStacks(specialInput))) {

            if (!recipe.machineType.isClickWith()) {
                specialInput.shrink(1);
            }

            return new ItemStack[]{recipe.itemsOut[0]};
        }

        return null;
    }

    private static boolean oreDictEqual(Ingredient one, Ingredient two) {
        return OreDictionary.itemMatches(one.getMatchingStacks()[0], two.getMatchingStacks()[0], false);
    }

    public static MachineRecipe findRecipe(
            ModMachineBase machine,
            ItemStack[] itemsIn,
            Pair<Fluid, Integer>[] fluidsIn,
            int energyIn,
            ItemStack specialInput) {

        for (MachineRecipe recipe : modRecipes) {

            if (recipe.machineType != machine) {
                continue;
            }

            if (energyIn < recipe.energyIn) {
                continue;
            }

            if (recipe.fluidsIn.length != fluidsIn.length) {
                continue;
            }

            boolean fluidsMatch = true;

            for (Pair<Fluid, Integer> recipeFluid : recipe.fluidsIn) {

                boolean found = false;

                for (Pair<Fluid, Integer> inputFluid : fluidsIn) {

                    if (recipeFluid.second().equals(inputFluid.second())
                            && recipeFluid.first().equals(inputFluid.first())) {

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    fluidsMatch = false;
                    break;
                }
            }

            if (!fluidsMatch) {
                continue;
            }

            if (recipe.itemsIn.length != itemsIn.length) {
                continue;
            }

            boolean[] used = new boolean[itemsIn.length];
            boolean itemsMatch = true;

            for (Ingredient ingredient : recipe.itemsIn) {

                boolean found = false;

                for (int i = 0; i < itemsIn.length; i++) {

                    if (used[i]) {
                        continue;
                    }

                    if (itemsIn[i].isEmpty()) {
                        continue;
                    }

                    if (ingredient.apply(itemsIn[i])) {
                        used[i] = true;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    itemsMatch = false;
                    break;
                }
            }

            if (itemsMatch && recipe.specialInput.equals(Ingredient.fromStacks(specialInput))) {
                return recipe;
            }
        }

        return null;
    }

}
