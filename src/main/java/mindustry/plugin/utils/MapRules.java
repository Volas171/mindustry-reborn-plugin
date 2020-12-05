package mindustry.plugin.utils;

import arc.util.Timer;
import mindustry.gen.Call;
import mindustry.maps.Map;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.state;
import static mindustry.Vars.world;
import static mindustry.plugin.ioMain.*;
import static mindustry.plugin.utils.Funcs.*;

public class MapRules {

    public static void onMapLoad(){

        Map map = state.map;

        // spawn all players quick for the first time
        float orig = state.rules.unitBuildSpeedMultiplier;
        state.rules.unitBuildSpeedMultiplier = 0.25f;
        Call.setRules(state.rules);
        Timer.schedule(() -> {
            state.rules.unitBuildSpeedMultiplier = orig;
            Call.setRules(state.rules);
        }, 5f);


        // display map description on core tiles for the first minute
        Call.infoToast("Playing [accent]" + escapeColorCodes(map.name()) + "[] by[accent] " + map.author(), 20f); // credit map makers

        if(map.description().equals("???unknown???")) return;
        for(Tile t : world.tiles){
            if(t.build != null && t.build.block instanceof CoreBlock){
                Call.label(map.description(), 20f, t.build.x, t.build.y);
            }
        }
    }

    public static void run(){
        onMapLoad();
        // reset minutes passed
        minutesPassed = 0;
    }
}
