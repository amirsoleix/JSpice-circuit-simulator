# JSpice Circuit Simulator

JSpice is a simple electrical circuit simulator written in Java with simple GUI and extended graphing capabilities for better analysis.  
It uses the finite element method technique to find the nearest solution for a given circuit and display the results both as data tables and graphs. The electrical circuit elements deployed include:

- Independent current and voltage sources
- Dependent current and voltage sources
- Resistors
- Capacitors
- Inductors  
  This makes it a fairly basic circuit simulator usable for high-school and freshman students.

## Graphical Interface

After the application is launched, it prompts the user to input the file for the circuit model. It then reads the file chosen, recognizes the errors and declares them using appropriate messages and in the case of no errors, It draws the layout of the circuit in a box window. The snippet for drawing the circuit layout uses complex conditioning which analyses the circuit and chooses the near best option to layout the circuit so that the intersections and aggregations of elements in the window are minimized.

## Console

The application console is available for commands and the user can interact with the program using only the specific commands that are introduced in the application.

## Graphical Output

Upon receiving input, the given circuit is drawn in the GUI and the analysis is performed based on the given parameters from the user. These parameters include:

- Analysis time interval
- Precision of analysis
- The desired outputs  
  The output is then graphed using appropriate axes to denote different relations between parameters of the circuit e.g. voltage-time. Also a file containing a thorough analysis is created and saved on the same directory. The user can then choose to close the program or analyse a new circuit by repeating the same steps.

For more info on how the simulator works please visit:

- https://www.pspice.com/
- https://www.orcad.com/pspice-free-trial

P.S: Note that the simulator uses a basic implementation of FEM and is not suitable for precise applications.

Developed by Amirmahdi Soleimanifar, Meysam Amirsardari
