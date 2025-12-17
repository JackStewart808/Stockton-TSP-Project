# Stockton-TSP-Project
A TSP implementation across the Stockton University Campus, designed to recommend routes based on scheduling requirements.


**To launch website (IN THEORY):**

When you first launch the Index.html file, this is what will appear.

![The initial index page with some random points for reference.](/Documentation/Pictures/Main-Index.png "This is just a sample image that was before the initial points. There will be different points for the actual campus for you.")

It will show the points on the left:

![Index Points](/Documentation/Pictures/Index-Points.png "Again, these are random points. These won't be the ones on your screen.")

And the rest of the map on the right. 

You can select multiple different points, to show from where you're coming from to where you're trying to head to.

![After clicking...](/Documentation/Pictures/After-Clicking.png "Buffalo and New York were added")

I have added 'Buffalo' and 'New York' to the list by Clicking the name, and then clicking the 'Add Point' Button.

You can then either add more points, or you can press 'Calculate Path which will output the points and tell you the total Feet that will be needed to travel:

![This is where we put the FINAL feet estimation screenshot.](/ "")

Or you can press the 'Clear Path' button, which will clear the entire path.


***IF JAVA IS STILL DISCONNECTED:***

Go into the Terminal and type in:
```
cd .../Stockton-TSP-Project
```
So that you are in the folder and reading the contents.

![In Case SS1](/Documentation/Pictures/In-Case-SS1.png "This is just to show how to access the folder from command prompt.")

Then after you're in the folder, type:

```
mvn clean package
```

You should then get a message like this:

![Message-Incase-1](/Documentation/Pictures/InCase-2-Build-Success.png "Build Sucess for a Maven clean install and to make sure it's still able to run.")

Afterwards you should run this command:

```
java -cp target/stocktonTSP-0.1.0-jar-with-dependencies.jar TestMapBuilder
```

![Final Output](/Documentation/Pictures/InCase-3-NoWeb.png)]

This is the output that the website map *SHOULD* show, just not as clean and neat.


**A [Report](/Documentation/TICS-FInal-Report.md) of all implementation and steps processed is located in the Report Folder.**

