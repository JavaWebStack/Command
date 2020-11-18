package org.javawebstack.command;

import java.util.Arrays;
import java.util.List;

public class CommandResult {

    private final String state;
    private String error;
    private List<String> commandList = null;

    private CommandResult(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public List<String> getCommandList(){
        return commandList;
    }

    public String getError() {
        return error;
    }

    public static CommandResult success(){
        return new CommandResult("success");
    }

    public static CommandResult commandList(List<String> commands){
        CommandResult result = new CommandResult("commandList");
        result.commandList = commands;
        return result;
    }

    public static CommandResult commandList(String... commands){
        return commandList(Arrays.asList(commands));
    }

    public static CommandResult error(Throwable throwable){
        return error(throwable.getMessage());
    }

    public static CommandResult error(String error){
        CommandResult result = new CommandResult("error");
        result.error = error;
        return result;
    }

    public static CommandResult syntax(String syntax){
        CommandResult result = new CommandResult("syntax");
        result.error = syntax;
        return result;
    }

    public static CommandResult notFound(String command){
        CommandResult result = new CommandResult("notFound");
        result.error = command;
        return result;
    }

}
