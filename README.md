<div align="center">
    <h1>Java Tanklager</h1>
    <i>A school task</i>
</div>

<br>

<div align="center">
    <h2>General:</h2>

   A command line application is to be created, this has no GUI. All inputs as well as all outputs are done on the command line. The application has no 
   persistence - this means that everything is 'in memory' and only available the runtime of the program. When the program is started again, it is back in the 
   initial state (empty). The application does not need to write log files and does not need unit tests. If there is time left, the programmer is free to add this.
</div>

<br>

<div align="center">
    <h2>First part:</h2>


   Heating oil is stored in large tanks. Thousands of liters of heating oil are delivered to and taken from these tanks every day, both by train and by truck. 
   Each heating oil tank has a capacity in liters, a construction date, a unique name, and a number. Heating oil can be delivered to a tank, and heating oil can 
   also be distributed from it. When a tank is full, it only accepts as much oil as it can hold during delivery. After each delivery, the tank reports how much 
   oil was received and how much space is still available. If a delivery cannot be fully completed because a tank is empty, as much oil is delivered as needed to 
   empty the tank. After each distribution, the tank reports how much oil was distributed and how much is still in it.

   Additionally, an information report can be requested from each tank, which provides its unique identification, capacity, fill level, and available space.
</div>




<div align="center">
    <h2>Second part:</h2>

   The tank storage consists of X different heating oil tanks. Deliveries are made to the tank storage as a whole and not to specific heating oil tanks. New 
   heating oil tanks can be added to the tank storage at any time, and existing tanks can also be removed.
    
   The goal is to maximize the filling of tanks and minimize empty tanks.

   During a delivery, the delivered quantity is first filled into the first tank. If there isn't enough space there, the remainder is filled into the next tank 
   and so on until the entire delivery quantity has been accommodated. At the end of a delivery, it is reported how much oil was received and how much space is 
   still available (similar to an individual tank). Additionally, the status of each tank involved in the delivery is reported.

   During a distribution, oil is pumped out from a tank that has available oil. If the tank cannot meet the required amount, the next tank is tapped, and this 
   process continues until the entire required quantity has been distributed. After each distribution, the tank storage reports how much oil was distributed and 
   how much is left (similar to an individual tank). Additionally, the status of each tank involved in the distribution is reported.

   A status report for the entire tank storage provides the number of tanks, the total capacity, the total fill quantity, and the remaining capacity of the tank 
   storage.

   You can still request the status report of an individual tank.

   If a heating oil tank is removed, its existing content is redistributed among the remaining tanks. If there isn't enough space in the remaining tanks, it 
   cannot be removed.

   An individual tank can go into maintenance. This means its content must be redistributed. While in maintenance, it cannot receive oil, and its capacity is not 
   counted towards the tank storage's capacity. Tank maintenance can be ended, and it is fully available again from that point.
</div>

## TODO:
- [x] First part
- [x] Second part
- [x] Log files
- [x] Unit tests
- [x] Documentation
- [x] Report