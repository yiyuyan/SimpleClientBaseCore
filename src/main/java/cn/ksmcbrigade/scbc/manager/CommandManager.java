package cn.ksmcbrigade.scbc.manager;

import cn.ksmcbrigade.scbc.command.Command;
import cn.ksmcbrigade.scbc.commands.Help;
import cn.ksmcbrigade.scbc.commands.Pos;
import cn.ksmcbrigade.scbc.commands.Say;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public static ArrayList<Command> commands = new ArrayList<>();

    public static Help help = new Help();
    public static Pos pos = new Pos();
    public static Say say = new Say();

    public static void add(Command command){
        commands.add(command);
    }

    public static void del(Command command){
        commands.remove(command);
    }

    public static void del(int command){
        commands.remove(command);
    }

    public CommandManager(){
        commands.add(help);
        commands.add(pos);
        commands.add(say);
    }

    public static String getName(String command){
        String noPoint = command.substring(1);
        if(noPoint.contains(" ")){
            return noPoint.split(" ")[0];
        }
        else{
            return noPoint;
        }
    }

    public static String[] getArgs(String command){
        String noPoint = command.substring(1);
        if(noPoint.contains(" ")){
            String name = noPoint.split(" ")[0];
            ArrayList<String> LastArgs = new ArrayList<>(List.of(noPoint.split(" ")));
            ArrayList<String> args = new ArrayList<>();
            LastArgs.forEach(a -> {
                if(!a.equalsIgnoreCase(name)){
                    args.add(a);
                }
            });
            return args.toArray(new String[0]);
        }
        else{
            return new String[0];
        }
    }

    @Nullable
    public static Command get(String name){
        for(Command command:commands){
            if(command.name.equalsIgnoreCase(name)){
                return command;
            }
        }
        return null;
    }

    public static ArrayList<Command> getAll(){
        return commands;
    }
}
