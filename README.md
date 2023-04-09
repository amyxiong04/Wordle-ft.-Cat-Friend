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
Added guess SLICE to guess log. \
Removed all guesses from guess log. \
Added guess APPLE to guess log. \
Added guess SLICE to guess log. \
Removed all guesses from guess log. \
Added guess SPORT to guess log. \
Removed all guesses from guess log. \
Added guess BEACH to guess log. \
Added guess RIVER to guess log.

# Phase 4: Task 3
- implemented singleton design for one instance of log class
- seperated code into more classes/abstract classes/ interfaces
- improve error handling by adding exceptions to make program more robust and prevented unexpected behaviour.
- use interfaces and abstractions to reduce coupling and improve flexibility/maintainability of the code
- implement design patterns to improve structure of code
- eliminate code duplication

# Attributions
- String builder technique to add separators to a string: https://stackoverflow.com/questions/58928521/java-add-separator-to-a-string
- JsonReader and JsonWriter classes (attribution also found in more detail in specification of referenced methods): https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo




