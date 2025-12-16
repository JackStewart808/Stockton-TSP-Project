# Topics in Computer Science: Applied Evolutionary Computation Stockton TSP Project Report

###### Team Members: Jack Stewart, Sajjad Haider, Matthew Houseworth, Kyle McFadden and Justin Murphy

## Introduction:
We chose to perform an **Application Project**, which will use different sections of the [_Chips n' Salsa_](https://chips-n-salsa.cicirello.org) library, specifically targeting the Travelling Salesperson Problem and the Floyd-Warshall Algorithm using these to show different pathways and routes that a person could take from one specific location to the other on the [Stockton University Campus](https://www.stockton.edu). We have noticed a trend with incoming freshman, transferring students, or the outlier of COVID-19 that there was barely any tools that assisted and aided students in navigating the halls. Yes, there is the [Smaller School Map](https://stockton.edu/maps/documents/stocktonmap.pdf) that is able to be accessed, but the main website is a hassle to navigate being a newcomer. So, our goal is for this to be influential and a tool used by both newcomers and veterans of the space to navigate between their classes, or places of interest, in the fastest way possible. 

Creating this, it was necessary to plot different points and create a graph that was able to differentiate the locations into points, and have there be lines traversing throughout, showing the shortest possible travel time between points that were connected. For example, when connecting a staircase we had to make a point for the upper and lower portions of the buildings seperate, since the top floor can *not* be accessed without taking the staircase. Then the 'cost', or the length of travel, would be taken. It was decided to use *[Google My Maps](https://www.google.com/maps/d/u/0/edit?mid=1j8Kd5MHKXVnJ9r8asANea8p6HhQWOuY&ll=39.49153912615076%2C-74.53232598664172&z=17)* _(**Note**: This is a link to the map that was used and all of the points added.)_, not to be confused with _Google Maps_, which allows multiple different people to plan and prepare for either a vacation or a trip however, for this project it is perfect to check the different distances between points that can be added onto a map.

Since it will be used by both new and older students alike, we made sure to make a simplistic front webpage that has different points for each specific place that generalizes where they will need to go. For example, if a student would need to go from B-Wing Room 010 to D-Wing Room 020, They would be shown the way to get from the **wing they are at**, to the **wing they are going**. It will show them a pathway from the entrance to the wing they're at, the stairway they need to get to, to the wing they need. We were not looking to add in every specific room due to time constraints, however this generalized guide of the academic spine will be a beneficial map and layout for those who need it. 

## Functionality:

- The user can insert different sections and entrances into a small menu with all of the points.
- The  menu will have an **ADD POINT** button to add a point for the Route Builder.
- The menu will have a **CLEAR PATH** button to clear all of the points on the path made.
- The menu will have a **CALCULATE PATH** button to show the points and the distances between.
- A small window will pop up after the **CALCULATE PATH** button has been pressed to show the distance between points and the total distance.
- A small informative section below the **CALCULATE PATH** button that shows the path that is being taken.
- The ability to zoom in and out of the map
- A bar at the top to filter through points.

## Software Architecture: 
- There was no specific design pattern in mind when starting this project, however it was known from the start that we were going to use a TSP while integrating components of the Floyd-Warshall method.
- The main components of the software are:
    - A Maven build
    - Floyd-Warshall
    - Traveling Salesperson Problem (TSP Problem)
    - A Distance Calculator
- The roles of the Software are:
    - **Maven**: A Project Managment and build tool that simplifies and automates different processes, including downloading dependicies that might be needed for the project to run.
    - **Floyd-Warshall**: A progamming algorithm that finds the shortest paths between all pairs of vertices in a weighted graph, handling positive or negative edge weights. It will then return a distance matrix containing the shortest path and their lengths between each vertices that were used in the graph.
    - **TSP**: Otherwise known as the 'Travelling Salesperson Problem', this is also an algorithm to find the shortest possible route, but visits a node only *once* before returning to the starting node. This, combined with the Floyd-Warshall Algorithm will show the shortest possible lengths between nodes and the fastest possible route throughout campus.
    - **The Distance Calculator**: This calculates the distance that a person will need to travel from the Lattitude and Longitude and changes it to feet for the User.


## Conclusion:

This project was very interesting for all of us to process and try to work through. Due to the nature of the semester, we tried to do something a bit more simplistic than what we actually achieved to do due to time constraints, but it was still extremely close to the wire on a finished product. There was much to learn, including the addition of Maven, the Spotify POM files for simplicity and simplifying, trying our hand at mixing both a TSP and Floydd-Warshall method together to combine into a total calculator for the Stockon Campus. Additionally, just how many doors and entrances the Stockton Campus actually has was eye-opening.

We continuously stated during the project that our goal was to do the whole campus, including the science wings and all of the main campis building, however, due to the time constraints of it being within the course time, and due to other classes also taking up a majority of our time, we had to cut a significant chunk of what we were willing to do. Other than the time constraints, the project was an extremely informative and knowledgeable dig into how to use the *Chips-n-Salsa* resource and the algorithms inside.
## Resources:
- [*Chips-n-Salsa*](https://chips-n-salsa.cicirello.org)
- [*Google My Maps*](https://www.google.com/maps/d/u/0/edit?mid=1j8Kd5MHKXVnJ9r8asANea8p6HhQWOuY&ll=39.49153912615076%2C-74.53232598664172&z=17)
- [*Markdown Guide*](https://www.markdownguide.org/basic-syntax/)
- [*Markdown Live Preview*](https://markdownlivepreview.com)
- [Stockton University Website](https://www.stockton.edu)
     - [*Stockton University* Map](https://stockton.edu/maps/documents/stocktonmap.pdf)