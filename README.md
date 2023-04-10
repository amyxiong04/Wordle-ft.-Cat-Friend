# Wordle

This Java application has the runs a word game called Wordle.
The aim is to guess a five-letter word in six attempts. Each box can hold one letter,
and so you guess at the correct word by typing in a five-letter word and hitting the Enter key.
To guide you towards the target word, each time you guess, certain letters will be highlighted. 
A letter coloured in yellow means that the letter is in the target word, but in the wrong 
position. A letter coloured in green indicates that it is a correct letter and also in the correct
position. Letters may be used twice or thrice in a word. If you fail 
to guess the target word in six tries, the answer will be revealed to you. 

The target audience of this application is any user who is looking for brain stimulating
entertainment, or simply those who desire a little more practice with their english vocabulary
and reasoning skills. As an avid word/puzzle game player, I find interest in exploring the technical
aspects that make such games possible.

# User Stories


- As a user, I want to be able to select a game-play difficulty level that controls how many characters the target
  word is.
- As a user, I want to be able to input a guess which is added to my list of previous guesses.
- As a user, I want to be given the option to receive a hint about the target word.
- As a user, I want to be given the option the statistics of the game upon guessing the correct word.
- As a user, when the game is over, regardless if I won or not, I want to be given the option to play again.
- As a user, I want to be given the option to save, or save and quit from my current game.
- As a user, I want to be given the option to load my previous
  game state from file and resume my game.

# Instructions for Grader

- You can generate the first required action related to adding a guess to a list of guesses by typing in your guess into the text field and clicking the 'Make Guess' button.
- You can generate the second required action related to removing all guesses from the list of guesses by clicking the 'Restart Game' button at any time.
- My visual component is located on the right, with a cat who changes expressions based on whether you win or lose the game.
- You can save the state of my application by clicking the 'Save Game' button at any time.
- You can reload the state of my application by clicking the 'Load Game' button at any time.

# Phase 4: Task 2
Mon Apr 10 15:51:18 PDT 2023 \
Added guess PIZZA to guess log.

Mon Apr 10 15:51:20 PDT 2023 \
Added guess SLICE to guess log.

Mon Apr 10 15:51:21 PDT 2023 \
Removed all guesses from guess log.

Mon Apr 10 15:51:23 PDT 2023 \
Added guess APPLE to guess log.

Mon Apr 10 15:51:27 PDT 2023 \
Added guess RIVER to guess log.

Mon Apr 10 15:51:28 PDT 2023 \
Removed all guesses from guess log.

Mon Apr 10 15:51:33 PDT 2023 \
Added guess APPLE to guess log.

# Phase 4: Task 3
Through examining the design presented in my UML class diagram for this project,
I notice that there is a bit of cross-over between association arrows as both the classes
WordleApp and WordleAppGraphical are associated with the same classes. This design appears
to be redundant, and could be improved by creating a super-class for which both WordleApp and 
WordleAppGraphical could extend and inherit methods from. 

As well, if given more time to work on the project, I would have liked to increase
the robustness of the entire program by injecting exceptions into my code. This would also
prevent unexpected behaviour. 

I believe the cohesion of my current code can also be significantly improved, as a select few
of my classes are quite large and cover a significant range of methods. This could result in confusion
over the purpose of certain functions. To resolve this design issue, I would consider breaking up my code
into more classes, such that the purpose of methods within each class are much more related
and that relation between each of the seperated classes is further coupled. In particular, given more time, 
I would construct a class solely for one character of one guess, allowing me to analyze the colour
of a singular letter before introducing it to the string of letters in a guess, and finally
the list of guesses in the guess log.


- implemented singleton design for one instance of log class
- seperated code into more classes/abstract classes/ interfaces
- improve error handling by adding exceptions to make program more robust and prevented unexpected behaviour.
- use interfaces and abstractions to reduce coupling and improve flexibility/maintainability of the code
- implement design patterns to improve structure of code
- eliminate code duplication

# Attributions
- String builder technique to add separators to a string: https://stackoverflow.com/questions/58928521/java-add-separator-to-a-string
- JsonReader and JsonWriter classes (attribution also found in more detail in specification of referenced methods): https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo




Add a section at the end of your README.md file entitled "Phase 4: Task 3".  Reflect on the design presented in your UML class diagram.  If you had more time to work on the project, what substantive refactoring might you use to improve your design?  In 1-2 paragraphs in this section of your README, describe the refactoring you are imagining and your reasons for it.  You do NOT need to perform the refactoring - just identify the changes you would make if you had more time.  Keep in mind that refactoring does not mean adding more features to your application.

(Everyone can find some refactoring. If you believe there's no refactoring that could improve your design: Check again; there is! Even if you still think there's nothing, there are tradeoffs you made that could be made differently via a refactoring. Choose one, follow the description above, but emphasize the advantages and disadvantages of the proposed refactoring.) 