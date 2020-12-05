package mindustry.plugin.discord;

import arc.util.CommandHandler;
import arc.util.Log;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

import static mindustry.plugin.discord.Loader.*;

/** Represents a registry of commands */
public class DiscordCommands extends ListenerAdapter {
    private boolean stop = false;
    public DiscordCommands() {
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // disable DMs & self messages
        if(event.isFromType(ChannelType.PRIVATE)) return;
        if(event.getMessage().getAuthor() == api.getSelfUser()) return;

        Member member = event.getMember();
        if(member == null) return;

        Context ctx = new Context(event);
        String content = event.getMessage().getContentRaw();

        List<Role> roles = member.getRoles();

        handleCommand(bt.publicHandler, content, ctx);
        handleCommand(bt.reviewerHandler, content, ctx);
        handleCommand(bt.moderatorHandler, content, ctx);

        /*
        if(!handleCommand(bt.publicHandler, content, ctx)){
            if(roles.contains(mapreviewer)){
                if(handleCommand(bt.reviewerHandler, content, ctx));
            }
            if(roles.contains(moderator)){
                Log.info("trying for mod");
                if(handleCommand(bt.moderatorHandler, content, ctx));
            }
        }
         */
    }

    public boolean handleCommand(CommandHandler handler, String contentRaw, Context ctx){
        boolean returnant = true;
        CommandHandler.CommandResponse response = handler.handleMessage(contentRaw, ctx);

        if (response.type != CommandHandler.ResponseType.noCommand) {
            //a command was sent, now get the output
            if(response.type != CommandHandler.ResponseType.valid){
                //send usage
                if(response.type == CommandHandler.ResponseType.manyArguments){
                    ctx.sendEmbed(false,":interrobang: **too many arguments**", "**usage:** " + prefix + response.command.text + " " + response.command.paramText);
                }else if(response.type == CommandHandler.ResponseType.fewArguments){
                    ctx.sendEmbed(false,":interrobang: **too few arguments**", "**usage:** " + prefix + response.command.text + " " + response.command.paramText);
                }else{ //unknown command
                    returnant = false;
                }
            }
        }
        return returnant;
    }
}