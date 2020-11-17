package org.javawebstack.command;

import java.util.List;
import java.util.Map;

public interface Command {

    CommandResult execute(CommandSystem system, List<String> args, Map<String, List<String>> params);

}
