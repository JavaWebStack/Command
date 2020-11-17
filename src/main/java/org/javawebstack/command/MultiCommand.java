package org.javawebstack.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiCommand implements Command {
    private final Map<String, Command> childCommands = new HashMap<>();
    public CommandResult execute(List<String> args, Map<String, String> params) {
        if(args.size() == 0)
            return CommandResult.commandList(new ArrayList<>(childCommands.keySet()));
        if(!childCommands.containsKey(args.get(0)))
            return CommandResult.notFound(args.get(0));
        List<String> childArgs = new ArrayList<>();
        for(int i=1; i<args.size(); i++)
            childArgs.add(args.get(i));
        return childCommands.get(args.get(0)).execute(childArgs, params);
    }
    public MultiCommand add(String name, Command command){
        childCommands.put(name, command);
        return this;
    }
}
