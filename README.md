Thanks for the opportunity to take the assessment.

Although this was a simple exercise, there was a challanege to find a balance between over engineering and trying to solve the solution in a simple way.
So I went about solving the problem as somewhat my first iterative version that could pass initial tests. Sure, 
there can be lots of improvement, but with a couple of assumptions, I wanted to solve it in a way that it was easy to read, simple enough for anyone to run and extend this to more functionality in more iterations.

## How to run

```shell
./gradlew run --args="path to your test file name"
```

That generates a text file called report.txt in the project root directory.

## Approach

I believe in writing code that is simple and independent enough to be unit tested without many dependencies. Looking at the problem statement, the first thing that naturally came to me was breaking it down by functional components.


I ended up breaking the problem down into 3 main components :  

* Tracker - The driver history tracker read commands from a file
* Command - There are two types of commands
* Trip Summary - A summary report which had to be generated after executing the commands

I did not add the utility component (as this is just some abstraction with helpers to read file, generate report etc.)

My approach to the problem was when components are broken down by function, it is easier to modify/change if needed in an iterative way and this helps to build more functionality on top of this easily.

### Tracker

The tracker basically is the driver function, which knows what to expect as the input and also generate the output.

Assumptions : 

* In my solution, I assumed that the input is always passed as the file path as the argument using the --args flag.
* The report is generated in a file called `report.txt` that is created in the project root directory.

Since this is the driver function, I believe this is something that shouldn't change if we need to change any command logic.
It is a very simple piece, which for each line of the file, extracts the command, filters nulls, filters invalid commands and executes them on a trip summary collection.
```java

linesFromFile.map(Util::extract)
                .filter(Objects::nonNull)
                .filter(Command::isExecutable)
                .forEach(cmd -> cmd.execute(tripSummaryMap));
```

### Command

Each line in the file is a command, which basically has a type, and a business logic to it based on the type.
This component can be handled in a multiple ways using generic classes, types etc. Since I am using Java, I felt using an interface was apt in this case.

Assumptions :
* Driver command is ignored if driver is already registered.
* Trip command does not register a driver if driver has not been registered. Such trips are just ignored.
* Tracker always calls isExecutable() before calling execute() (So I do not have validation for execute, which does not break the code but something that can always be improved upon with another iteration)

```java
public interface Command {

    /**
     *  Validate all the required parameters to execute the command
     *
     * @return true if Command is executable
     */
    boolean isExecutable();

    /**
     * Execute the command on the provided trip summary
     *
     * @param tripSummaryMap map that includes the 
     * trip summary associated with each Driver
     */
    void execute(Map<String, TripSummary> tripSummaryMap);

}

```

So, I have 2 classes implementing this interface, `Driver` command and `Trip` command. Since their function is broken down into validations and execution, adding new functionality/commands to this would be very simple i.e as easy as just adding another class implementing the interface and not changing any of the Tracker and other components thanks to polymorphism.


### Trip Summary

I have used a simple Map to store the drivers, and their respective trip summary in this case, only because I wanted to keep the solution simple. I understand persisting and updating values would make more sense in a prodution ready environment, but for this assessment, a Hashmap it is. :)
Also, keeping the summary as an individual component allows us to change how we report again without changing a lot of code.

The trip summary also implements `Comparable` so it is easy to modify sort logic and keeps things isolated and testable.

### Unit tests

All methods are unit tested apart from imported java functions.

To run the tests :
```shell
./gradlew test
```

### Conclusion

I hope this brief explanation was sufficient to help you understand my thought process. I looked at this problem like any other and broke it down into functional pieces that made sense and solved it like I would in an iterative approach. I did not try to overuse any software design paradigm/pattern except for some OOPs. I believe some problems do have simple solutions :)

Thank you for your time.