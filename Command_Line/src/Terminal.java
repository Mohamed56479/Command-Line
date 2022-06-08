import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Terminal {

    Parser parser = new Parser();
    //I created that variable to keep track of the current path
    String currentPath = System.getProperty("user.dir");
    
//Implement each command in a method, for example:
    
    public void echo(String[] args) {
        System.out.println(args[0]);
    }

    public void pwd() {
        //testing argument
        System.out.println(currentPath);
    }
    
    public void ls(String[] args) {
        //testing argument
        File f = new File(currentPath);
        String[] ar = f.list();
        try{
            if(args[0].equals("-r")){
                for(int i=ar.length-1;i>=0;i--)
                    System.out.print(ar[i] + "  ");
                System.out.print("\n");
            }else{
                System.out.println( "bad arguments" );
            }
        }catch(NullPointerException e){
            for(int i=0;i<ar.length;i++)
                System.out.print(ar[i] + "  ");
            System.out.print("\n");
        }
    }
    
    public void cd(String[] args){
        //testing argument
        try{
            if(args[0].equals("..")){
                int rmv = currentPath.lastIndexOf("\\");
                currentPath = currentPath.substring(0,rmv);
            }
            //absolute path
            else if(args[0].contains(":")){
                if(Files.exists(Paths.get(args[0])))
                    this.currentPath = args[0];
                else System.out.println("path is not valid");
            }
            //relative path
            else{
                String newPath = this.currentPath+"\\"+args[0];
                
                if(Files.exists(Paths.get(newPath)))
                    this.currentPath = newPath;
                else System.out.println("path is not valid");
            }
        }catch(NullPointerException e){
            this.currentPath = System.getProperty("user.dir");
        }
    }
    
    public void mkdir(String[] args) {
    	
        //testing argument
    		String filename =args[0];
    		File file = new File(filename);
    		boolean bool = file.mkdirs();  
    	      if(bool){  
    	         System.out.println("Folder is created successfully");  
    	      }else{  
    	         System.out.println("Error Found!");  
    	      }  
    }
    
    public void touch(String[] args) throws IOException {
    	
        //testing argument
    		String filename =args[0];
    		File file = new File(filename);
    		 if (!file.isAbsolute()) {
    			 file = new File(currentPath + "\\" + filename);
    	        }
    	        if (file.exists()) {
    	            System.out.println("File exists, cannot create this folder");
    	        } else
    	        	file.createNewFile();

    }
    
    public void rmdir(String[] args) {
	
    //testing argument
	if(args[0].equals("*"))
	{
		boolean isFinished = false;
		do {
	        isFinished = true;
	        delete_empty(currentPath);
	    } while (!isFinished);
	}
	else
	{
		String filename =args[0];
		File file = new File(filename);
		if (file.isDirectory()) 
		{
		    String[] list = file.list();
		    if (list == null || list.length == 0) 
		    {
		    	file.delete();
		    } 
		    else 
		    {
		        System.out.println("Dirctory is not Empty");
		    }
		} 
		else 
		{
		    System.out.println("Wrong Directory!");
		}

	}
    }

    public void cp(String[] args){
    	int i;
    	FileInputStream in;
    	FileOutputStream out;
    	
    	try {
    		if(args[0].equals("-r")){

        		File sFile =new File(args[1]) ;
            	File dFile =new File(args[2]) ;
    			copyFolder(sFile,dFile);
    		}
    		else {

        		File sFile =new File(args[0]) ;
            	File dFile =new File(args[1]) ;
            	in = new FileInputStream(sFile);
            	out = new FileOutputStream(dFile);
            	while((i=in.read())!=-1) {
            		out.write(i);
            	}
            	if (in!=null)
            		in.close();
            	if (out!=null)
            		out.close();
    	}}
    	
    	catch (Exception e) {
    		
    	}
    	
    	
    }
    
    public void rm(String[] args){
    	File file =new File(args[0]);
    	try {
    		file.delete(); 
       	}
    	catch(Exception e){  
    	}
    }
    
    public void cat(String[] args) throws IOException {
    	try {
    		if(args.length==1) {
    		BufferedReader file = new BufferedReader(new FileReader(args[0]));
    		String line;
    		while ((line = file.readLine()) != null) {
    		       System.out.println(line);
    		   }
		 }
    		else if(args.length==2) {
    			BufferedReader file1 = new BufferedReader(new FileReader(args[0]));
    			BufferedReader file2 = new BufferedReader(new FileReader(args[1]));
    			String line;
        		while ((line = file1.readLine()) != null) {
        		       System.out.println(line);
        		   }
        		while ((line = file2.readLine()) != null) {
     		       System.out.println(line);
     		   }
    		}
    		
    	} catch (FileNotFoundException e) {
		
		}
    	
    }
    
    public static void copyFolder(File s, File d)
    {
        if (s.isDirectory())
        {
            if (!d.exists())
            {
                d.mkdirs();
            }

            String files[] = s.list();

            for (String file : files)
            {
                File sFile = new File(s, file);
                File dFile = new File(d, file);

                copyFolder(sFile, dFile);
            }
        }
        else
        {
            InputStream in;
            OutputStream out;

            try
            {
                in = new FileInputStream(s);
                out = new FileOutputStream(d);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0)
                {
                    out.write(buffer, 0, length);
                }
                if (in!=null)
                	in.close();
                if (out!=null)
                	out.close();
            }
            catch (Exception e)
            {
              
            }
        }
    }    
    
    private static void delete_empty(String fileLocation) {
    	File folder = new File(fileLocation);
        File[] listofFiles = folder.listFiles();
        if (listofFiles.length == 0) {
            System.out.println("Folder Name :: " + folder.getAbsolutePath() + " is deleted.");
            folder.delete();
            boolean isFinished = false;
        } else {
            for (int j = 0; j < listofFiles.length; j++) {
                File file = listofFiles[j];
                if (file.isDirectory()) {
                    delete_empty(file.getAbsolutePath());
                }
            }
        }
    }
// ...
//This method will choose the suitable command method to be called

    public void chooseCommandAction() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("> ");
        String input = in.nextLine();
        boolean valid = parser.parse(input);
        if (valid) {
            //switch between commands
            switch (parser.getCommandName()) {
                case "echo":
                    this.echo(parser.getArgs());
                    break;
                case "pwd":
                    this.pwd();
                    break;
                case "ls":
                    this.ls(parser.getArgs());
                    break;
                case "cd":
                    this.cd(parser.getArgs());
                    break;
                case "mkdir":
                    this.mkdir(parser.getArgs());
                    break;
                case "touch":
                    this.touch(parser.getArgs());
                    break;
                case "rmdir":
                    this.rmdir(parser.getArgs());
                    break;
                case "cp":
                	this.cp(parser.getArgs());
                    break;
                case "rm":
                	this.rm(parser.getArgs());
                	break;
                case "cat":
                	try {
                		this.cat(parser.getArgs());
                	} catch (IOException e) {
                		e.printStackTrace();
                	}
                	break;
                case "exit":
                    throw new IllegalStateException();
                default:
                    System.out.println("error has occured!");
                    break;
            }
        }else{
            if(parser.getIsArgProblem())
            System.out.println("arguments number is not right");
        }
    }

    public static void main(String[] args) throws IOException {
        Terminal t = new Terminal();
        while (true) {
            try{
                t.parser = new Parser();
                t.chooseCommandAction();
            }catch(IllegalStateException e){
                break;
            }
        }
    }
}