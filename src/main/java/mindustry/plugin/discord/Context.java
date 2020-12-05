package mindustry.plugin.discord;

import mindustry.plugin.utils.Funcs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

/** Represents the context in which a command was called */
public class Context {
    /** Source event */
    public MessageReceivedEvent event;
    public TextChannel channel;
    public User author;

    public Context(MessageReceivedEvent event) {
        this.event = event;
        this.channel = event.getTextChannel();
        this.author = event.getAuthor();
    }

    public void sendEmbed(EmbedBuilder eb){
        channel.sendMessage(eb.build()).queue();
    }

    public void sendEmbed(String title){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        channel.sendMessage(eb.build()).queue();
    }

    public void sendEmbed(boolean success, String title){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        if(success){
            eb.setColor(Funcs.Pals.success);
        } else{
            eb.setColor(Funcs.Pals.error);
        }
        channel.sendMessage(eb.build()).queue();
    }

    public void sendEmbed(String title, String description){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        channel.sendMessage(eb.build()).queue();;
    }

    public void sendEmbed(boolean success, String title, String description){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        if(success){
            eb.setColor(Funcs.Pals.success);
        } else{
            eb.setColor(Funcs.Pals.error);
        }
        channel.sendMessage(eb.build()).queue();;
    }

    public void sendEmbed(boolean success, String title, String description, HashMap<String, String> fields, boolean inline){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        if(success){
            eb.setColor(Funcs.Pals.success);
        } else{
            eb.setColor(Funcs.Pals.error);
        }
        for(String name : fields.keySet()){
            String desc = fields.get(name);
            eb.addField(name, desc, inline);
        }
        channel.sendMessage(eb.build()).queue();;
    }

    public void sendEmbed(boolean success, String title, HashMap<String, String> fields, boolean inline){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        if(success){
            eb.setColor(Funcs.Pals.success);
        } else{
            eb.setColor(Funcs.Pals.error);
        }
        for(String name : fields.keySet()){
            String desc = fields.get(name);
            eb.addField(name, desc, inline);
        }
        channel.sendMessage(eb.build()).queue();;
    }

    public void sendEmbed(String title, String description, HashMap<String, String> fields){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        for(String name : fields.keySet()){
            String desc = fields.get(name);
            eb.addField(name, desc, false);
        }
        channel.sendMessage(eb.build()).queue();;
    }
}
