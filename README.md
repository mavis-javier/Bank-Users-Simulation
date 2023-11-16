# Bank-Users-Simulation
This program will create simulated users with different security clearances. Its goal is to show the importance of Confidentiality policies utilized in the real-world.

# Quick blurbs about code structure
 - Console.java serves as the controller and the view. Ideally all logic will occur here.
 - DBManager strictly builds and returns object instances. No logic or DB modifications occur here.
    - DB modification might need to just be done directly through the MySQL cli or gui for the purposes of this project
 - SecLevel.java is an enum of Bell-Lapadula clearances/classifcations.
    - Subjects call their associated SecLevel their clearance.
    - Options call their associated SecLevel their classification.
