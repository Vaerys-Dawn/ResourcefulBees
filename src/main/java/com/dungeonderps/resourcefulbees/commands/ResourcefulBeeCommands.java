        package com.dungeonderps.resourcefulbees.commands;

        import com.dungeonderps.resourcefulbees.config.ResourcefulBeesConfig;
        import com.mojang.brigadier.Command;
        import com.mojang.brigadier.CommandDispatcher;
        import net.minecraft.command.CommandSource;
        import net.minecraft.command.Commands;
        import net.minecraft.entity.player.ServerPlayerEntity;
        import net.minecraft.item.ItemStack;
        import net.minecraft.util.Hand;
        import net.minecraft.util.text.StringTextComponent;
        import net.minecraft.util.text.TextFormatting;

        import static com.dungeonderps.resourcefulbees.ResourcefulBees.LOGGER;

public class ResourcefulBeeCommands
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        LOGGER.info("got to command dispatcher");
        dispatcher.register(Commands.literal("resourcefulbees").requires(rq -> rq.hasPermissionLevel(2))
                .then(Commands.literal("help")
                        .executes(context -> help(context.getSource().asPlayer()))
                )
        );
    }

    private static int help(ServerPlayerEntity player)
    {
        //TODO Update this with better way of sending data, the ability to click on it to copy to clipboard, and get item tags.
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        player.sendMessage(new StringTextComponent("Item: " + stack.getItem().getRegistryName().toString()));
        return Command.SINGLE_SUCCESS;
    }
}