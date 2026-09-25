package micromechanica.util.recipes;

import akka.japi.Pair;
import micromechanica.common.templates.ModMachineBase;
import micromechanica.util.StorageTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.IItemHandler;

public class MachineRecipe {

    int energyIn;
    Ingredient[] itemsIn;
    Pair<Fluid, Integer>[] fluidsIn;
    ItemStack[] itemsOut;
    Pair<Fluid, Integer>[] fluidsOut;
    int energyOut;
    ModMachineBase machineType;
    ItemStack machineStack;
    Ingredient specialInput;
    int requiredProgress;
    boolean hold;

    /*
    false -> recipe progress is increased by clicking mouse button. For processes like crushing
    true -> recipe progress is increased by holding mouse button. For processes like stitching and welding
     */

    /*
    "hold" is to define should you hold right mouse button or simply click it a certain amount of times to craft something
     */

    public static class MachineRecipeFactory {

        MachineRecipeFactory factory;
        MachineRecipe recipe;

        public MachineRecipeFactory() {}

        public MachineRecipeFactory begin(ItemStack machine, int requiredProgress, boolean hold) {
            factory = new MachineRecipeFactory();
            recipe = new MachineRecipe();
            recipe.machineType = (ModMachineBase) machine.getItem();
            recipe.machineStack = machine;
            return factory;
        }

        public MachineRecipeFactory setEnergyIn(int value) {

            if (recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.ENERGY) != null) {
                recipe.energyIn = value;
            } else {
                this.crash();
            }

            return factory;
        }

        public MachineRecipeFactory setItemsIn(Object... value) {

            if (recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.ITEMS) != null) {

                if (((IItemHandler)recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.ITEMS)).getSlots() <= value.length) {

                    recipe.itemsIn = new Ingredient[value.length];

                    for (int i = 0; i < value.length; i++) {

                        if (value[i] instanceof Item) {
                            recipe.itemsIn[i] = Ingredient.fromItem((Item) value[i]);
                        } else if (value[i] instanceof Block) {
                            recipe.itemsIn[i] = Ingredient.fromStacks(new ItemStack((Block) value[i]));
                        } else if (value[i] instanceof Ingredient) {
                            recipe.itemsIn[i] = (Ingredient) value[i];
                        } else if (value[i] instanceof ItemStack) {
                            recipe.itemsIn[i] = Ingredient.fromStacks((ItemStack) value[i]);
                        }
                    }

                }
            } else {
                this.crash();
            }

            return factory;
        }

        public MachineRecipeFactory setSpecialInput(Object input) {

            Ingredient ingr = Ingredient.EMPTY;

            if (input instanceof Item) {
                ingr = Ingredient.fromItem((Item) input);
            } else if (input instanceof Block) {
                ingr = Ingredient.fromStacks(new ItemStack((Block) input));
            } else if (input instanceof Ingredient) {
                ingr = (Ingredient) input;
            } else if (input instanceof ItemStack) {
                ingr = Ingredient.fromStacks((ItemStack) input);
            }

            recipe.specialInput = ingr;

            return factory;
        }

        public MachineRecipeFactory setFluidIn(Pair<Fluid, Integer>... value) {

            if (recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.FLUID) != null) {

                recipe.fluidsOut = new Pair[value.length];

                for (int i = 0; i < value.length; i++) {
                    recipe.fluidsIn[i] = value[i];
                }
            } else {
                this.crash();
            }
            return factory;
        }

        public MachineRecipeFactory setItemsOut(Object... value) {

            if (recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.ITEMS) != null) {

                if (((IItemHandler)recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.ITEMS)).getSlots() <= value.length) {

                    recipe.itemsIn = new Ingredient[value.length];

                    for (int i = 0; i < value.length; i++) {

                        if (value[i] instanceof Item) {
                            recipe.itemsOut[i] = new ItemStack((Item) value[i]);
                        } else if (value[i] instanceof Block) {
                            recipe.itemsOut[i] = new ItemStack((Block) value[i]);
                        } else if (value[i] instanceof ItemStack) {
                            recipe.itemsOut[i] = ((ItemStack) value[i]);
                        }
                    }

                }

                } else {
                this.crash();
            }

            return factory;
        }

        public MachineRecipeFactory setFluidsOut(Pair<Fluid, Integer>... value) {

            if (recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.FLUID) != null) {

                recipe.fluidsIn = new Pair[value.length];

                for (int i = 0; i < value.length; i++) {
                    recipe.fluidsOut[i] = value[i];
                }
            } else {
                this.crash();
            }
            return factory;
        }

        public MachineRecipeFactory setEnergyOut(int value) {

            if (recipe.machineType.getRealStorage(recipe.machineStack, StorageTypes.ENERGY) != null) {
                recipe.energyOut = value;
            } else {
                this.crash();
            }
            return factory;
        }

        public MachineRecipe write() {
            return recipe;
        }

        private void crash() {
            throw new NullPointerException("Tried adding a value with no storage for it provided!");
        }

    }

}
