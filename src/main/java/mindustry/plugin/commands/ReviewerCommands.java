package mindustry.plugin.commands;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.maps.Map;
import mindustry.plugin.discord.Context;
import mindustry.plugin.utils.Funcs;

import static mindustry.Vars.*;
import static mindustry.plugin.discord.Loader.*;
import static mindustry.plugin.utils.Funcs.*;

public class ReviewerCommands {
    public ReviewerCommands(){

    }

    public void registerCommands(CommandHandler handler){
        handler.<Context>register("removemap", "<map...>", "Remove the specified map from the playlist.", (args, ctx) -> {
            Map map = getMapBySelector(args[0]);
            if(map != null){
                maps.removeMap(map);
                maps.reload();
                ctx.sendEmbed(true, ":knife: **" + map.name() + "** was murdered successfully");
            } else{
                ctx.sendEmbed(false, ":knife: *" + Funcs.escapeCharacters(args[0]) + "* not found", "display all maps with **" + prefix + "maps**");
            }
        });

        handler.<Context>register("changemap", "[map...]", "Change the map to the provided one, or to a random one if no map is provided. Applies to " + serverName, (args, ctx) -> {
            if(args.length > 0){
                Map map = getMapBySelector(args[0]);
                if(map != null){
                    changeMap(map);
                    ctx.sendEmbed(true, ":mountain_snow: map changed to "  + escapeCharacters(map.name()));
                }else{
                    ctx.sendEmbed(false, ":mountain_snow: *" + Funcs.escapeCharacters(args[0]) + "* not found", "display all maps with **" + prefix + "maps**");
                }
            }else{
                Events.fire(new EventType.GameOverEvent(Team.sharded));
                ctx.sendEmbed(true, ":mountain_snow: gameover event trigerred");
            }
        });
    }
}