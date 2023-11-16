# Bank-Users-Simulation
This program will create simulated users with different security clearances. Its goal is to show the importance of Confidentiality policies utilized in the real-world.

# Quick blurbs about code structure
 - Console.java serves as the controller and the view. Ideally all logic will occur here.
 - DBManager strictly builds and returns object instances. No logic or DB modifications occur here.
    - DB modification might need to just be done directly through the MySQL CLI or GUI for the purposes of this project
 - SecLevel.java is an enum of Bell-Lapadula clearances/classifcations.
    - Subjects call their associated SecLevel their clearance.
    - Options call their associated SecLevel their classification.

# Setting up database locally
- Creating the database
  - download MySQL Server 8.0.035 - X64
    - note username, password, and socket number during installation process
  - open MySQL Command Line Client
  - copy and paste sql script
- Ensure mysql-connector-j-8.2.0.jar is in classpath (it is in the lib folder)
  - Go to project structure, library, add library, set classpath to the .jar file
- Add your username, password, and socket number to DBManager