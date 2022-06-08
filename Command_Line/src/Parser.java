import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;

class Parser {

    String commandName;
    String[] args;
    boolean isArgProblem = false;

//This method will divide the input into commandName and args
//where "input" is the string command entered by the user 
    public boolean parse(String input) {
        String[] commandParts = input.split(" ");
        //filtering command name
        this.commandName = commandParts[0];
        // validating then filter the arguments for every command
        switch (this.commandName) {
            case "echo":
                if (commandParts.length != 2) {
                    this.isArgProblem = true;
                    return false;
                }
                args = new String[1];
                args[0] = commandParts[1];
                return true;
            case "pwd":
                if (commandParts.length != 1) {
                    this.isArgProblem = true;
                    return false;
                }
                return true;
            case "ls":
                if (commandParts.length > 2) {
                    this.isArgProblem = true;
                    return false;
                }
                if(commandParts.length == 2){
                    args = new String[1];
                    args[0] = commandParts[1];
                }
                return true;
            case "cd":
                if (commandParts.length > 2) {
                    this.isArgProblem = true;
                    return false;
                }
                if(commandParts.length == 2){
                    args = new String[1];
                    args[0] = commandParts[1];
                }
                return true;
             case "mkdir":
            	if(commandParts.length != 2) {
            		this.isArgProblem = true;
                    return false;
            	}
            	if(commandParts.length == 2) {
            	//here argument size will be the commandparts - first one which is the command itself
            	args = new String[commandParts.length-1];
                args = removefirst(commandParts);
                return true;
            	}
            case "rmdir":
            	if(commandParts.length != 2) {
            		this.isArgProblem = true;
                    return false;
            	}
            	if(commandParts.length == 2) {
            		//here argument size will be the commandparts - first one which is the command itself
                	args = new String[commandParts.length-1];
                    args = removefirst(commandParts);
                    return true;
            	}
            case "touch":
            	if(commandParts.length != 2) {
            		this.isArgProblem = true;
                    return false;
            	}
            	if(commandParts.length == 2) {
            	//here argument size will be the commandparts - first one which is the command itself
            	args = new String[commandParts.length-1];
                args = removefirst(commandParts);
                return true;
            	}
            case "cp":
            	
            	if (commandParts.length == 3) {
            		args = new String[2];
                    args[0] = commandParts[1];
                    args[1] = commandParts[2];
               	}
            	if (commandParts.length == 4) {
            		args = new String[3];
                    args[0] = commandParts[1];
                    args[1] = commandParts[2];
                    args[2] = commandParts[3];
               	}
            	
            	if (commandParts.length != 3 && commandParts.length != 4) {
                		this.isArgProblem = true;
             		return false;
            	}
            	return true;
            case "rm":
            	if (commandParts.length == 2) {
            		 args = new String[1];
                     args[0] = commandParts[1];
            	}
            	return true;
            case "cat":
            	if (commandParts.length == 2) {
            		args = new String[1];
                    args[0] = commandParts[1];
            	}
            	if (commandParts.length == 3) {
            		args = new String[2];
                    args[0] = commandParts[1];
                    args[1] = commandParts[2];
            	}
            	return true;
            case "exit":
                return true;
            default:
                System.out.println("no such command");
            return false;
        }
    }
    
     //function to remove the first element 
    public String[] removefirst(String[] array) {

    	for(int i = 0; i < array.length; i++)
    	{
    	      if( array[i] == null ? array[0] == null : array[i].equals(array[0]))
    	      {
    	        // shift elements to the left
    	        for(int j=i ; j<array.length-1 ; j++)
    	        {
    	        	array[j] = array[j+1];
    	        }
    	        break;
    	      }
    	    }
		return array;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public String[] getArgs() {
        return this.args;
    }
    
    public boolean getIsArgProblem() {
        return this.isArgProblem;
    }

}