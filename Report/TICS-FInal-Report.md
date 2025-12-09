# Topics in Computer Science: Applied Evolutionary Computation Stockton TSP Project Report

###### Team Members: Jack Stewart, Sajjad Haider, Matthew Houseworth, Kyle McFadden and Justin Murphy

## Introduction:
We chose to perform an **Application Project**, which will use different sections of the [_Chips n' Salsa_](https://chips-n-salsa.cicirello.org) library, specifically targeting the Travelling Salesperson Problem and the Floyd-Warshall Algorithm using these to show different pathways and routes that a person could take from one specific location to the other on the [Stockton University Campus](https://www.stockton.edu). We have noticed a trend with incoming freshman, transferring students, or the outlier of COVID-19 that there was barely any tools that assisted and aided students in navigating the halls. Yes, there is the [Smaller School Map](https://stockton.edu/maps/documents/stocktonmap.pdf) that is able to be accessed, but the main website is a hassle to navigate being a newcomer. So, our goal is for this to be influential and a tool used by both newcomers and veterans of the space to navigate between their classes, or places of interest, in the fastest way possible. 

Creating this, it was necessary to plot different points and create a graph that was able to differentiate the locations into points, and have there be lines traversing throughout, showing the shortest possible travel time between points that were connected. For example, when connecting a staircase we had to make a point for the upper and lower portions of the buildings seperate, since the top floor can *not* be accessed without taking the staircase. Then the 'cost', or the length of travel, would be taken. It was decided to use *[Google My Maps](https://www.google.com/maps/d/u/0/edit?mid=1j8Kd5MHKXVnJ9r8asANea8p6HhQWOuY&ll=39.49153912615076%2C-74.53232598664172&z=17)* _(**Note**: This is a link to the map that was used and all of the points added.)_, not to be confused with _Google Maps_, which allows multiple different people to plan and prepare for either a vacation or a trip however, for this project it is perfect to check the different distances between points that can be added onto a map.

Since it will be used by both new and older students alike, we made sure to make a simplistic front webpage that has different points for each specific place that generalizes where they will need to go. For example, if a student would need to go from B-Wing Room 010 to D-Wing Room 020, They would be shown the way to get from the **wing they are at**, to the **wing they are going**. It will show them a pathway from the entrance to the wing they're at, the stairway they need to get to, to the wing they need. We were not looking to add in every specific room due to time constraints, however this generalized guide of the academic spine will be a beneficial map and layout for those who need it. 

## Functionality:

- The user can insert different sections and entrances into a dropdown menu.
- The dropdown menu will have a **START** and a **STOP** bar for the start and ending destinations that they request.
- An additional dropdown menu to add multiple more points to the map
- A button to continue forward to the next webpage
- A webpage that shows the points from **START** and **STOP** on the previous webpage
- The ability to zoom in and out of the map
- A button to go backwards to the previous webpage

## Software Architecture: 
- This is where the high level architecture will be.
- There was no specific design pattern in mind when starting this project, however it was known from the start that we were going to use a TSP while integrating components of the Floyd-Warshall method.
- The main components of the software are:
    - A Maven build
    - Floyd-Warshall
    - Traveling Salesperson Problem (TSP Problem)
    - A Distance Calculator
- The roles of the Software are:
    - Maven 
    - Floyd-Warshall
    - TSP
    - The Distance Calculator calculates the distance that a person will need to travel from the Lattitude and Longitude and changes it to feet for the User.
- How do they interact?
- Diagram is useful**


## Conclusion:

This needs to be done when the website is completely finished and operable.**
I'll be trying to do this while making a simplistic User Guide in a seperate file.
I'll also be adding pictures to this Report.

## Resources:
- [*Chips-n-Salsa*](https://chips-n-salsa.cicirello.org)
- [*Google My Maps*](https://www.google.com/maps/d/u/0/edit?mid=1j8Kd5MHKXVnJ9r8asANea8p6HhQWOuY&ll=39.49153912615076%2C-74.53232598664172&z=17)
- [*Markdown Guide*](https://www.markdownguide.org/basic-syntax/)
- [*Markdown Live Preview*](https://markdownlivepreview.com)
- [Stockton University Website](https://www.stockton.edu)
     - [*Stockton University* Map](https://stockton.edu/maps/documents/stocktonmap.pdf)
- 