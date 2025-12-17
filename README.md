# Stockton-TSP-Project
A TSP implementation across the Stockton University Campus, designed to recommend routes based on scheduling requirements.


**To launch website:**

To start, please run the [MainServer.java](/src/main/java/MainServer.java) file.

![AfterDonePrt1]( /Documentation/Pictures/Launch-Server.png "This is the normal message for the output.")

Go into your Browser of choice and run [this link](http://localhost:8080) and it should bring you right to the homepage.

![The initial index page with some random points for reference.](/Documentation/Pictures/Main-Index-Prt2.png "This is just a sample image that was before the initial points. There will be different points for the actual campus for you.")

It will show the points on the left:

![Index Points](/Documentation/Pictures/Before-ClickingPrt2.png "Again, these are random points. These won't be the ones on your screen.")

And the rest of the map on the right. 

You can select multiple different points, to show from where you're coming from to where you're trying to head to.

![After clicking...](/Documentation/Pictures/After-Clicking-Prt2.png "A-Wing(100s) and D-Wing(100s) Were added to the list.")

I have added 'A' and 'D' to the list by clicking the name, and then clicking the 'Add Point' Button.

You can then either add more points, or you can press 'Calculate Path' which will output the points and tell you the total Feet that will be needed to travel:

![Final Submission Screenshot](/Documentation/Pictures/Final-SS.png "This will be the actual pathways for you, but for simplicity sake lets use letters.")

As you can see, it tells us to go from C -> D -> A instead of the route we were taking before.

You can then press the 'Clear Path' button, which will clear the entire path, or add addition points to go to the next place you must attend.


**A [Report](/Documentation/TICS-FInal-Report.md) of all implementation and steps processed is located in the Report Folder.**

