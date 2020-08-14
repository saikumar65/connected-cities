MASTERCARD CODE CHALLENGE 


I solved the Connected cities puzzle in the best way i could there is scope for enhancements using some of the Java 8 features but i was running with issues, so didnot use it.
I believe the code coverage is more than 90% covering most of the scenarios. 
I put the input file in the classpath and started reading from it. Please update the file with more content if you want to test extra scenarios.

Thanks for the opportunity

Challenge:

You’re asked to write a program using Spring Boot & Java (1.8 or above) which determines if two cities are connected. Two cities are considered connected if there’s a series of roads that can be traveled from one city to another. 
List of roads is available in a file. The file contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities. 
It will be deployed as a Spring Boot App and expose one endpoint: http://localhost:8080/connected?origin=city1&destination=city2 
Your program should respond with ‘yes’ if city1 is connected to city2, ’no’ if city1 is not connected to city2. Any unexpected input should result in a ’no’ response. 

For Example: 
city.txt content is: Boston, New York Philadelphia, Newark Newark, Boston Trenton, Albany 
http://localhost:8080/connected?origin=Boston&destination=Newark Should return yes http://localhost:8080/connected?origin=Boston&destination=Philadelphia Should return yes http://localhost:8080/connected?origin=Philadelphia&destination=Albany Should return no 
——————————————— 
IMPORTANT 
• This exercise is a take-home assignment. 
• Use Github as a repository for your solution. The completed assignment should be emailed back to myself in the form of the GitHub URL. 
• This challenge needs to be completed within 1-3 days from the Date/Time of your first commit. 
• Impress us with: 
o A well-documented project that utilizes various best 
practices. o Well-written Unit Tests (which need to pass) & comprehensive 
test coverage. 
