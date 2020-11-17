package org.javawebstack.command;

import org.javawebstack.injector.Injector;

import java.util.*;

public class CommandSystem {

    private final MultiCommand mainCommand = new MultiCommand();
    private Injector injector;

    public CommandSystem addCommand(String name, Command command){
        mainCommand.add(name, command);
        return this;
    }

    public CommandSystem setInjector(Injector injector){
        this.injector = injector;
        return this;
    }

    public Injector getInjector() {
        return injector;
    }

    public CommandResult eval(String[] input){
        List<String> args = new ArrayList<>();
        Map<String, List<String>> params = new HashMap<>();

        for(int i=0; i<input.length; i++){
            if(input[i].startsWith("--")){
                String value = i+1==input.length?"":input[i+1];
                String key = input[i].substring(2);
                List<String> list = params.containsKey(key) ? params.get(key) : new ArrayList<>();
                list.add(value);
                params.put(key, list);
                i++;
                continue;
            }
            if(input[i].startsWith("-")) {
                for(char c : input[i].substring(1).toCharArray()) {
                    String key = String.valueOf(c);
                    List<String> list = params.containsKey(key) ? params.get(key) : new ArrayList<>();
                    list.add("");
                    params.put(key, list);
                }
                continue;
            }
            args.add(input[i]);
        }

        return mainCommand.execute(this, args, params);
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
