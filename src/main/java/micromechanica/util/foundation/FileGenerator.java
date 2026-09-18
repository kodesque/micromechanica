package micromechanica.util.foundation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import micromechanica.api.INotCube;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class FileGenerator {

    public static final Gson GSON = new Gson();

    public static Path STATES = Paths.get("src/main/resources/assets/aldynamica/blockstates");
    public static Path MODELBLOCK = Paths.get("src/main/resources/assets/aldynamica/models/block");
    public static Path MODELITEM = Paths.get("src/main/resources/assets/aldynamica/models/item");

    public static void generateBlockFiles(Block block) {

        IBlockState state = block.getDefaultState();

        generateStates(state);
        generateBlockModels(state);

        //generate blockstates.json based on information from the block
        //if a model for block isn't found, preferred model is considered to be the basic model
        //if OBJ, don't generate an ItemBlock, but insert display information into blockstate.json instead

        //or simply generate states with display for all blocks, not only for OBJ

    }

    private static void generateStates(IBlockState state) {

        JsonObject root = new JsonObject();

        Collection<IProperty<?>> props = state.getPropertyKeys();

        StringBuilder fullname = new StringBuilder();

        for (IProperty<?> map1 : props) {

            int i = 0;

            for (IProperty<?> map2 : props) {

                i++;

                for (IProperty<?> map3 : props) {

                    if (map3 instanceof PropertyBool) {

                        boolean value = i == 0 ? false : true;

                        fullname.append(map3.getName().toLowerCase() + "=" + value + ",");

                    } else if (map3 instanceof PropertyInteger) {

                        fullname.append(map3.getName().toLowerCase() + "=" + i + ",");

                    } else if (map3 instanceof PropertyDirection) {

                        fullname.append(map3.getName().toLowerCase() + "=" + EnumFacing.byIndex(i) + ",");
                    }
                }

                root.addProperty(fullname.toString(), state.getBlock().getRegistryName().toString());
            }
        }


        try (FileWriter writer = new FileWriter(new File(STATES.toString(), state.getBlock().getRegistryName().getPath()))) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void generateBlockModels(IBlockState state) {

        //separate logic for columns

        File[] files = new File(STATES.toString()).listFiles();
        JsonObject root = new JsonObject();

        String shortname = state.getBlock().getRegistryName().getPath();

        for (File file : files) {
            if (file.getName().equals(shortname))
                return;
        }

        if (state.getBlock() instanceof INotCube)
            return;

        root.addProperty("parent", "block/cube_all");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", "blocks/" + shortname);

        root.add("textures", textures);

        File actual = new File(MODELBLOCK.toString(), shortname);

        try (FileWriter writer = new FileWriter(actual)) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateItemBlockFiles() {

    }

    public static void generateItemFiles() {

    }

}
