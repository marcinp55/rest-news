To run the project:

1. Download release from https://sourceforge.net/projects/rest-news/files/ (I can't upload executables to GitHub).
2. Extract archive into folder. It should be a .jar file and WebContent folder with frontend files.
3. Run .jar file through command line (cmd) using command (  java -jar rest-news-v1.jar  ). It should start on localhost on port 8080. 
4. Open WebContent folder and run index.html file. The app should be working fine now. If you want to use another port or it didn't start on port 8080, just change apiRoot variable in script.js file to appropriate port showed in command line after executing jar file.

If you don't want to start it by .jar file you can import the project from GitHub into Eclipse EE, run RestNewsApplication after getting all dependencies and start index.html file. You can try launching it on IntelliJ, but I didn't test it because I don't have active licence now.

If there are any problems feel free to contact me.

Documentation available at http://localhost:8080/swagger-ui.html when using config provided in project.
