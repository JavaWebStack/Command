package org.javawebstack.command;

import java.util.*;

public class CommandSystem {

    private final MultiCommand mainCommand = new MultiCommand();

    public CommandSystem addCommand(String name, Command command){
        mainCommand.add(name, command);
        return this;
    }

    public CommandResult eval(String[] input){
        List<String> args = new ArrayList<>();
        Map<String, String> params = new HashMap<>();

        for(int i=0; i<input.length; i++){
            if(input[i].startsWith("--")){
                String value = i+1==input.length?"":input[i+1];
                params.put(input[i].substring(2), value);
                i++;
                continue;
            }
            if(input[i].startsWith("-")) {
                for(char c : input[i].substring(1).toCharArray())
                    params.put(String.valueOf(c), "");
                continue;
            }
            args.add(input[i]);
        }

        return mainCommand.execute(args, params);
    }

    public void run(String[] input){
        CommandResult result = eval(input);
        switch (result.getState()){
            case "notFound":
                System.err.println("Command '"+result.getError()+"' not found!");
                break;
            case "commandList": {
                System.out.println("Available Commands:");
                for(String command : result.getCommandList())
                    System.out.println("\t" + command);
                break;
            }
            case "error":
                System.err.println("Error: " + result.getError());
                break;
        }
    }

}
