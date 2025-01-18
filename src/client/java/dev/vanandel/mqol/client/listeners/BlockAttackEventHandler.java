package dev.vanandel.mqol.client.listeners;

import dev.vanandel.mqol.client.InventoryUtils;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class BlockAttackEventHandler implements AttackBlockCallback {
    private static final Map<Item, Integer> TOOL_LEVELS = new HashMap<>();

    static {
        TOOL_LEVELS.put(Items.WOODEN_SHOVEL, 1);
        TOOL_LEVELS.put(Items.STONE_SHOVEL, 2);
        TOOL_LEVELS.put(Items.IRON_SHOVEL, 3);
        TOOL_LEVELS.put(Items.DIAMOND_SHOVEL, 4);
        TOOL_LEVELS.put(Items.NETHERITE_SHOVEL, 5);
        TOOL_LEVELS.put(Items.WOODEN_PICKAXE, 1);
        TOOL_LEVELS.put(Items.STONE_PICKAXE, 2);
        TOOL_LEVELS.put(Items.IRON_PICKAXE, 3);
        TOOL_LEVELS.put(Items.DIAMOND_PICKAXE, 4);
        TOOL_LEVELS.put(Items.NETHERITE_PICKAXE, 5);
        TOOL_LEVELS.put(Items.WOODEN_AXE, 1);
        TOOL_LEVELS.put(Items.STONE_AXE, 2);
        TOOL_LEVELS.put(Items.IRON_AXE, 3);
        TOOL_LEVELS.put(Items.DIAMOND_AXE, 4);
        TOOL_LEVELS.put(Items.NETHERITE_AXE, 5);
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos);

        if (!player.isSpectator()) {
            String toolSuffix = getPreferredToolSuffix(state);
            int preferredToolSlot = findPreferredToolInInventory(player, toolSuffix);

            if (preferredToolSlot != -1) {
                int selectedSlot = player.getInventory().selectedSlot + 36;
                if (preferredToolSlot < 9) {
                    // Tool is already in the hotbar, just switch to that slot
                    player.getInventory().selectedSlot = preferredToolSlot;
                } else if (player.getStackInHand(hand).isEmpty()) {
                    // Tool is in the inventory and hand is empty, swap it to the selected slot
                    InventoryUtils.swapItems(player, preferredToolSlot, selectedSlot);
                }
            }
        }

        return ActionResult.PASS;
    }

    private String getPreferredToolSuffix(BlockState state) {
        if (state.isIn(BlockTags.SHOVEL_MINEABLE)) {
            return "_shovel";
        } else if (state.isIn(BlockTags.PICKAXE_MINEABLE)) {
            return "_pickaxe";
        } else if (state.isIn(BlockTags.AXE_MINEABLE)) {
            return "_axe";
        } else {
            return "";
        }
    }

    private int findPreferredToolInInventory(PlayerEntity player, String toolSuffix) {
        int bestToolSlot = -1;
        int bestToolLevel = -1;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (!stack.isEmpty() && stack.getItem().toString().endsWith(toolSuffix)) {
                int toolLevel = TOOL_LEVELS.getOrDefault(stack.getItem(), 0);

                if (toolLevel > bestToolLevel) {
                    bestToolLevel = toolLevel;
                    bestToolSlot = i;
                }
            }
        }
        return bestToolSlot;
    }
}